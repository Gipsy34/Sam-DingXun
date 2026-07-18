package com.codeying.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 抽取service
 */
public interface MyService<T> extends IService<T> {
    T getBy(String col,String val);
}
