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

/** 最新Game News控制器 关于最新Game News的增删改查操作都在这 */
@Controller
@RequestMapping({"newsInfo", "webu/newsInfo"})
public class NewsInfoController extends BaseController {

  /** 最新Game News网站列表页 */
  @RequestMapping("list-web")
  @ResponseBody
  public ApiResult<Map<String, Object>> listweb() {
    Map<String, Object> respMap = new HashMap<>();
    QueryWrapper<NewsInfo> queryWrapper = getQueryWrapper();
    List<NewsInfo> newsInfoList = newsInfoService.list(queryWrapper);

    List<TagInfo> biaoqFrnList = tagInfoService.list();
    respMap.put("biaoqFrnList", biaoqFrnList); // 外键放入request
    // 循环遍历list数据，统计、获取外键数据
    for (NewsInfo e : newsInfoList) {

      e.setBiaoqLabels(tagInfoService.getLabelValues(e.getBiaoq()));

      // 最新Game NewsFavoriteLike数
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
    newsInfoList = newsInfoService.recommend(newsInfoList);
    respMap.put("listData", newsInfoList);
    return successData(respMap);
  }

  // 最新Game News列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 分页大小
    // 获取列表数据
    QueryWrapper<NewsInfo> queryWrapper = getQueryWrapper();
    LoginUser loginUser = tokenService.getLoginInfo();
    IPage<NewsInfo> pageInfo = new Page<NewsInfo>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = newsInfoService.page(pageInfo, queryWrapper);

    // 循环遍历list数据获取外键数据
    for (NewsInfo e : pageInfo.getRecords()) {
      e.setBiaoqLabels(tagInfoService.getLabelValues(e.getBiaoq()));
    }

    PagerVO<NewsInfo> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<NewsInfo> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<NewsInfo> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String showtitle = req.getParameter("showtitle");
    if (!StringUtils.isEmpty(showtitle)) {
      paramMap.like("showtitle", showtitle);
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

  // 最新Game News详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    NewsInfo entity = newsInfoService.getById(id);
    // 获取外键标签：游戏标签
    entity.setBiaoqLabels(tagInfoService.getLabelValues(entity.getBiaoq()));

    if (req.getRequestURI().contains("/webu")) {
      Map<String, Object> respMap = new HashMap<>();
      // 最新Game NewsFavoriteLike数
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
      // 最新Game News详情页推荐
      List<NewsInfo> newsHotList = newsInfoService.topN(5);
      respMap.put("recommends", newsHotList);
      respMap.put("entity", entity);
      return successData(respMap);
    } else {
      return successData(entity);
    }
  }

  // 最新Game News保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody NewsInfo entityTemp) {
    String id = entityTemp.getId(); // 最新Game News主键
    String showpic = entityTemp.getShowpic(); // 资讯首图
    String showtitle = entityTemp.getShowtitle(); // 资讯标题
    String biaoq = entityTemp.getBiaoq(); // 标签
    String showdesc = entityTemp.getShowdesc(); // 内容简介
    String showdetail = entityTemp.getShowdetail(); // 详情
    Date publishtime = entityTemp.getPublishtime(); // 发布时间
    String vv = entityTemp.getVv(); // 更多信息

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      publishtime = new Date();
      entityTemp.setPublishtime(publishtime);
      // 唯一字段，则在此校验
      QueryWrapper<NewsInfo> wrappershowtitle = new QueryWrapper();
      wrappershowtitle.eq("showtitle", entityTemp.getShowtitle());
      if (newsInfoService.list(wrappershowtitle).size() > 0) {
        return fail("资讯标题 已存在！");
      }
      // before add

      newsInfoService.save(entityTemp);
    } else {
      // before edit

      newsInfoService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 最新Game News删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    NewsInfo delTemp = newsInfoService.getById(id);
    // before del

    // 根据ID删除记录
    newsInfoService.removeById(id);
    return success();
  }
}

