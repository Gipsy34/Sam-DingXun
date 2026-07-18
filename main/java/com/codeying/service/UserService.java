package com.codeying.service;

import com.codeying.entity.User;
import java.util.List;
/** 服务类 */
public interface UserService extends MyService<User> {

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<User> sqlSelectList(User qo);

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
  int sqlUpdate(User e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(User e);
}

