package com.codeying.component.chat;

import com.codeying.component.TokenService;
import com.codeying.component.utils.CacheUtil;
import com.codeying.entity.LoginUser;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.server.ServerEndpointConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


//主要是将目前的类定义成一个websocket服务器端,
// 注解的值将被用于监听用户连接的终端访问URL地址,
// 客户端可以通过这个URL来连接到WebSocket服务器端
@Component
//访问服务端的url地址
@ServerEndpoint(value = "/websocket/{id}", configurator = WebSocketServer.GetHttpSessionConfigurator.class)
public class WebSocketServer {

    private ChatService chatService;
    //用户名为key
    public static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    LoginUser user;
    //对话ID
    private String id;//消息列表username_chatlist , 甲乙聊天页面username_2_admin__xxx 3未读消息 username_unread
    private int chatType = 1 ;//对话类型 1 聊天列表 2 双人对话 3 未读消息
    private String hisRole;//对方角色
    private String hisName;//对方名称

    @OnOpen
    public void onOpen(@PathParam(value = "id") String id, Session session, EndpointConfig config) {
        // 用于存储HTTP会话
        List<String> token1 = session.getRequestParameterMap().get("token");
        if(token1!=null){
            String token = token1.get(0);
            if(token!=null){
                CacheUtil cacheUtil = SpringUtils.getBean(CacheUtil.class); // token专用
                user = cacheUtil.getV(token);
            }
        }
        if (user == null || !id.contains(user.getUsername() + "_")) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "尚未LOG IN"));
            } catch (IOException e) {
                log.error("Error closing session", e);
            }
            return;
        }
        this.session = session;
        this.id = id;
        //区分消息类型
        //如果是两人对话
        if (id.contains("_2_")) {
            chatType = 2;
            String his = id.split("_2_")[1];
            if (his.contains("_")) {
                String[] split = his.split("__");
                this.hisRole = split[0];
                this.hisName = split[1];
            }
        }
        webSocketMap.put(id, this);
        chatService = SpringUtils.getBean(ChatService.class);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketMap.remove(id);  //从set中删除
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info(id + " 收到消息:" + message);
        if(chatType == 2){
            //获取对方ID
            String rid = chatService.getUserId(hisName, hisRole);
            if(rid != null){
                //websocket发送给接收人
                boolean res = sendToUser(message,hisName + "_2_" + user.getRole() + "__" + user.getUsername());
                if(!res){
                    chatService.send(user,rid,hisRole, message,false);
                }else {
                    chatService.send(user,rid,hisRole, message,true);
                }
                //未读消息+1
                sendToUser("refresh",hisName + "_chatlist");//通知刷新
                sendToUser("1",hisName + "_unread");//no impl
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误",error);
    }

    /**
     * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
     * @param message
     * @param sendSocketId
     * @throws IOException
     */
    public boolean sendToUser(String message,String sendSocketId) throws IOException {
        WebSocketServer webSocketServer1 = webSocketMap.get(sendSocketId);
        if(webSocketServer1!=null){
            webSocketServer1.sendMessage(message);
            return true;
        }
        return false;
    }

    /**
     * 发送给客户端消息，仅发送给该socket对应的用户
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送信息给所有人
     * @param message
     * @throws IOException
     */
    public void sendToAll(String message) {
        for (String key : webSocketMap.keySet()) {
            try {
                WebSocketServer webSocketServer = webSocketMap.get(key);
                webSocketServer.sendMessage(message);
            } catch (IOException e) {
                log.error("发送错误",e);
            }
        }
    }
    // 配置类，用于获取HTTP会话
    public static class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
        @Override
        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        }
    }

}
