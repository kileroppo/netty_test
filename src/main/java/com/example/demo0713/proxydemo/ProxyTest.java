package com.example.demo0713.proxydemo;

import com.example.demo0713.proxydemo.impl.CglibProxyService;
import com.example.demo0713.proxydemo.impl.JdkProxyService;
import com.example.demo0713.proxydemo.impl.StaticProxyService;

public class ProxyTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    public static void test1() {
        StaticProxyService proxyService = new StaticProxyService(new PersonServiceImpl());

        proxyService.sayHello();
    }

    public static void test2() {
        PersonServiceImpl personService = new PersonServiceImpl();
        PersonService proxy = (PersonService) JdkProxyService.getInstance(personService);
        proxy.sayHello();
    }


    public static void test3() {
        PersonService personService = new PersonServiceImpl();
        PersonService proxy = (PersonService) CglibProxyService.getInstance(personService);
        proxy.sayHello();

    }
    }
