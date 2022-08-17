package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lv
 * @create 2022-08-14 1:09
 */
@Mapper
public interface DiscussPostMapper {

    //首页帖子实际不用传入userId，但考虑到之后按照作者查询帖子，这里就整合到一起
    //userId = 0时，查询所有；否则，查询指定userId
    // offset偏移量表示当前是第几行，limit表示每页数量，用于分页功能

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    //查询所有或指定userId的帖子数量
    // @Param注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.
    int selectDiscussPostRows(@Param("userId") int userId);

    //插入贴子到数据库
    int insertDiscussPost(DiscussPost discussPost);

    //根据贴子ID查询帖子对象
    DiscussPost selectDiscussPostById(int id);

    //修改指定id的贴子的评论数量
    int updateCommentCount(int id, int commentCount);

}
