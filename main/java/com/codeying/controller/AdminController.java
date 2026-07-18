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

/** 管理员控制器 关于管理员的增删改查操作都在这 */
@Controller
@RequestMapping({"admin", "webu/admin"})
public class AdminController extends BaseController {

  // 管理员列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 分页大小
    // 获取列表数据
    QueryWrapper<Admin> queryWrapper = getQueryWrapper();
    LoginUser loginUser = tokenService.getLoginInfo();
    IPage<Admin> pageInfo = new Page<Admin>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = adminService.page(pageInfo, queryWrapper);

    PagerVO<Admin> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<Admin> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<Admin> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String username = req.getParameter("username");
    if (!StringUtils.isEmpty(username)) {
      paramMap.like("username", username);
    }
    String name = req.getParameter("name");
    if (!StringUtils.isEmpty(name)) {
      paramMap.like("name", name);
    }

    String orderByStr = "id desc"; // 默认根据id降序排序
    // 默认按照id排序
    paramMap.last("order by " + orderByStr);
    return paramMap;
  }

  // 管理员详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    Admin entity = adminService.getById(id);

    return successData(entity);
  }

  // 管理员保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody Admin entityTemp) {
    String id = entityTemp.getId(); // 管理员主键
    String username = entityTemp.getUsername(); // 用户名
    String password = entityTemp.getPassword(); // 密码
    String name = entityTemp.getName(); // 姓名
    String tele = entityTemp.getTele(); // 电话

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      // 唯一字段，则在此校验
      QueryWrapper<Admin> wrapperusername = new QueryWrapper();
      wrapperusername.eq("username", entityTemp.getUsername());
      if (adminService.list(wrapperusername).size() > 0) {
        return fail("用户名 已存在！");
      }
      // before add
      adminService.save(entityTemp);
    } else {
      // before edit
      adminService.updateById(entityTemp);
      if (id.equals(getCurrentUser().getId())
          && getCurrentUser().getUsername().equals(username)) { // 刷新用户信息
        tokenService.updateLoginUser(adminService.getById(id));
      }
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 管理员删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    Admin delTemp = adminService.getById(id);
    // before del
    // 根据ID删除记录
    adminService.removeById(id);
    return success();
  }
}

