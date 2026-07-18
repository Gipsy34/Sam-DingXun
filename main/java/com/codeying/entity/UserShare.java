package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 游戏攻略分享实体类 */
@TableName("tb_userShare")
public class UserShare implements Serializable {

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

  /** 游戏攻略分享主键 */
  @TableId private String id;

  /** 首图 */
  @TableField("showpic")
  private String showpic;

  /** 分享标题 */
  @TableField("showtitle")
  private String showtitle;

  /** 游戏 */
  @TableField("youx")
  private String youx;

  @TableField(exist = false)
  private Games youxFrn;
  /** 发布人 */
  @TableField("fabr")
  private String fabr;

  @TableField(exist = false)
  private User fabrFrn;
  /** 简介 */
  @TableField("showdesc")
  private String showdesc;

  /** 详情 */
  @TableField("showdetail")
  private String showdetail;

  /** 发布时间 */
  @TableField("publishtime")
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  @DateTimeFormat(
      pattern = "yyyy-MM-dd HH:mm",
      fallbackPatterns = {"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd"})
  private Date publishtime;

  /** 操作视频 */
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

  public String getFabr() {
    return fabr;
  }

  public void setFabr(String fabr) {
    this.fabr = fabr;
  }

  public User getFabrFrn() {
    return fabrFrn;
  }

  public void setFabrFrn(User v) {
    this.fabrFrn = v;
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

