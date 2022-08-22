package com.nowcoder.community.controller;

import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.event.EventProducer;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController implements CommunityConstant {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    //此处未限制未登录点赞，
    // 会抛异常 可以采用之前的全局异常注解，
    // 后期会对代码重构 增加SpringSecurity框架权限控制
    public String like(int entityType,
                       int entityId,
                       int entityUserId,
                       int postId) {
        //获取当前用户
        User user = hostHolder.getUser();

        // 点赞
        likeService.like(user.getId(), entityType, entityId, entityUserId);

        // 实体被点赞的数量
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        // 当前用户对实体的点赞状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);
        // 返回的结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);


        // 触发点赞事件
        if (likeStatus == 1) { //取消点赞不发消息
            Event event = new Event()
                    .setTopic(TOPIC_LIKE)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId", postId); //此处需要帖子id
            eventProducer.fireEvent(event);
        }

        //异步请求，直接利用fastJSON转换成JSON格式返回数据给页面
        return CommunityUtil.getJSONString(0, null, map);
    }

}
