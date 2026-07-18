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

/** 游戏信息库控制器 关于游戏信息库的增删改查操作都在这 */
@Controller
@RequestMapping({"games", "webu/games"})
public class GamesController extends BaseController {

  // 前后端分离，做为外键下拉
  @RequestMapping("list/select")
  @ResponseBody
  public ApiResult select() {
    ApiResult res = list(1, 500);
    PagerVO<Games> pagerVO = (PagerVO<Games>) res.getData();
    return successData(pagerVO.getRecords());
  }

  // 游戏信息库列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 分页大小
    // 获取列表数据
    QueryWrapper<Games> queryWrapper = getQueryWrapper();
    LoginUser loginUser = tokenService.getLoginInfo();
    IPage<Games> pageInfo = new Page<Games>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = gamesService.page(pageInfo, queryWrapper);

    PagerVO<Games> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<Games> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<Games> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String name = req.getParameter("name");
    if (!StringUtils.isEmpty(name)) {
      paramMap.like("name", name);
    }
    String fabsjL = req.getParameter("fabsjL");
    String fabsjR = req.getParameter("fabsjR");
    if (!StringUtils.isEmpty(fabsjL)) {
      paramMap.ge("fabsj", DateUtil.strToDate(fabsjL));
    }
    if (!StringUtils.isEmpty(fabsjR)) {
      paramMap.le("fabsj", DateUtil.strToDate(fabsjR));
    }

    String orderByStr = "id desc"; // 默认根据id降序排序
    // 默认按照id排序
    paramMap.last("order by " + orderByStr);
    return paramMap;
  }

  // 游戏信息库详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    Games entity = gamesService.getById(id);

    return successData(entity);
  }

  // 游戏信息库保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody Games entityTemp) {
    String id = entityTemp.getId(); // 游戏信息库主键
    String name = entityTemp.getName(); // 游戏名
    String youxjj = entityTemp.getYouxjj(); // 游戏简介
    String company = entityTemp.getCompany(); // 发布公司
    Integer hots = entityTemp.getHots(); // 玩家人数
    Date fabsj = entityTemp.getFabsj(); // 发布时间

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      // 唯一字段，则在此校验
      QueryWrapper<Games> wrappername = new QueryWrapper();
      wrappername.eq("name", entityTemp.getName());
      if (gamesService.list(wrappername).size() > 0) {
        return fail("游戏名 已存在！");
      }
      // before add

      gamesService.save(entityTemp);
    } else {
      // before edit

      gamesService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 游戏信息库删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    Games delTemp = gamesService.getById(id);
    // before del

    // 根据ID删除记录
    gamesService.removeById(id);
    return success();
  }
}

