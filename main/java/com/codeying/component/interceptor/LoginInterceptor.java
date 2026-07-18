package com.codeying.component.interceptor;

import com.codeying.entity.LoginUser;
import com.codeying.utils.component.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.codeying.component.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
/** 后台页面LOG IN拦截器 防止没有LOG IN而访问后台 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
  private Logger log = LoggerFactory.getLogger(getClass());
  @Autowired protected TokenService tokenService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    log.debug("LoginInterceptor:" + request.getRequestURL().toString());
    LoginUser user = tokenService.getLoginInfo();
    if (user != null) { // && !user.getIsWuser() 暂且不用，所有用户均可访问，因为增改的下拉框需要用到，而且网站用户无法进入后台。
      return true;
    }
    // 否则校验不通过
    unauthorized(response);
    return false;
  }

  ObjectMapper objectMapper = new ObjectMapper();

  private void unauthorized(HttpServletResponse response) throws IOException {
    response.setContentType("application/json; charset=utf-8");
    PrintWriter out = response.getWriter();
    ApiResult<String> apiResult = ApiResult.fail("请先LOG IN！");
    apiResult.setCode(302);
    out.print(objectMapper.writeValueAsString(apiResult));
    out.close();
    out.flush();
  }
}

