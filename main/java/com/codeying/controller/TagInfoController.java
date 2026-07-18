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

/** 游戏标签控制器 关于游戏标签的增删改查操作都在这 */
@Controller
@RequestMapping({"tagInfo", "webu/tagInfo"})
public class TagInfoController extends BaseController {

  // 前后端分离，做为外键下拉
  @RequestMapping("list/select")
  @ResponseBody
  public ApiResult select() {
    ApiResult res = list(1, 500);
    PagerVO<TagInfo> pagerVO = (PagerVO<TagInfo>) res.getData();
    return successData(pagerVO.getRecords());
  }

  // 游戏标签列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 分页大小
    // 获取列表数据
    QueryWrapper<TagInfo> queryWrapper = getQueryWrapper();
    LoginUser loginUser = tokenService.getLoginInfo();
    IPage<TagInfo> pageInfo = new Page<TagInfo>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = tagInfoService.page(pageInfo, queryWrapper);

    PagerVO<TagInfo> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<TagInfo> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<TagInfo> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String name = req.getParameter("name");
    if (!StringUtils.isEmpty(name)) {
      paramMap.like("name", name);
    }

    String orderByStr = "id desc"; // 默认根据id降序排序
    // 默认按照id排序
    paramMap.last("order by " + orderByStr);
    return paramMap;
  }

  // 游戏标签详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    TagInfo entity = tagInfoService.getById(id);

    return successData(entity);
  }

  // 游戏标签保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody TagInfo entityTemp) {
    String id = entityTemp.getId(); // 游戏标签主键
    String name = entityTemp.getName(); // 标签名
    String description = entityTemp.getDescription(); // 标签说明

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      // 唯一字段，则在此校验
      QueryWrapper<TagInfo> wrappername = new QueryWrapper();
      wrappername.eq("name", entityTemp.getName());
      if (tagInfoService.list(wrappername).size() > 0) {
        return fail("标签名 已存在！");
      }
      // before add

      tagInfoService.save(entityTemp);
    } else {
      // before edit

      tagInfoService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 游戏标签删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    TagInfo delTemp = tagInfoService.getById(id);
    // before del

    // 根据ID删除记录
    tagInfoService.removeById(id);
    return success();
  }
}

