package com.codeying.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.NewsInfo;
import java.util.List;
/** 最新Game News mybatisPlus提供接口，自动实现了各种单表操作 */
public interface NewsInfoMapper extends BaseMapper<NewsInfo> {

  /**
   * Favorite最多的前几个
   *
   * @param page
   * @return
   */
  List<NewsInfo> topN(Page<NewsInfo> page);

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<NewsInfo> sqlSelectList(NewsInfo qo);

  /**
   * 删掉
   *
   * @param id
   * @return
   */
  int sqlDeleteById(String id);

  /**
   * 更新
   *
   * @param e
   * @return
   */
  int sqlUpdate(NewsInfo e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(NewsInfo e);
}

