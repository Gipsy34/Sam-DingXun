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

/** 游戏数据展示控制器 关于游戏数据展示的增删改查操作都在这 */
@Controller
@RequestMapping({"gameData", "webu/gameData"})
public class GameDataController extends BaseController {

  /** 游戏数据展示网站列表页 */
  @RequestMapping("list-web")
  @ResponseBody
  public ApiResult<Map<String, Object>> listweb() {
    Map<String, Object> respMap = new HashMap<>();
    QueryWrapper<GameData> queryWrapper = getQueryWrapper();
    List<GameData> gameDataList = gameDataService.list(queryWrapper);

    List<Games> gameidFrnList = gamesService.list();
    respMap.put("gameidFrnList", gameidFrnList); // 外键放入request
    List<User> yonghFrnList = userService.list();
    respMap.put("yonghFrnList", yonghFrnList); // 外键放入request
    List<TagInfo> biaoqFrnList = tagInfoService.list();
    respMap.put("biaoqFrnList", biaoqFrnList); // 外键放入request
    // 循环遍历list数据，统计、获取外键数据
    for (GameData e : gameDataList) {

      e.setBiaoqLabels(tagInfoService.getLabelValues(e.getBiaoq()));

      // 游戏数据展示FavoriteLike数
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
    gameDataList = gameDataService.recommend(gameDataList);
    respMap.put("listData", gameDataList);
    return successData(respMap);
  }

  // 游戏数据展示列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 分页大小
    // 获取列表数据
    QueryWrapper<GameData> queryWrapper = getQueryWrapper();
    LoginUser loginUser = tokenService.getLoginInfo();
    // User只能看关联了自己的
    if (loginUser != null && loginUser.getRole().equals("user")) {
      queryWrapper.eq("yongh", loginUser.getId()); // 只能查看和自己相关的内容
    }
    IPage<GameData> pageInfo = new Page<GameData>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = gameDataService.page(pageInfo, queryWrapper);

    // 循环遍历list数据获取外键数据
    for (GameData e : pageInfo.getRecords()) {
      // 获取外键数据:游戏信息库
      e.setGameidFrn(gamesService.getById(e.getGameid()));
      // 获取外键数据:用户
      e.setYonghFrn(userService.getById(e.getYongh()));
      e.setBiaoqLabels(tagInfoService.getLabelValues(e.getBiaoq()));
    }

    PagerVO<GameData> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<GameData> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<GameData> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String showtitle = req.getParameter("showtitle");
    if (!StringUtils.isEmpty(showtitle)) {
      paramMap.like("showtitle", showtitle);
    }
    String gameid = req.getParameter("gameid");
    if (!StringUtils.isEmpty(gameid)) {
      paramMap.eq("gameid", gameid);
    }
    String yongh = req.getParameter("yongh");
    if (!StringUtils.isEmpty(yongh)) {
      paramMap.eq("yongh", yongh);
    }
    String biaoq = req.getParameter("biaoq");
    if (!StringUtils.isEmpty(biaoq)) {
      paramMap.like("biaoq", biaoq);
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

  // 游戏数据展示详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    GameData entity = gameDataService.getById(id);
    // 获取外键数据：游戏信息库
    entity.setGameidFrn(gamesService.getById(entity.getGameid()));
    // 获取外键数据：用户
    entity.setYonghFrn(userService.getById(entity.getYongh()));
    // 获取外键标签：游戏标签
    entity.setBiaoqLabels(tagInfoService.getLabelValues(entity.getBiaoq()));

    if (req.getRequestURI().contains("/webu")) {
      Map<String, Object> respMap = new HashMap<>();
      // 游戏数据展示FavoriteLike数
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
      // 游戏数据展示详情页推荐
      List<GameData> newsHotList = gameDataService.topN(5);
      respMap.put("recommends", newsHotList);
      respMap.put("entity", entity);
      return successData(respMap);
    } else {
      return successData(entity);
    }
  }

  // 游戏数据展示保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody GameData entityTemp) {
    String id = entityTemp.getId(); // 游戏数据展示主键
    String showpic = entityTemp.getShowpic(); // 首图
    String showtitle = entityTemp.getShowtitle(); // 标题
    String gameid = entityTemp.getGameid(); // 游戏
    String yongh = entityTemp.getYongh(); // 用户
    String biaoq = entityTemp.getBiaoq(); // 标签
    String showdesc = entityTemp.getShowdesc(); // 描述
    String showdetail = entityTemp.getShowdetail(); // 我的成就详情
    Integer hours = entityTemp.getHours(); // 游戏时长H
    Date publishtime = entityTemp.getPublishtime(); // 发布时间
    String vv = entityTemp.getVv(); // 展示

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      publishtime = new Date();
      entityTemp.setPublishtime(publishtime);
      // 唯一字段，则在此校验
      QueryWrapper<GameData> wrappershowtitle = new QueryWrapper();
      wrappershowtitle.eq("showtitle", entityTemp.getShowtitle());
      if (gameDataService.list(wrappershowtitle).size() > 0) {
        return fail("标题 已存在！");
      }
      // before add

      gameDataService.save(entityTemp);
    } else {
      // before edit

      gameDataService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 游戏数据展示删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    GameData delTemp = gameDataService.getById(id);
    // before del

    // 根据ID删除记录
    gameDataService.removeById(id);
    return success();
  }
}

