package com.codeying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.codeying.component.chat.Chat;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChatMapper extends BaseMapper<Chat> {

    @Select("select sid,rid,stype,rtype from tb_chat " +
            "where sid=#{id} and stype=#{role} or rid=#{id} and rtype = #{role} " +
            "group by sid,rid,stype,rtype")
    List<Chat> chatList(@Param("id")String id, @Param("role")String role);

    @Select("select username from tb_${stype} where id = #{sid}")
    String getUserName(@Param("sid") String sid, @Param("stype") String stype);

    @Select("select id from tb_${stype} where username = #{username}")
    String getUserId(String username, String stype);
}
