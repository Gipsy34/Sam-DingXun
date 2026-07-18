package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 评论实体类 */
@TableName("tb_userComment")
public class UserComment implements Serializable {

  /** 评论主键 */
  @TableId private String id;

  /** 用户编号 */
  @TableField("userid")
  private String userid;

  /** 用户名 */
  @TableField("username")
  private String username;

  /** 用户角色 */
  @TableField("rolech")
  private String rolech;

  /** 内容 */
  @TableField("content")
  private String content;

  /** 发布时间 */
  @TableField("createtime")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(
      pattern = "yyyy-MM-dd HH:mm",
      fallbackPatterns = {"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd"})
  private Date createtime;

  /** 内容编号 */
  @TableField("ctid")
  private String ctid;

  /** 内容类型 */
  @TableField("type")
  private String type;

  /** 状态 */
  @TableField("status")
  private String status;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRolech() {
    return rolech;
  }

  public void setRolech(String rolech) {
    this.rolech = rolech;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public String getCtid() {
    return ctid;
  }

  public void setCtid(String ctid) {
    this.ctid = ctid;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}

