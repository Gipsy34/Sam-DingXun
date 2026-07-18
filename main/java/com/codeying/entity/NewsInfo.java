package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

import java.io.Serializable;
/** 最新Game News实体类 */
@TableName("tb_newsInfo")
public class NewsInfo implements Serializable {

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

  /** 最新Game News主键 */
  @TableId private String id;

  /** 资讯首图 */
  @TableField("showpic")
  private String showpic;

  /** 资讯标题 */
  @TableField("showtitle")
  private String showtitle;

  /** 标签 */
  @TableField("biaoq")
  private String biaoq;

  @TableField(exist = false)
  private List<TagInfo> biaoqLabels;

  @TableField(exist = false)
  private String biaoqLabelStr;
  /** 内容简介 */
  @TableField("showdesc")
  private String showdesc;

  /** 详情 */
  @TableField("showdetail")
  private String showdetail;

  /** 发布时间 */
  @TableField("publishtime")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @DateTimeFormat(
      pattern = "yyyy-MM-dd HH:mm",
      fallbackPatterns = {"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd"})
  private Date publishtime;

  /** 更多信息 */
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

