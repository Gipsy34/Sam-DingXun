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

/** 评论控制器 关于评论的增删改查操作都在这 */
@Controller
@RequestMapping({"userComment", "webu/userComment"})
public class UserCommentController extends BaseController {

  // 评论列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 分页大小
    // 获取列表数据
    QueryWrapper<UserComment> queryWrapper = getQueryWrapper();
    LoginUser loginUser = tokenService.getLoginInfo();
    IPage<UserComment> pageInfo = new Page<UserComment>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = userCommentService.page(pageInfo, queryWrapper);

    PagerVO<UserComment> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<UserComment> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<UserComment> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String username = req.getParameter("username");
    if (!StringUtils.isEmpty(username)) {
      paramMap.like("username", username);
    }
    String rolech = req.getParameter("rolech");
    if (!StringUtils.isEmpty(rolech)) {
      paramMap.like("rolech", rolech);
    }
    String content = req.getParameter("content");
    if (!StringUtils.isEmpty(content)) {
      paramMap.like("content", content);
    }
    String createtimeL = req.getParameter("createtimeL");
    String createtimeR = req.getParameter("createtimeR");
    if (!StringUtils.isEmpty(createtimeL)) {
      paramMap.ge("createtime", DateUtil.strToDate(createtimeL));
    }
    if (!StringUtils.isEmpty(createtimeR)) {
      paramMap.le("createtime", DateUtil.strToDate(createtimeR));
    }
    String status = req.getParameter("status");
    if (!StringUtils.isEmpty(status)) {
      paramMap.eq("status", status);
    }

    String orderByStr = "id desc"; // 默认根据id降序排序
    // 默认按照id排序
    paramMap.last("order by " + orderByStr);
    return paramMap;
  }

  // 评论详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    UserComment entity = userCommentService.getById(id);

    return successData(entity);
  }

  // 评论保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody UserComment entityTemp) {
    String id = entityTemp.getId(); // 评论主键
    String userid = entityTemp.getUserid(); // 用户编号
    String username = entityTemp.getUsername(); // 用户名
    String rolech = entityTemp.getRolech(); // 用户角色
    String content = entityTemp.getContent(); // 内容
    Date createtime = entityTemp.getCreatetime(); // 发布时间
    String ctid = entityTemp.getCtid(); // 内容编号
    String type = entityTemp.getType(); // 内容类型
    String status = entityTemp.getStatus(); // 状态

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      createtime = new Date();
      entityTemp.setCreatetime(createtime);
      // before add

      userCommentService.save(entityTemp);
    } else {
      // before edit

      userCommentService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 评论删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    UserComment delTemp = userCommentService.getById(id);
    // before del

    // 根据ID删除记录
    userCommentService.removeById(id);
    return success();
  }
}

