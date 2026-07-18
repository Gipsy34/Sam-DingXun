package com.codeying.service;

import com.codeying.entity.Admin;
import java.util.List;
/** 服务类 */
public interface AdminService extends MyService<Admin> {

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Admin> sqlSelectList(Admin qo);

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
  int sqlUpdate(Admin e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Admin e);
}

