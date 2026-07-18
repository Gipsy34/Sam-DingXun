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

/** 我的素材控制器 关于我的素材的增删改查操作都在这 */
@Controller
@RequestMapping({"sucai", "webu/sucai"})
public class SucaiController extends BaseController {

  // 我的素材列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 分页大小
    // 获取列表数据
    QueryWrapper<Sucai> queryWrapper = getQueryWrapper();
    LoginUser loginUser = tokenService.getLoginInfo();
    IPage<Sucai> pageInfo = new Page<Sucai>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = sucaiService.page(pageInfo, queryWrapper);

    // 循环遍历list数据获取外键数据
    for (Sucai e : pageInfo.getRecords()) {
      // 获取外键数据:游戏信息库
      e.setYouxFrn(gamesService.getById(e.getYoux()));
    }

    PagerVO<Sucai> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<Sucai> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<Sucai> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String name = req.getParameter("name");
    if (!StringUtils.isEmpty(name)) {
      paramMap.like("name", name);
    }
    String youx = req.getParameter("youx");
    if (!StringUtils.isEmpty(youx)) {
      paramMap.eq("youx", youx);
    }
    String createtimeL = req.getParameter("createtimeL");
    String createtimeR = req.getParameter("createtimeR");
    if (!StringUtils.isEmpty(createtimeL)) {
      paramMap.ge("createtime", DateUtil.strToDate(createtimeL));
    }
    if (!StringUtils.isEmpty(createtimeR)) {
      paramMap.le("createtime", DateUtil.strToDate(createtimeR));
    }

    String orderByStr = "id desc"; // 默认根据id降序排序
    // 默认按照id排序
    paramMap.last("order by " + orderByStr);
    return paramMap;
  }

  // 我的素材详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    Sucai entity = sucaiService.getById(id);
    // 获取外键数据：游戏信息库
    entity.setYouxFrn(gamesService.getById(entity.getYoux()));

    return successData(entity);
  }

  // 我的素材保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody Sucai entityTemp) {
    String id = entityTemp.getId(); // 我的素材主键
    String name = entityTemp.getName(); // 素材名称
    String youx = entityTemp.getYoux(); // 游戏
    String jianj = entityTemp.getJianj(); // 简介
    String files = entityTemp.getFiles(); // 素材文件
    Date createtime = entityTemp.getCreatetime(); // 游戏时间

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      // 唯一字段，则在此校验
      QueryWrapper<Sucai> wrappername = new QueryWrapper();
      wrappername.eq("name", entityTemp.getName());
      if (sucaiService.list(wrappername).size() > 0) {
        return fail("素材名称 已存在！");
      }
      // before add

      sucaiService.save(entityTemp);
    } else {
      // before edit

      sucaiService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 我的素材删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    Sucai delTemp = sucaiService.getById(id);
    // before del

    // 根据ID删除记录
    sucaiService.removeById(id);
    return success();
  }
}

