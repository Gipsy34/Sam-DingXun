package com.codeying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codeying.component.TokenService;
import com.codeying.entity.*;
import com.codeying.mapper.TagInfoMapper;
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
public class TagInfoServiceImpl extends AbsServiceImpl<TagInfoMapper, TagInfo>
    implements TagInfoService {

  @Override
  public List<TagInfo> sqlSelectList(TagInfo qo) {
    return baseMapper.sqlSelectList(qo);
  }

  @Override
  public int sqlDeleteById(String id) {
    return baseMapper.sqlDeleteById(id);
  }

  @Override
  public int sqlUpdate(TagInfo e) {
    return baseMapper.sqlUpdate(e);
  }

  @Override
  public int sqlSave(TagInfo e) {
    return baseMapper.sqlSave(e);
  }

  /**
   * 获取标签值的列表
   *
   * @param labels
   * @return
   */
  @Override
  public List<TagInfo> getLabelValues(String labels) {
    List<TagInfo> res = new ArrayList<>();
    if (labels == null || labels.isEmpty()) {
      return res;
    }
    String[] ids = labels.split(",");
    for (String id : ids) {
      TagInfo e = getById(id);
      if (e != null) {
        res.add(e);
      }
    }
    return res;
  }
}

