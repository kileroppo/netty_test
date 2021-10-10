package com.example.demo0713.proxydemo.impl;

import com.example.demo0713.proxydemo.PersonService;

public class StaticProxyService implements PersonService {

    private PersonService personService;
    
    public StaticProxyService(PersonService personService) {
        this.personService = personService;
    }
    
    @Override
    public String sayHello() {
        System.out.println("静态 增强");
        return personService.sayHello();
    }
}
