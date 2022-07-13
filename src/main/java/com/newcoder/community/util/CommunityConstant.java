package com.newcoder.community.util;

public interface CommunityConstant {
    //激活成功
    int ACTIVATION_SUCCESS = 0;
    //重复激活
    int ACTIVATION_REPEAT = 1;
    //激活失败
    int ACTIVATION_FAILURE = 2;
    //默认登陆凭证超时时间秒
    int DEFAULT_EXPIRED_SECONDS = 3600*2;
    //记住我登录凭证超时秒
    int REMEMBER_EXPIRED_SECONDS = 3600*24*30;

    //实体类型：帖子
    int ENTITY_TYPE_POST=1;
    //实体类型：评论
    int ENTITY_TYPE_COMMENT=2;
}
