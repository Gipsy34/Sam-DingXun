package com.codeying.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.TagInfo;
import java.util.List;
/** 游戏标签 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface TagInfoMapper extends BaseMapper<TagInfo> {

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
}

