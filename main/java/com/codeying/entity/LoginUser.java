package com.codeying.entity;
import com.baomidou.mybatisplus.annotation.TableField;
/** 标记为可LOG IN用户 */
public class LoginUser {

    protected String id;
    protected String username;
    protected String password;
    @TableField(exist = false)
    protected String role;
    @TableField(exist = false)
    protected String rolech;
    @TableField(exist = false)
    protected String avatar;
    @TableField(exist = false)
    protected boolean isWuser;

    public boolean getIsWuser() {
        return isWuser;
    }

    public void setIsWuser(boolean wuser) {
        isWuser = wuser;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRolech() {
        return rolech;
    }

    public void setRolech(String rolech) {
        this.rolech = rolech;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

