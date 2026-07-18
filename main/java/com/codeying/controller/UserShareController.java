package com.codeying.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codeying.component.*;
import com.codeying.component.utils.*;
import com.codeying.utils.component.*;
import com.codeying.utils.*;
import com.codeying.entity.*;
import com.codeying.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.io.InputStream;
import java.util.*;
import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;
import jakarta.servlet.ServletOutputStream;
import java.math.BigDecimal;

/** 游戏攻略分享控制器 关于游戏攻略分享的增删改查操作都在这 */
@Controller
@RequestMapping({"userShare", "webu/userShare"})
public class UserShareController extends BaseController {

  /** 游戏攻略分享网站列表页 */
  @RequestMapping("list-web")
  @ResponseBody
  public ApiResult<Map<String, Object>> listweb() {
    Map<String, Object> respMap = new HashMap<>();
    QueryWrapper<UserShare> queryWrapper = getQueryWrapper();
    List<UserShare> userShareList = userShareService.list(queryWrapper);

    List<Games> youxFrnList = gamesService.list();
    respMap.put("youxFrnList", youxFrnList); // 外键放入request
    List<User> fabrFrnList = userService.list();
    respMap.put("fabrFrnList", fabrFrnList); // 外键放入request
    // 循环遍历list数据，统计、获取外键数据
    for (UserShare e : userShareList) {

      // 游戏攻略分享FavoriteLike数
      int starCount =
          (int)
              starService.count(
                  new LambdaQueryWrapper<Star>()
                      .eq(Star::getItemid, e.getId())
                      .eq(Star::getType, "Favorite"));
      int praiseCount =
          (int)
              starService.count(
                  new LambdaQueryWrapper<Star>()
                      .eq(Star::getItemid, e.getId())
                      .eq(Star::getType, "Like"));
      e.setStarCount(starCount);
      e.setPraiseCount(praiseCount);
    }
    userShareList = userShareService.recommend(userShareList);
    respMap.put("listData", userShareList);
    return successData(respMap);
  }

  // 游戏攻略分享列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 分页大小
    // 获取列表数据
    QueryWrapper<UserShare> queryWrapper = getQueryWrapper();
    LoginUser loginUser = tokenService.getLoginInfo();
    // User只能看关联了自己的
    if (loginUser != null && loginUser.getRole().equals("user")) {
      queryWrapper.eq("fabr", loginUser.getId()); // 只能查看和自己相关的内容
    }
    IPage<UserShare> pageInfo = new Page<UserShare>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = userShareService.page(pageInfo, queryWrapper);

    // 循环遍历list数据获取外键数据
    for (UserShare e : pageInfo.getRecords()) {
      // 获取外键数据:游戏信息库
      e.setYouxFrn(gamesService.getById(e.getYoux()));
      // 获取外键数据:用户
      e.setFabrFrn(userService.getById(e.getFabr()));
    }

    PagerVO<UserShare> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<UserShare> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<UserShare> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String showtitle = req.getParameter("showtitle");
    if (!StringUtils.isEmpty(showtitle)) {
      paramMap.like("showtitle", showtitle);
    }
    String youx = req.getParameter("youx");
    if (!StringUtils.isEmpty(youx)) {
      paramMap.eq("youx", youx);
    }
    String fabr = req.getParameter("fabr");
    if (!StringUtils.isEmpty(fabr)) {
      paramMap.eq("fabr", fabr);
    }
    String publishtimeL = req.getParameter("publishtimeL");
    String publishtimeR = req.getParameter("publishtimeR");
    if (!StringUtils.isEmpty(publishtimeL)) {
      paramMap.ge("publishtime", DateUtil.strToDate(publishtimeL));
    }
    if (!StringUtils.isEmpty(publishtimeR)) {
      paramMap.le("publishtime", DateUtil.strToDate(publishtimeR));
    }

    String orderByStr = "id desc"; // 默认根据id降序排序
    // 默认按照id排序
    paramMap.last("order by " + orderByStr);
    return paramMap;
  }

  // 游戏攻略分享详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    UserShare entity = userShareService.getById(id);
    // 获取外键数据：游戏信息库
    entity.setYouxFrn(gamesService.getById(entity.getYoux()));
    // 获取外键数据：用户
    entity.setFabrFrn(userService.getById(entity.getFabr()));

    if (req.getRequestURI().contains("/webu")) {
      Map<String, Object> respMap = new HashMap<>();
      // 游戏攻略分享FavoriteLike数
      Long starCount =
          starService.count(
              new LambdaQueryWrapper<Star>()
                  .eq(Star::getItemid, entity.getId())
                  .eq(Star::getType, "Favorite"));
      Long praiseCount =
          starService.count(
              new LambdaQueryWrapper<Star>()
                  .eq(Star::getItemid, entity.getId())
                  .eq(Star::getType, "Like"));
      respMap.put("starCount", starCount);
      respMap.put("praiseCount", praiseCount);
      // 游戏攻略分享详情页推荐
      List<UserShare> newsHotList = userShareService.topN(5);
      respMap.put("recommends", newsHotList);
      respMap.put("entity", entity);
      return successData(respMap);
    } else {
      return successData(entity);
    }
  }

  // 游戏攻略分享保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody UserShare entityTemp) {
    String id = entityTemp.getId(); // 游戏攻略分享主键
    String showpic = entityTemp.getShowpic(); // 首图
    String showtitle = entityTemp.getShowtitle(); // 分享标题
    String youx = entityTemp.getYoux(); // 游戏
    String fabr = entityTemp.getFabr(); // 发布人
    String showdesc = entityTemp.getShowdesc(); // 简介
    String showdetail = entityTemp.getShowdetail(); // 详情
    Date publishtime = entityTemp.getPublishtime(); // 发布时间
    String vv = entityTemp.getVv(); // 操作视频

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      publishtime = new Date();
      entityTemp.setPublishtime(publishtime);
      // 唯一字段，则在此校验
      QueryWrapper<UserShare> wrappershowtitle = new QueryWrapper();
      wrappershowtitle.eq("showtitle", entityTemp.getShowtitle());
      if (userShareService.list(wrappershowtitle).size() > 0) {
        return fail("分享标题 已存在！");
      }
      // before add

      userShareService.save(entityTemp);
    } else {
      // before edit

      userShareService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 游戏攻略分享删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    UserShare delTemp = userShareService.getById(id);
    // before del

    // 根据ID删除记录
    userShareService.removeById(id);
    return success();
  }
}

