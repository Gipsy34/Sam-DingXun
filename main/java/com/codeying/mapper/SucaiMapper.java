package com.codeying.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.entity.Sucai;
import java.util.List;
/** 我的素材 mybatisPlus提供接口，自动实现了各种单表操作 */
public interface SucaiMapper extends BaseMapper<Sucai> {

  /**
   * 查询
   *
   * @param qo
   * @return
   */
  List<Sucai> sqlSelectList(Sucai qo);

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
  int sqlUpdate(Sucai e);

  /**
   * 保存
   *
   * @param e
   * @return
   */
  int sqlSave(Sucai e);
}

