package com.example.spring01;

import com.example.spring01.aop.City;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Spring01Demo {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Person bean_chendecai = (Person) context.getBean("bean_chendecai");

        System.out.printf("print:"+bean_chendecai.toString());


        Home bean_cdx_home = context.getBean(Home.class);
        System.out.println(bean_cdx_home);
        System.out.println("Home 对象AOP代理后的实际类型："+bean_cdx_home.getClass());
        System.out.println("Home 对象AOP代理后的实际类型是否是 Home 子类："+(bean_cdx_home instanceof Home));
        bean_cdx_home.printHomeInfo();

        City bean = context.getBean(City.class);
        bean.getPerson();
    }
}
