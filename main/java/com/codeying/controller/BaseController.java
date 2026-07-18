package com.codeying.controller;

import com.codeying.component.chat.ChatService;
import com.codeying.component.TokenService;
import com.codeying.utils.component.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import com.codeying.entity.LoginUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.codeying.service.*;
/** 项目Controller通用父类 */
public class BaseController {
  @Autowired protected TokenService tokenService; // token专用
  @Autowired protected ChatService chatService;
  @Autowired protected AdminService adminService;
  @Autowired protected UserService userService;
  @Autowired protected NewsInfoService newsInfoService;
  @Autowired protected GameDataService gameDataService;
  @Autowired protected UserShareService userShareService;
  @Autowired protected GamesService gamesService;
  @Autowired protected SucaiService sucaiService;
  @Autowired protected TagInfoService tagInfoService;
  @Autowired protected NoticeService noticeService;
  @Autowired protected UserCommentService userCommentService;
  @Autowired protected StarService starService;
  @Autowired protected HttpServletRequest req;

  @Autowired protected HttpServletResponse resp;

  protected <T> ApiResult<T> success() {
    return ApiResult.success();
  }

  protected <T> ApiResult<T> successMsg(String message) {
    return ApiResult.successMsg(message);
  }

  protected <T> ApiResult<T> successData(T data) {
    return ApiResult.successData(data);
  }

  protected <T> ApiResult<T> successMsgAndData(String message, T data) {
    return ApiResult.successMsgAndData(message, data);
  }

  protected <T> ApiResult<T> fail() {
    return ApiResult.fail();
  }

  protected <T> ApiResult<T> fail(String message) {
    return ApiResult.fail(message);
  }

  protected <T> ApiResult<T> fail(String message, T data) {
    return ApiResult.fail(message, data);
  }

  /** 转发 */
  protected ApiResult<String> redirect(String url, String message) {
    ApiResult apiResult = new ApiResult();
    apiResult.setCode(301);
    apiResult.setMessage(message);
    apiResult.setSuccess(false);
    apiResult.setData(url);
    return apiResult;
  }

  protected ApiResult<String> redirect(String url) {
    return redirect(url, "");
  }

  // 获取当前登陆用户
  protected LoginUser getCurrentUser() {
    return tokenService.getLoginInfo();
  }
}

