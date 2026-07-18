package com.codeying.component.chat;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codeying.entity.LoginUser;
import com.codeying.mapper.ChatMapper;
import com.codeying.utils.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {
    @Override
    public int unreadCount(String userId,String role, String hisId) {
        QueryWrapper<Chat> wrapper = new QueryWrapper<>();
        wrapper.eq("flag","N");
        wrapper.eq("rid",userId);
        wrapper.eq("rtype",role);
        wrapper.eq(hisId!=null,"sid",hisId);
        int res = Math.toIntExact(count(wrapper));
        return res;
    }

    @Override
    public String getUserName(String id, String utype){
        return baseMapper.getUserName(id,utype);
    }

    @Override
    public String getUserId(String username, String utype){
        return baseMapper.getUserId(username,utype);
    }

    /**
     * 聊天列表
     * @param uid
     * @param role
     * @return
     */
    @Override
    public List<Chat> chatList(String uid, String role) {
        List<Chat> list = baseMapper.chatList(uid,role);
        //过滤掉重复的内容
        List<String> chats = new ArrayList<>();
        List<Chat> distinctChat = new ArrayList<>();
        for (Chat chat : list) {
            String str;
            if(uid.equals(chat.getRid()) && role.equals(chat.getRtype()) ){
                str = chat.getSid() + "," + chat.getStype();
            }else {
                str = chat.getRid() + "," + chat.getRtype();
            }
            if(!chats.contains(str)){
                chats.add(str);
                distinctChat.add(chat);
            }
        }
        //填充最新消息和对方姓名
        for (Chat chat : distinctChat) {
            //填充对方username
            String username;
            if(uid.equals(chat.getRid()) && role.equals(chat.getRtype())){
                username = baseMapper.getUserName(chat.getSid() , chat.getStype());
            }else {
                username = baseMapper.getUserName(chat.getRid(), chat.getRtype());
            }
            chat.setHisName(username);
            QueryWrapper<Chat> wrapper = new QueryWrapper<>();
            wrapper.eq("sid",chat.getSid());
            wrapper.eq("rid",chat.getRid());
            wrapper.eq("rtype",chat.getRtype());
            wrapper.eq("stype",chat.getStype());
            wrapper.or();
            wrapper.eq("sid",chat.getRid());
            wrapper.eq("rid",chat.getSid());
            wrapper.eq("rtype",chat.getStype());
            wrapper.eq("stype",chat.getRtype());
            wrapper.orderByDesc("createtime");
            Page<Chat> page = new Page(1,1);
            page = page(page,wrapper);
            List<Chat> newChat = page.getRecords();

            chat.setContent(newChat.get(0).getContent());
            if(chat.getContent()!=null && chat.getContent().length() > 18){
                chat.setContent(chat.getContent().substring(0,18) + "...");//解决消息过长
            }
            chat.setCreatetime(newChat.get(0).getCreatetime());
            //设置未读条数
            chat.setUnreadCount(unreadCount(uid,role,uid.equals(chat.getSid())&&role.equals(chat.getStype()) ? chat.getRid() : chat.getSid()));
        }
        return distinctChat;
    }

    /**
     * 获取某两人的聊天
     * @param id1 他
     * @param type1 他
     * @param id2 我
     * @param type2 我
     * @return
     */
    @Override
    public List<Chat> getChat(String id1, String type1, String id2, String type2) {
        QueryWrapper<Chat> wrapper = new QueryWrapper<>();
        wrapper.eq("sid",id1);
        wrapper.eq("rid",id2);
        wrapper.eq("rtype",type2);
        wrapper.eq("stype",type1);
        wrapper.or();
        wrapper.eq("sid",id2);
        wrapper.eq("rid",id1);
        wrapper.eq("rtype",type1);
        wrapper.eq("stype",type2);
        wrapper.orderByAsc("createtime");

        //设置已读
        //"update tb_chat set flag = 'Y' where rid='"+id2+"' and sid = '"+id1+"' ";
        UpdateWrapper<Chat> wrapper2 = new UpdateWrapper<>();
        wrapper2.eq("sid",id1);
        wrapper2.eq("rid",id2);
        wrapper2.set("flag","Y");
        update(wrapper2);

        return list(wrapper);
    }

    @Override
    public void send(LoginUser user, String rid, String rtype, String content,boolean isread) {
        if(user == null){
            return;
        }
        Chat chat = new Chat();
        chat.setId(CommonUtils.newId());
        chat.setRid(rid);
        chat.setRtype(rtype);
        chat.setSid(user.getId());
        chat.setStype(user.getRole());
        chat.setContent(content);
        chat.setCreatetime(new Date());
        chat.setFlag(isread ? "Y" : "N");
        save(chat);
    }
}

