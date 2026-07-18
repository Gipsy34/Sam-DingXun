package com.codeying.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Star
 */
@TableName("tb_star")
public class Star implements Serializable {

    //===============主图、标题、描述、详细内容，发布时间
    @TableField(exist = false)
    private String showtitle;
    @TableField(exist = false)
    private String showpic;
    @TableField(exist = false)
    private String showdesc;
    @TableField(exist = false)
    private Date publishtime;
    @TableField(exist = false)
    private int count;//Favorite数

    //===============

    /**
     * 主键
     */
    private String id;

    /**
     * Favorite / Like
     */
    private String type;

    /**
     * Favorite项
     */
    private String itemid;

    /**
     * Favorite类型
     */
    private String itemtype;

    /**
     * 用户
     */
    private String userid;

    /**
     * 角色
     */
    private String userrole;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShowtitle() {
        return showtitle;
    }

    public void setShowtitle(String showtitle) {
        this.showtitle = showtitle;
    }

    public String getShowpic() {
        return showpic;
    }

    public void setShowpic(String showpic) {
        this.showpic = showpic;
    }

    public String getShowdesc() {
        return showdesc;
    }

    public void setShowdesc(String showdesc) {
        this.showdesc = showdesc;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }


}

