package com.example.demo0713;

import org.springframework.stereotype.Component;

@Component("someBean")
public class SomeBean {

    public void doSomething(){
        System.out.println("你好呀 spring！！！");
    }
}
