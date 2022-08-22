package com.nowcoder.community.util;

//Redis的工具类，功能简单直接封装成静态的
//提高Key的可用性，编写一个RedisKeyUtil来拼接RedisKey
public class RedisKeyUtil {

    //key的分隔符
    private static final String SPLIT = ":";
    //点赞的前缀
    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    private static final String PREFIX_USER_LIKE = "like:user";
    //关注列表
    private static final String PREFIX_FOLLOWEE = "followee";
    //粉丝列表
    private static final String PREFIX_FOLLOWER = "follower";
    private static final String PREFIX_KAPTCHA = "kaptcha";
    private static final String PREFIX_TICKET = "ticket";
    private static final String PREFIX_USER = "user";

    // 某个实体的赞
    // like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    // 某个用户的赞
    // like:user:userId -> int
    public static String getUserLikeKey(int userId) {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }


    // 某个用户关注的实体
        //这里实体的可以为用户，帖子 等
        // followee:userId:entityType -> zset(entityId,now)
    public static String getFolloweeKey(int userId, int entityType) {
        return PREFIX_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }

    // 某个实体拥有的粉丝
    // follower:entityType:entityId -> zset(userId,now)
    public static String getFollowerKey(int entityType, int entityId) {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    // 登录验证码
    //用字符串验证用户（临时凭证）
    public static String getKaptchaKey(String owner) {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    // 登录的凭证
    public static String getTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    // 缓存的指定用户的key
    public static String getUserKey(int userId) {
        return PREFIX_USER + SPLIT + userId;
    }

}
