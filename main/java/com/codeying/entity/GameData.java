package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 游戏数据展示实体类 */
@TableName("tb_gameData")
public class GameData implements Serializable {

  // ======================
  @TableField(exist = false)
  private int starCount;

  @TableField(exist = false)
  private int praiseCount;

  @TableField(exist = false)
  private String smallTip; // 提示

  @TableField(exist = false)
  private double recommendScore; // 推荐系数
  // ======================

  /** 游戏数据展示主键 */
  @TableId private String id;

  /** 首图 */
  @TableField("showpic")
  private String showpic;

  /** 标题 */
  @TableField("showtitle")
  private String showtitle;

  /** 游戏 */
  @TableField("gameid")
  private String gameid;

  @TableField(exist = false)
  private Games gameidFrn;
  /** 用户 */
  @TableField("yongh")
  private String yongh;

  @TableField(exist = false)
  private User yonghFrn;
  /** 标签 */
  @TableField("biaoq")
  private String biaoq;

  @TableField(exist = false)
  private List<TagInfo> biaoqLabels;

  @TableField(exist = false)
  private String biaoqLabelStr;
  /** 描述 */
  @TableField("showdesc")
  private String showdesc;

  /** 我的成就详情 */
  @TableField("showdetail")
  private String showdetail;

  /** 游戏时长H */
  @TableField("hours")
  private Integer hours;

  /** 发布时间 */
  @TableField("publishtime")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(
      pattern = "yyyy-MM-dd HH:mm",
      fallbackPatterns = {"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd"})
  private Date publishtime;

  /** 展示 */
  @TableField("vv")
  private String vv;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getShowpic() {
    return showpic;
  }

  public void setShowpic(String showpic) {
    this.showpic = showpic;
  }

  public String getShowtitle() {
    return showtitle;
  }

  public void setShowtitle(String showtitle) {
    this.showtitle = showtitle;
  }

  public String getGameid() {
    return gameid;
  }

  public void setGameid(String gameid) {
    this.gameid = gameid;
  }

  public Games getGameidFrn() {
    return gameidFrn;
  }

  public void setGameidFrn(Games v) {
    this.gameidFrn = v;
  }

  public String getYongh() {
    return yongh;
  }

  public void setYongh(String yongh) {
    this.yongh = yongh;
  }

  public User getYonghFrn() {
    return yonghFrn;
  }

  public void setYonghFrn(User v) {
    this.yonghFrn = v;
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

  public String getShowdesc() {
    return showdesc;
  }

  public void setShowdesc(String showdesc) {
    this.showdesc = showdesc;
  }

  public String getShowdetail() {
    return showdetail;
  }

  public void setShowdetail(String showdetail) {
    this.showdetail = showdetail;
  }

  public Integer getHours() {
    return hours;
  }

  public void setHours(Integer hours) {
    this.hours = hours;
  }

  public Date getPublishtime() {
    return publishtime;
  }

  public void setPublishtime(Date publishtime) {
    this.publishtime = publishtime;
  }

  public String getVv() {
    return vv;
  }

  public void setVv(String vv) {
    this.vv = vv;
  }

  public int getStarCount() {
    return starCount;
  }

  public void setStarCount(int starCount) {
    this.starCount = starCount;
  }

  public int getPraiseCount() {
    return praiseCount;
  }

  public void setPraiseCount(int praiseCount) {
    this.praiseCount = praiseCount;
  }

  public String getSmallTip() {
    return smallTip;
  }

  public void setSmallTip(String smallTip) {
    this.smallTip = smallTip;
  }

  public double getRecommendScore() {
    return recommendScore;
  }

  public void setRecommendScore(double recommendScore) {
    this.recommendScore = recommendScore;
  }
}

