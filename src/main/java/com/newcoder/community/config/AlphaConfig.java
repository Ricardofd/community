package com.newcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration//表明是配置类
public class AlphaConfig {

    @Bean
    public SimpleDateFormat simpleDateFormat(){//方法名就是bean的名字
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    }

}
