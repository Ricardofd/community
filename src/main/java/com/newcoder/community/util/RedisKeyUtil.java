package com.newcoder.community.util;

public class RedisKeyUtil {
    //用来拼key，拼好了再传到redis，太简单了不用容器管理
    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";
    private static final String PREFIX_USER_LIKE = "like:user";
    //某个实体的赞
    //like:entity:entityType:entityId -> set(userId(谁点的赞）),set能统计赞
    public static String getEntityLikeKey(int entityType, int entityId){
        return PREFIX_ENTITY_LIKE+SPLIT+entityType+SPLIT+entityId;
    }
    // 某个用户的赞
    // like:user:userId -> int
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }


}
