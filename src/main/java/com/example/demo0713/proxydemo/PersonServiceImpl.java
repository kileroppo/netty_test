package com.example.demo0713.proxydemo;

import java.text.SimpleDateFormat;
import java.util.Date;

//业务接口实现类
public class PersonServiceImpl implements PersonService {
    @Override
    public String sayHello() {
        SimpleDateFormat formatter = new SimpleDateFormat();
        System.out.println("hello," + formatter.format(new Date()));
        return formatter.format(new Date());
    }
}
