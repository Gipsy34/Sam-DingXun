package com.codeying.service;

import com.codeying.entity.GameData;
import java.util.List;
/** 服务类 */
public interface GameDataService extends MyService<GameData> {

  /**
   * 推荐
   *
   * @param all
   * @return
   */
  public List<GameData> recommend(List<GameData> all);

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

  List<GameData> topN(int n);
}

