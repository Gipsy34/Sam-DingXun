package com.codeying.service;

import com.codeying.entity.UserShare;
import java.util.List;
/** 服务类 */
public interface UserShareService extends MyService<UserShare> {

  /**
   * 推荐
   *
   * @param all
   * @return
   */
  public List<UserShare> recommend(List<UserShare> all);

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<UserShare> sqlSelectList(UserShare qo);

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
  int sqlUpdate(UserShare e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(UserShare e);

  List<UserShare> topN(int n);
}

