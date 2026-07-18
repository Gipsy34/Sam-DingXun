package com.codeying.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.UserShare;
import java.util.List;
/** 游戏攻略分享 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface UserShareMapper extends BaseMapper<UserShare> {

  /**
   * Favorite最多的前几个
   *
   * @param page
   * @return
   */
  List<UserShare> topN(Page<UserShare> page);

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
}

