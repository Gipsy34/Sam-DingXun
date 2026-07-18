package com.codeying.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.Admin;
import java.util.List;
/** 管理员 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface AdminMapper extends BaseMapper<Admin> {

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

