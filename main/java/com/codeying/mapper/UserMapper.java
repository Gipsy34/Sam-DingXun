package com.codeying.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.User;
import java.util.List;
/** 用户 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface UserMapper extends BaseMapper<User> {

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

