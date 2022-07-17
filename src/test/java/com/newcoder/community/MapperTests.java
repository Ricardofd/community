package com.newcoder.community;

import com.newcoder.community.dao.*;
import com.newcoder.community.entity.*;
import com.newcoder.community.service.CommentService;
import com.newcoder.community.service.MessageService;
import com.newcoder.community.util.CommunityConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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
    @Autowired
    private MessageMapper messageMapper;
    @Test
    public void testMessageMapper(){
        List<Message> list = messageMapper.selectConversations(111,0,20);
        for(Message message:list){
            System.out.println(message);
        }
        int abc = messageMapper.selectConversationCount(111);
        System.out.println(abc);
        List<Message> list2 = messageMapper.selectLetters("111_112",0,10);
        for(Message message2:list2){
            System.out.println(message2);
        }
        abc = messageMapper.selectLetterCount("111_112");
        System.out.println(abc);
        abc = messageMapper.selectLetterUnreadCount(131,"111_131");
        System.out.println(abc);


    }
    @Autowired
    private MessageService messageService;

    @Test
    public void testMessageService(){
        Message mse = new Message();
        mse.setCreateTime(new Date());
        mse.setContent("dsfjsldkfjdsf");
        mse.setToId(12);
        mse.setFromId(234);
        mse.setConversationId("12_234");
        mse.setStatus(0);
        int a = messageService.addMessage(mse);

        List<Integer> abc = new ArrayList<>();


    }

    @Test
    public void testMessageService2(){
        List<Integer> abc = new ArrayList<>();
        abc.add(355);
        messageService.readMessage(abc);
    }



}
