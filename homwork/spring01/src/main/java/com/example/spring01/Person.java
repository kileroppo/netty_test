package com.example.spring01;



import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;



public class Person implements Serializable, BeanNameAware, ApplicationContextAware {



    private String name;
    private int age;
    private char sex;

    private String beanName;
    private ApplicationContext applicationContext;

    public void init(){
        System.out.println("hello...........");
    }

//    public static Person create(){
//        return new Person(102,"KK102",null, null);
//    }

    public void print() {
        System.out.println(this.beanName);
        System.out.println("   context.getBeanDefinitionNames() ===>> "
                + String.join(",", applicationContext.getBeanDefinitionNames()));

    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    public void  setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    public Person(String name, int age, char sex, String beanName, ApplicationContext applicationContext) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.beanName = beanName;
        this.applicationContext = applicationContext;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getBeanName() {
        return beanName;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", beanName='" + beanName + '\'' +
                ", applicationContext=" + applicationContext +
                '}';
    }
}
