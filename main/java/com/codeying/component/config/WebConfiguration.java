package com.codeying.component.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.codeying.component.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 配置类 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Autowired LoginInterceptor loginInterceptor;
  @Autowired WebSiteInterceptor webSiteInterceptor;

  /**
   * 除了LOG IN页面，所有页面都要验证是否LOG IN
   *
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 需要LOG IN才可访问
    String includePatterns =
        "/admin/**,/user/**,/newsInfo/**,/gameData/**,/userShare/**,/games/**,/sucai/**,/tagInfo/**,/notice/**,/userComment/**,";
    registry.addInterceptor(loginInterceptor).addPathPatterns(includePatterns.split(","));

    String includePatterns2 =
        "/webu/personal,/webu/admin/**,/webu/user/**,/webu/newsInfo/**,/webu/gameData/**,/webu/userShare/**,/webu/games/**,/webu/sucai/**,/webu/tagInfo/**,/webu/notice/**,/webu/userComment/**";
    registry.addInterceptor(webSiteInterceptor).addPathPatterns(includePatterns2.split(","));
  }

  @Value("${spring.datasource.url}")
  String url;

  private String sqlserver = "sqlserver";
  private String mysql = "mysql";

  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    String type = url.contains(sqlserver) ? sqlserver : mysql;
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.getDbType(type)));
    return interceptor;
  }
}

