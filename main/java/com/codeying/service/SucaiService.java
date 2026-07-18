package com.codeying.service;

import com.codeying.entity.Sucai;
import java.util.List;
/** 服务类 */
public interface SucaiService extends MyService<Sucai> {

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Sucai> sqlSelectList(Sucai qo);

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
  int sqlUpdate(Sucai e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Sucai e);
}

