package com.codeying.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.codeying.utils.component.*;
import com.codeying.utils.*;
import com.codeying.component.TokenService;
import com.codeying.mapper.SqlMapper;
import com.codeying.mapper.SqlMapper.*;
import com.codeying.component.VerifiedCodeGenerator;
import com.codeying.component.utils.CacheUtil;
import com.codeying.entity.*;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.codeying.service.*;

import jakarta.servlet.http.HttpServletRequest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;
import java.util.*;
import java.util.Date;

/** 登陆、Registration、登出 */
@Controller
public class IndexController extends BaseController {
  @Autowired private TokenService tokenService;
  @Autowired CacheUtil cacheUtil;
  @Autowired private SqlMapper sqlMapper;

  /**
   * 首页访问
   *
   * @return
   */
  @RequestMapping({"welcome", "/"})
  @ResponseBody
  public ApiResult welcome(Integer n) {
    if (n == null) {
      n = 5; // 取前几个？
    }
    Map<String, Object> res = new HashMap<>();
    LoginUser user = getCurrentUser();
    // 公告
    List<Notice> notices = noticeService.list();
    res.put("notices", notices);
    // 最新Game News
    List<NewsInfo> newsInfoHotList = newsInfoService.topN(n);
    for (NewsInfo e : newsInfoHotList) {
      e.setBiaoqLabels(tagInfoService.getLabelValues(e.getBiaoq()));
    }
    res.put("newsInfoHotList", newsInfoHotList);
    // 游戏数据展示
    List<GameData> gameDataHotList = gameDataService.topN(n);
    for (GameData e : gameDataHotList) {
      e.setBiaoqLabels(tagInfoService.getLabelValues(e.getBiaoq()));
    }
    res.put("gameDataHotList", gameDataHotList);
    // 游戏攻略分享
    List<UserShare> userShareHotList = userShareService.topN(n);
    for (UserShare e : userShareHotList) {}
    res.put("userShareHotList", userShareHotList);
    return successData(res);
  }

  @GetMapping("/captcha")
  @ResponseBody
  public ApiResult<Map<String, Object>> captcha() {
    ByteArrayOutputStream outputStream = null;
    try {
      outputStream = new ByteArrayOutputStream();
      String code = VerifiedCodeGenerator.generateVerifyCode(4, VerifiedCodeGenerator.JUST_NUMBER);
      VerifiedCodeGenerator.outputImage(100, 50, outputStream, code);
      byte[] images = outputStream.toByteArray();
      String imageStr = Base64.getEncoder().encodeToString(images);
      Map<String, Object> resMap = new HashMap<>();
      String captchaKey = "Captcha" + CommonUtils.newId();
      cacheUtil.set(captchaKey, code, 600);
      resMap.put("captchaKey", captchaKey);
      resMap.put("captcha", imageStr);
      return successData(resMap);
    } catch (Exception e) {
      return fail(e.getMessage());
    } finally {
      IOUtils.closeQuietly(outputStream);
    }
  }

  @RequestMapping("login")
  @ResponseBody
  public ApiResult<Map<String, Object>> login(
      String captcha, String captchaKey, String username, String password, String usertype)
      throws Exception {
    Map<String, Object> apiRes = new HashMap<>();
    // 校验验证码
    String captchaOrigin = cacheUtil.remove(captchaKey);
    if (captcha == null || !captcha.equalsIgnoreCase(captchaOrigin)) {
      return fail("验证码错误");
    }
    try {
      SecretKeySpec skeySpec = new SecretKeySpec("IJNDAIWNDWAJWJ65".getBytes("UTF-8"), "AES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, skeySpec);
      byte[] original = cipher.doFinal(Base64.getDecoder().decode(password));
      password = new String(original);
    } catch (Exception e) {
    }

    // 登陆的用户
    LoginUser loginUser;
    if (usertype.equals("admin")) {
      QueryWrapper<Admin> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      wrapper.eq("password", password);
      loginUser = adminService.getOne(wrapper);
      if (loginUser != null) {
        String token = tokenService.createToken(loginUser);
        apiRes.put("token", token);
        apiRes.put("user", loginUser);
        return successData(apiRes);
      }
    }
    if (usertype.equals("user")) {
      QueryWrapper<User> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      wrapper.eq("password", password);
      loginUser = userService.getOne(wrapper);
      if (loginUser != null) {
        String token = tokenService.createToken(loginUser);
        apiRes.put("token", token);
        apiRes.put("user", loginUser);
        return successData(apiRes);
      }
    }
    // 登陆失败，就重新登陆
    return fail("账号密码有误，登陆失败！");
  }

  @PostMapping("register")
  @ResponseBody
  public ApiResult<String> register(@RequestBody Map<String, String> map) throws Exception {
    String username = map.get("username");
    String password = map.get("password");
    String usertype = map.get("usertype");
    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
      return fail("账号密码不可为空！");
    }
    if (usertype.equals("admin")) {
      QueryWrapper<Admin> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      Admin temp = adminService.getOne(wrapper);
      if (temp != null) {
        return fail("账号已存在！");
      }
      Admin admin = new Admin();
      admin.setId(CommonUtils.newId());
      admin.setUsername(username);
      admin.setPassword(password);
      String id = ""; // 管理员主键
      admin.setId(CommonUtils.newId());
      String name = ""; // 姓名
      String tele = ""; // 电话
      adminService.save(admin);
      return successMsg("Registration成功，请登陆");
    }
    if (usertype.equals("user")) {
      QueryWrapper<User> wrapper = new QueryWrapper<>();
      wrapper.eq("username", username);
      User temp = userService.getOne(wrapper);
      if (temp != null) {
        return fail("账号已存在！");
      }
      User user = new User();
      user.setId(CommonUtils.newId());
      user.setUsername(username);
      user.setPassword(password);
      String id = ""; // 用户主键
      user.setId(CommonUtils.newId());
      String name = ""; // 姓名
      String avatar = ""; // 头像
      String gender = ""; // 性别
      Integer age = 0; // 年龄
      String tele = ""; // 电话
      String biaoq = ""; // 游戏标签
      String gamesId = ""; // 喜欢的游戏
      Double youx = 0.0; // 账号总价值
      Double gameh = 0.0; // 总游戏时长
      userService.save(user);
      return successMsg("Registration成功，请登陆");
    }
    // Registration失败
    return successMsg("请选择角色类型");
  }

  /**
   * LOG OUT
   *
   * @return
   */
  @PostMapping("/logout")
  @ResponseBody
  public ApiResult<String> loginOut(HttpServletRequest request) {
    try {
      tokenService.removeLoginInfo();
      return success();
    } catch (Exception e) {
      e.printStackTrace();
      return fail(e.getMessage());
    }
  }

  @GetMapping("/userinfo")
  @ResponseBody
  public ApiResult<LoginUser> getUser() {
    return successData(getCurrentUser());
  }

  // echarts数据
  @PostMapping("hello")
  @ResponseBody
  public ApiResult<List<Echart>> helloData() throws SQLException {
    // 数据
    List<Echart> l = new ArrayList<>();
    {
      // 图表
      Echart echart = new Echart();
      echart.setName("数");
      echart.setType("n"); // line pie
      // echart.setValue(studentService.count()+"");
      // l.add(echart.init());
    }
    {
      // 图表
      Echart echart = new Echart();
      echart.setName("统计");
      echart.setType("bar"); // line pie
      // echart.setDtos(sqlMapper.exec("select id as name,score as value from tb_score"));
      // l.add(echart.init());
    }
    return successData(l);
  }
}

