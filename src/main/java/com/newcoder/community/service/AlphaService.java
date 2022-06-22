package com.newcoder.community.service;

import com.newcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
//@Scope("prototype")//单个或多个实例
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;

    public AlphaService(){
        System.out.println("shilihua");
    }
    @PreDestroy//销毁之前调用
    public void destroy(){
        System.out.println("xiaohui");
    }
    @PostConstruct//在构造后调用下面函数
    public void init(){
        System.out.println("chushihua");
    }
    public String find(){
        return alphaDao.select();
    }
}
