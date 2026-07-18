package com.codeying.component.config;

import com.codeying.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 全局错误处理
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Object IllegalArgumentException(Exception e) {
        //错误日志
        logger.error(getStackTraceInfo(e));
        return fail(e.getMessage());
    }


    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseBody
    public Object myHandler(NoResourceFoundException e) {
        // 错误日志
        logger.error(e.getMessage());
        return null;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object myHandler(Exception e) {
        //错误日志
        logger.error(getStackTraceInfo(e));
        return fail("系统繁忙，请稍后再试或联系管理员");
    }

    /**
     * 获取e.printStackTrace() 的具体信息，赋值给String 变量，并返回
     *
     * @param e Exception
     * @return e.printStackTrace() 中 的信息
     */
    public static String getStackTraceInfo(Exception e) {

        StringWriter sw = null;
        PrintWriter pw = null;

        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);//将出错的栈信息输出到printWriter中
            pw.flush();
            sw.flush();

            return sw.toString();
        } catch (Exception ex) {

            return "发生错误";
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }

    }
}
