package com.codeying.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.Notice;
import java.util.List;
/** 公告 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface NoticeMapper extends BaseMapper<Notice> {

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Notice> sqlSelectList(Notice qo);

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
  int sqlUpdate(Notice e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Notice e);
}

