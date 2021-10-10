package com.example.demo0713.proxydemo.impl;


import com.example.demo0713.proxydemo.PersonServiceImpl;

import java.lang.reflect.Proxy;

public class JdkProxyService {

    public static Object getInstance(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), PersonServiceImpl.class.getInterfaces(),
                (proxy, method, args) -> {
                    System.out.println("jdk 增强");
                    return method.invoke(target, args);
                });
    }

}
