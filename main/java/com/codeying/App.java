package com.codeying;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan("com.codeying.mapper")
public class App {

    //文件上传路径
    public static String fileUploadPath = System.getProperty("user.dir") + "\\fileUpload\\";

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
