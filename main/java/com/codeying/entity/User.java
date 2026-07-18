package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 用户实体类 */
@TableName("tb_user")
public class User extends LoginUser implements Serializable {

  public User() {
    role = "user";
    rolech = "用户";
    isWuser = true;
  }

  /** 用户主键 */
  @TableId private String id;

  /** 用户名 */
  @TableField("username")
  private String username;

  /** 密码 */
  @TableField("password")
  private String password;

  /** 姓名 */
  @TableField("name")
  private String name;

  /** 头像 */
  @TableField("avatar")
  private String avatar;

  /** 性别 */
  @TableField("gender")
  private String gender;

  /** 年龄 */
  @TableField("age")
  private Integer age;

  /** 电话 */
  @TableField("tele")
  private String tele;

  /** 游戏标签 */
  @TableField("biaoq")
  private String biaoq;

  @TableField(exist = false)
  private List<TagInfo> biaoqLabels;

  @TableField(exist = false)
  private String biaoqLabelStr;
  /** 喜欢的游戏 */
  @TableField("gamesId")
  private String gamesId;

  @TableField(exist = false)
  private List<Games> gamesIdLabels;

  @TableField(exist = false)
  private String gamesIdLabelStr;
  /** 账号总价值 */
  @TableField("youx")
  private Double youx;

  /** 总游戏时长 */
  @TableField("gameh")
  private Double gameh;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getTele() {
    return tele;
  }

  public void setTele(String tele) {
    this.tele = tele;
  }

  public String getBiaoq() {
    return biaoq;
  }

  public void setBiaoq(String biaoq) {
    this.biaoq = biaoq;
  }

  public List<TagInfo> getBiaoqLabels() {
    return biaoqLabels;
  }

  public String getBiaoqLabelStr() {
    return biaoqLabelStr;
  }

  public void setBiaoqLabels(List<TagInfo> v) {
    this.biaoqLabels = v;
    if (v == null) return;
    String s = "";
    for (TagInfo label : this.biaoqLabels) {
      if (s.length() > 0) {
        s += ",";
      }
      s += label.getName();
    }
    biaoqLabelStr = s;
  }

  public String getGamesId() {
    return gamesId;
  }

  public void setGamesId(String gamesId) {
    this.gamesId = gamesId;
  }

  public List<Games> getGamesIdLabels() {
    return gamesIdLabels;
  }

  public String getGamesIdLabelStr() {
    return gamesIdLabelStr;
  }

  public void setGamesIdLabels(List<Games> v) {
    this.gamesIdLabels = v;
    if (v == null) return;
    String s = "";
    for (Games label : this.gamesIdLabels) {
      if (s.length() > 0) {
        s += ",";
      }
      s += label.getName();
    }
    gamesIdLabelStr = s;
  }

  public Double getYoux() {
    return youx;
  }

  public void setYoux(Double youx) {
    this.youx = youx;
  }

  public Double getGameh() {
    return gameh;
  }

  public void setGameh(Double gameh) {
    this.gameh = gameh;
  }
}

