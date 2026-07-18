package com.codeying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codeying.component.TokenService;
import com.codeying.entity.*;
import com.codeying.mapper.GamesMapper;
import com.codeying.service.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/** 服务实现类 */
@Service
public class GamesServiceImpl extends AbsServiceImpl<GamesMapper, Games> implements GamesService {

  @Override
  public List<Games> sqlSelectList(Games qo) {
    return baseMapper.sqlSelectList(qo);
  }

  @Override
  public int sqlDeleteById(String id) {
    return baseMapper.sqlDeleteById(id);
  }

  @Override
  public int sqlUpdate(Games e) {
    return baseMapper.sqlUpdate(e);
  }

  @Override
  public int sqlSave(Games e) {
    return baseMapper.sqlSave(e);
  }

  /**
   * 获取标签值的列表
   *
   * @param labels
   * @return
   */
  @Override
  public List<Games> getLabelValues(String labels) {
    List<Games> res = new ArrayList<>();
    if (labels == null || labels.isEmpty()) {
      return res;
    }
    String[] ids = labels.split(",");
    for (String id : ids) {
      Games e = getById(id);
      if (e != null) {
        res.add(e);
      }
    }
    return res;
  }
}

