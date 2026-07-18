package com.codeying.service;

import com.codeying.entity.TagInfo;
import java.util.List;
/** 服务类 */
public interface TagInfoService extends MyService<TagInfo> {

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<TagInfo> sqlSelectList(TagInfo qo);

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
  int sqlUpdate(TagInfo e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(TagInfo e);

  /**
   * 获取标签值的列表
   *
   * @param labels
   * @return
   */
  List<TagInfo> getLabelValues(String labels);
}

