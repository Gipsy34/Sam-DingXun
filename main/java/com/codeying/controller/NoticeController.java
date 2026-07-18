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

/** 公告控制器 关于公告的增删改查操作都在这 */
@Controller
@RequestMapping({"notice", "webu/notice"})
public class NoticeController extends BaseController {

  // 公告列表数据
  @RequestMapping("list")
  @ResponseBody
  public ApiResult list(Integer current, Integer size) {
    current = current == null ? 1 : current; // 默认第一页
    size = size == null ? 20 : size; // 分页大小
    // 获取列表数据
    QueryWrapper<Notice> queryWrapper = getQueryWrapper();
    LoginUser loginUser = tokenService.getLoginInfo();
    IPage<Notice> pageInfo = new Page<Notice>().setCurrent(current).setSize(size); // 分页大小
    pageInfo = noticeService.page(pageInfo, queryWrapper);

    PagerVO<Notice> pagerVO = new PagerVO<>(pageInfo); // 可以承载除了page的额外信息

    return successData(pagerVO);
  }

  private QueryWrapper<Notice> getQueryWrapper() {
    // 用于存储查询的条件
    QueryWrapper<Notice> paramMap = new QueryWrapper<>();
    String id = req.getParameter("id");
    paramMap.eq(!StringUtils.isEmpty(id), "id", id);
    String title = req.getParameter("title");
    if (!StringUtils.isEmpty(title)) {
      paramMap.like("title", title);
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

  // 公告详情
  @RequestMapping("detail")
  @ResponseBody
  public ApiResult detail(String id) {
    Notice entity = noticeService.getById(id);

    return successData(entity);
  }

  // 公告保存
  @RequestMapping("save")
  @ResponseBody
  public ApiResult save(@RequestBody Notice entityTemp) {
    String id = entityTemp.getId(); // 公告主键
    String title = entityTemp.getTitle(); // 标题
    String content = entityTemp.getContent(); // 内容
    Date createtime = entityTemp.getCreatetime(); // 发布时间

    // 新增或更新
    if (entityTemp.getId() == null || "".equals(entityTemp.getId())) { // 新增
      id = CommonUtils.newId();
      entityTemp.setId(id);
      createtime = new Date();
      entityTemp.setCreatetime(createtime);
      // 唯一字段，则在此校验
      QueryWrapper<Notice> wrappertitle = new QueryWrapper();
      wrappertitle.eq("title", entityTemp.getTitle());
      if (noticeService.list(wrappertitle).size() > 0) {
        return fail("标题 已存在！");
      }
      // before add

      noticeService.save(entityTemp);
    } else {
      // before edit

      noticeService.updateById(entityTemp);
    }
    return ApiResult.successMsg("操作成功"); // 返回保存成功
  }

  // 公告删除
  @RequestMapping("delete")
  @ResponseBody
  public ApiResult delete(String id) {
    Notice delTemp = noticeService.getById(id);
    // before del

    // 根据ID删除记录
    noticeService.removeById(id);
    return success();
  }
}

