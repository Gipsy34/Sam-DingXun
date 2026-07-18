package com.codeying.service;

import com.codeying.entity.NewsInfo;
import java.util.List;
/** 服务类 */
public interface NewsInfoService extends MyService<NewsInfo> {

  /**
   * 推荐
   *
   * @param all
   * @return
   */
  public List<NewsInfo> recommend(List<NewsInfo> all);

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<NewsInfo> sqlSelectList(NewsInfo qo);

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
  int sqlUpdate(NewsInfo e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(NewsInfo e);

  List<NewsInfo> topN(int n);
}

