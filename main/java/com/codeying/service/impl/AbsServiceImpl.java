package com.codeying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codeying.service.MyService;
public abstract class AbsServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements MyService<T>{

    /**
     * 获取唯一字段值
     *
     * @param col
     * @param val
     * @return
     */
    public T getBy(String col, String val) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.eq(col, val);
        return getOne(wrapper);
    }

}
