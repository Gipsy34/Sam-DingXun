package com.codeying.component.chat;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codeying.entity.LoginUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface ChatService extends IService<Chat> {

    String getUserName(String id, String utype);

    String getUserId(String username, String utype);

    List<Chat> chatList(String id, String role);

    List<Chat> getChat(String rid, String rtype, String id, String role);
    
    int unreadCount(String userId,String role,String hisId);

    void send(LoginUser user, String rid, String rtype, String content,boolean isread);
}

