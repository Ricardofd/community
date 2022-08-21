package com.newcoder.community.dao;

import com.newcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
@Deprecated//不推荐使用的注解
public interface LoginTicketMapper {
    //接口的实现可以用配置类mappper文件夹底下那样实现，也可以用注解，这里用注解
    //里面写字符串，自编给你整成sql
    @Insert({"insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired}) ",
            })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({"select id,user_id,ticket,status,expired " ,
        "from login_ticket ",
        "where ticket=#{ticket} "
    })
    LoginTicket selectByTicket(String ticket);

    //要变成动态sql加if什么的得加上scipt标签
    @Update({"update login_ticket set status=#{status} where ticket=#{ticket}"})
    int updateStatus(String ticket,int status);
}
