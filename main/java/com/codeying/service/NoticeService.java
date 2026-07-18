package com.codeying.service;

import com.codeying.entity.Notice;
import java.util.List;
/** 服务类 */
public interface NoticeService extends MyService<Notice> {

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Notice> sqlSelectList(Notice qo);

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
  int sqlUpdate(Notice e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Notice e);
}

