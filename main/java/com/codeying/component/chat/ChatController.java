package com.codeying.component.chat;

import com.codeying.controller.BaseController;
import com.codeying.entity.LoginUser;
import com.codeying.utils.CommonUtils;
import com.codeying.utils.component.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wechat")
public class ChatController extends BaseController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    @ResponseBody
    public ApiResult chat() {
        //根据联系对方用户类型rtype获取用户姓名
        LoginUser loginUser = getCurrentUser();
        List<Chat> chatList = chatService.chatList(loginUser.getId(), loginUser.getRole());
        return successData(chatList);
    }

    @PostMapping
    @ResponseBody
    public ApiResult receiveAndSend(@RequestBody Map<String,String> map) {
        String rid = map.get("rid");
        String rtype = map.get("rtype");
        String action = map.get("action");
        String content = map.get("content");
        if ("send".equals(action)) {
            return send(rid, rtype, content);
        } else if ("receive".equals(action)) {
            return receive(rid, rtype);
        } else if ("unread".equals(action)) {
            LoginUser loginUser = getCurrentUser();
            if(loginUser == null){
                return successData(0);
            }
            int count = chatService.unreadCount(loginUser.getId(),loginUser.getRole(), null);
            return successData(count);
        }
        return success();
    }

    public ApiResult receive(String rid, String rtype) {
        LoginUser loginUser = getCurrentUser();
        if (loginUser == null) {
            return ApiResult.fail("尚未登陆！");
        }
        if (loginUser.getId().equals(rid) && loginUser.getRole().equals(rtype)) {
            return ApiResult.fail("不可和自己聊天！");
        }
        List<Chat> chats = chatService.getChat(rid, rtype, loginUser.getId(), loginUser.getRole());
        return ApiResult.successData(chats);
    }

    public ApiResult send(String rid, String rtype, String content) {
        LoginUser loginUser = getCurrentUser();
        if (loginUser == null) {
            return ApiResult.fail("尚未登陆！");
        }
        chatService.send(loginUser,rid,rtype,content,false);
        return ApiResult.success();
    }
}
