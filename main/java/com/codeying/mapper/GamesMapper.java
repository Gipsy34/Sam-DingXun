package com.codeying.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.Games;
import java.util.List;
/** 游戏信息库 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface GamesMapper extends BaseMapper<Games> {

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
}

