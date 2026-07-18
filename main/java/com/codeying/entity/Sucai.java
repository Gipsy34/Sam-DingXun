package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 我的素材实体类 */
@TableName("tb_sucai")
public class Sucai implements Serializable {

  /** 我的素材主键 */
  @TableId private String id;

  /** 素材名称 */
  @TableField("name")
  private String name;

  /** 游戏 */
  @TableField("youx")
  private String youx;

  @TableField(exist = false)
  private Games youxFrn;
  /** 简介 */
  @TableField("jianj")
  private String jianj;

  /** 素材文件 */
  @TableField("files")
  private String files;

  /** 游戏时间 */
  @TableField("createtime")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(
      pattern = "yyyy-MM-dd HH:mm",
      fallbackPatterns = {"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd"})
  private Date createtime;

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

  public String getYoux() {
    return youx;
  }

  public void setYoux(String youx) {
    this.youx = youx;
  }

  public Games getYouxFrn() {
    return youxFrn;
  }

  public void setYouxFrn(Games v) {
    this.youxFrn = v;
  }

  public String getJianj() {
    return jianj;
  }

  public void setJianj(String jianj) {
    this.jianj = jianj;
  }

  public String getFiles() {
    return files;
  }

  public void setFiles(String files) {
    this.files = files;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }
}

