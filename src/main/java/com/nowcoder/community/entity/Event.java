package com.nowcoder.community.entity;

import java.util.HashMap;
import java.util.Map;

public class Event {

    //事件类型
    private String topic;
    //消息发送方Id，1为管理消息
    private int userId;
    //实体类型，点赞，评论，关注
    private int entityType;
    //对应的实体id
    private int entityId;
    //接收方Id（作者）
    private int entityUserId;
    //预留的其他数据容器（使其具有扩展性）
    private Map<String, Object> data = new HashMap<>();

    public String getTopic() {
        return topic;
    }

    //改造set方法，使其可以链式调用
    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public Event setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public Event setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return entityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        this.entityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

}
