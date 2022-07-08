package com.newcoder.community;

import com.newcoder.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTests {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter(){
        String text="这里可以⭐赌⭐ 博，可以办证，可以⭐嫖⭐娼⭐，可以开票，哈哈哈哈wdnmd,欢迎来sexytube,欢迎办假证";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
