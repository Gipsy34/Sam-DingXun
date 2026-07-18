package com.codeying.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.UserComment;
import java.util.List;
/** 评论 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface UserCommentMapper extends BaseMapper<UserComment> {

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<UserComment> sqlSelectList(UserComment qo);

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
  int sqlUpdate(UserComment e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(UserComment e);
}

