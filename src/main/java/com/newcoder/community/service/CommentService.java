package com.newcoder.community.service;

import com.newcoder.community.dao.CommentMapper;
import com.newcoder.community.entity.Comment;
import com.newcoder.community.util.CommunityConstant;
import com.newcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Component
public class CommentService implements CommunityConstant {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    public List<Comment> findCommentsByEntity(int entityType,int entityId,int offset,int limit){
//        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(userId,offset,limit);
//        for(DiscussPost post:list){
//            post.setTitle(sensitiveFilter.filter(post.getTitle()));
//            post.setContent(sensitiveFilter.filter(post.getContent()));
//        }
        List<Comment> list = commentMapper.selectCommentsByEntity(entityType,entityId,offset,limit);
        for(Comment temp:list){
            temp.setContent(sensitiveFilter.filter(temp.getContent()));
        }

        return list;
    }

    public int findCommentCount(int entityType,int entityId){
        return commentMapper.selectCountByEntity(entityType,entityId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment){
        //进行了两次数据库操作，因此需要事务管理保证失败可以回滚
        if(comment==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        //敏感词和教程不一样，我存到数据库里，显示的时候再过滤
        int rows = commentMapper.insertComment(comment);
        //更新帖子的评论数量
        if(comment.getEntityType()==ENTITY_TYPE_POST){
            int count = commentMapper.selectCountByEntity(comment.getEntityType(),comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(),count);
        }

        return rows;
    }
}
