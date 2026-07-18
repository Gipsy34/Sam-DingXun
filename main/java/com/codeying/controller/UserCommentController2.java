package com.codeying.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.codeying.utils.CommonUtils;
import com.codeying.utils.component.ApiResult;
import com.codeying.entity.LoginUser;
import com.codeying.entity.UserComment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 详情页获取评论列表
 * 发表评论
 */
@Controller
@RequestMapping("userComment2")
public class UserCommentController2  extends BaseController {

    /**
     * 详情页获取评论列表
     * @param ctid 内容id
     * @return
     */
    @GetMapping("action")
    @ResponseBody
    public ApiResult<List<UserComment>> action(String ctid){
        LambdaQueryWrapper<UserComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserComment::getCtid,ctid);
        wrapper.eq(UserComment::getStatus,"正常");
        wrapper.orderByDesc(UserComment::getId);
        List<UserComment> comments = userCommentService.list(wrapper);
        return successData(comments);
    }

    /**
     * 发布评论
     * @return
     */
    @PostMapping("action")
    @ResponseBody
    public ApiResult saveComment(){
        LoginUser loginUser = getCurrentUser();
        if(loginUser==null){
            return fail("请先LOG IN！");
        }
        UserComment comment = new UserComment();
        comment.setId(CommonUtils.newId());
        comment.setUserid(loginUser.getId());
        comment.setUsername(loginUser.getUsername());
        comment.setRolech(loginUser.getRolech());
        comment.setContent(req.getParameter("content"));//获取评论内容
        comment.setCtid(req.getParameter("ctid"));//获取内容id
        comment.setType(req.getParameter("type"));//获取内容类型
        comment.setCreatetime(new Date());
        comment.setStatus("正常");
        //保存内容
        userCommentService.save(comment);
        return success();
    }

}
