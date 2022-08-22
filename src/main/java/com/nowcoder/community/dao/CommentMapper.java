package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    //根据评论类型筛选出评论（回复）,后边俩参数控制分页
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    //评论（回复）数量
    int selectCountByEntity(int entityType, int entityId);

    //添加评论
    int insertComment(Comment comment);

    Comment selectCommentById(int id);

}
