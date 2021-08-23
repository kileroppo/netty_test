package com.example.spring01.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class Aop1 {

    public void qianzhinotify(){
        System.out.println("这个就是普通的前置通知");
    }

    public void houzhinotify(){
        System.out.println("这个就是个普通后置通知");
    }

    public void aroundNotify(ProceedingJoinPoint point) throws Throwable {

        System.out.println("    ====>around begin ding");
        point.proceed();
        System.out.println("    ====>around finish ding");
    }
}
