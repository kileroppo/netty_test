package com.example.spring01;

import com.example.spring01.aop.City;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;

@Data
public class JuWeiHui implements City {

    @Resource(name = "bean_chendecai")
    Person person;

    @Autowired(required = true)
    Home home;


    @Override
    public void getPerson() {
//        System.out.println("Class1 have " + this.home.getPersons().size() + " students and one is " + this.person);

    }
}
