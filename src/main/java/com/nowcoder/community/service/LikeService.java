package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

//使用redis 就不需要对持久层操作，直接编写Sevice层即可
@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    // 点赞
    //使用事务，实体的赞和事务的赞要统一
    public void like(int userId,
                     int entityType,
                     int entityId,
                     int entityUserId) {

        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //调用工具类拼Key
                    // 被点赞的实体
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                    // 被点赞的用户
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);

                boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);

                //此处需要两次更新操作，所以使用Redis编程事务
                //redis事务特殊，像是一个队列一样，查询操作只有在exec时才会执行.
                // 所以不要在redis事务中查询
                operations.multi();

                if (isMember) {
                    operations.opsForSet().remove(entityLikeKey, userId);
                    operations.opsForValue().decrement(userLikeKey);
                } else {
                    operations.opsForSet().add(entityLikeKey, userId);
                    operations.opsForValue().increment(userLikeKey);
                }

                return operations.exec();
            }
        });
    }

    // 查询某实体点赞的数量
    public long findEntityLikeCount(int entityType, int entityId) {

        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    // 查询某人对某实体的点赞状态
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

    // 查询某个用户获得的赞
    public int findUserLikeCount(int userId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null ? 0 : count.intValue();
    }

}
