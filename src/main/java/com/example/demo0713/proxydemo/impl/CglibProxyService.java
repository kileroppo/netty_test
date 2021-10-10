package com.example.demo0713.proxydemo.impl;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

public class CglibProxyService {
    public static Object getInstance(Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("cgliab 增强");
            //不能使用method.invoke ,否在会再次进入intercept,造成死循环
            return methodProxy.invokeSuper(o, objects);
        });
        return enhancer.create();
    }
}
