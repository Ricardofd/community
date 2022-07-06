package com.newcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//这个注解可以表型哪些方法登陆了才能用
@Target(ElementType.METHOD)//注解用来描述方法
@Retention(RetentionPolicy.RUNTIME)//程序运行的时候
public @interface LoginRequired {

}
