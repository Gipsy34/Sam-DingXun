package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 游戏信息库实体类 */
@TableName("tb_games")
public class Games implements Serializable {

  /** 游戏信息库主键 */
  @TableId private String id;

  /** 游戏名 */
  @TableField("name")
  private String name;

  /** 游戏简介 */
  @TableField("youxjj")
  private String youxjj;

  /** 发布公司 */
  @TableField("company")
  private String company;

  /** 玩家人数 */
  @TableField("hots")
  private Integer hots;

  /** 发布时间 */
  @TableField("fabsj")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(
      pattern = "yyyy-MM-dd HH:mm",
      fallbackPatterns = {"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd"})
  private Date fabsj;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getYouxjj() {
    return youxjj;
  }

  public void setYouxjj(String youxjj) {
    this.youxjj = youxjj;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public Integer getHots() {
    return hots;
  }

  public void setHots(Integer hots) {
    this.hots = hots;
  }

  public Date getFabsj() {
    return fabsj;
  }

  public void setFabsj(Date fabsj) {
    this.fabsj = fabsj;
  }
}

