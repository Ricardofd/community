package com.newcoder.community;

import com.newcoder.community.dao.CommentMapper;
import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.dao.LoginTicketMapper;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.Comment;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.LoginTicket;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.CommentService;
import com.newcoder.community.util.CommunityConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        user= userMapper.selectByName("liubei");
        System.out.println(user);
        user= userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("test1");
        user.setPassword("1234");
        user.setSalt("abc");
        user.setEmail("sdfds@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());

        int rows=userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }

    @Test
    public void updateUser(){
        int rows= userMapper.updateStatus(150,1);
        System.out.println(rows);

        rows= userMapper.updateHeader(150,"sdfasdfasdfdasf");
        System.out.println(rows);

        rows= userMapper.updatePassword(150,"passwd");
        System.out.println(rows);

    }

    @Test
    public void testSelectPosts(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(149,0,10);
        for(DiscussPost post:list){
            System.out.println(post);
        }
        int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }



    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis()+1000*60*10));
        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket.getTicket());
        System.out.println(loginTicket.getUserId());
        System.out.println(loginTicket.getStatus());
        loginTicketMapper.updateStatus(loginTicket.getTicket(),0);
        loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket.getTicket());
        System.out.println(loginTicket.getUserId());
        System.out.println(loginTicket.getStatus());
    }
    @Test
    public void testInsertDiscussPost(){
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(777);
        discussPost.setTitle("lalalalalalala");
        discussPost.setCreateTime(new Date());
        discussPost.setContent("abcdefghijklmn");
        discussPost.setType(1);
        discussPost.setStatus(1);
        discussPost.setCommentCount(100);
        discussPost.setScore(4396);
        int rows=discussPostMapper.insertDiscussPost(discussPost);

        System.out.println(rows);
        System.out.println(discussPost.getUserId());
    }

    @Autowired
    private CommentMapper commentMapper;
    @Test
    public void testCommentMapper(){
        List<Comment> comment = commentMapper.selectCommentsByEntity(ENTITY_TYPE_POST,228,0,100);
        int abc=commentMapper.selectCountByEntity(ENTITY_TYPE_COMMENT,12);
        System.out.println(comment);
        System.out.println(abc);
    }
    @Autowired
    private CommentService commentService;
    @Test
    public void testCommentService(){
        List<Comment> comment = commentService.findCommentsByEntity(ENTITY_TYPE_POST,0,0,5);
        int abc=commentService.findCommentCount(ENTITY_TYPE_COMMENT,12);
        System.out.println(comment);
        System.out.println(abc);
    }

    @Test
    public void testDiscussPostMapper(){
        DiscussPost post = discussPostMapper.selectDiscussPostById(289);
        System.out.println(post);
        List<DiscussPost> post2 = discussPostMapper.selectDiscussPosts(174,0,10);
        System.out.println(post2);
    }

}
