package com.codeying.service;

import com.codeying.entity.Games;
import java.util.List;
/** 服务类 */
public interface GamesService extends MyService<Games> {

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Games> sqlSelectList(Games qo);

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
  int sqlUpdate(Games e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Games e);

  /**
   * 获取标签值的列表
   *
   * @param labels
   * @return
   */
  List<Games> getLabelValues(String labels);
}

