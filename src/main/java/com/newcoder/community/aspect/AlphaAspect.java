package com.newcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

//@Component
//@Aspect
public class AlphaAspect {
    //定义切点
    @Pointcut("execution(* com.newcoder.community.service.*.*(..))")//什么返回值都行，这个包里面的所有类，所有的方法所有的参数
    public void pointcut(){


    }

    @Before("pointcut()")
    public void before(){
        System.out.println("before");
    }

    @After("pointcut()")
    public void after(){
        System.out.println("after");
    }

    @AfterReturning("pointcut()")
    public void afterReturning(){
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }

    @Around("pointcut()")//想在前后都织入逻辑
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("around Before");
        Object obj = joinPoint.proceed();//调用原始对象的方法，可以在他之前干什么，在他之后干什么
        System.out.println("around after");
        return obj;

    }

}
