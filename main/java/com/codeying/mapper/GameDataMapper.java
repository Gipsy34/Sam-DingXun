package com.codeying.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.GameData;
import java.util.List;
/** 游戏数据展示 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface GameDataMapper extends BaseMapper<GameData> {

  /**
   * Favorite最多的前几个
   *
   * @param page
   * @return
   */
  List<GameData> topN(Page<GameData> page);

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<GameData> sqlSelectList(GameData qo);

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
  int sqlUpdate(GameData e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(GameData e);
}

