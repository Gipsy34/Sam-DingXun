package com.codeying.component.chat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * chat entity
 */
@TableName("tb_chat")
public class Chat implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 发送者
     */
    private String sid;

    /**
     * 接受者
     */
    private String rid;

    /**
     * 发送者类型
     */
    private String stype;

    /**
     * 接受者类型
     */
    private String rtype;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;

    @TableField(exist = false)
    private String hisName;//对方用户名

    private String flag;//是否已读

    @TableField(exist = false)
    private int unreadCount;//未读数量

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    public String getHisName() {
        return hisName;
    }

    public void setHisName(String hisName) {
        this.hisName = hisName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
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

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", sid='" + sid + '\'' +
                ", rid='" + rid + '\'' +
                ", stype='" + stype + '\'' +
                ", rtype='" + rtype + '\'' +
                ", content='" + content + '\'' +
                ", createtime=" + createtime +
                ", hisName='" + hisName + '\'' +
                ", flag='" + flag + '\'' +
                ", unreadCount=" + unreadCount +
                '}';
    }
}

