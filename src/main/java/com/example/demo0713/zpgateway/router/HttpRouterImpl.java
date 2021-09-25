package com.example.demo0713.zpgateway.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class HttpRouterImpl implements  HttppointRouter
{
    @Override
    public String router(List<String> endpoints) {
        int size = endpoints.size();
        Random random = new Random(System.currentTimeMillis());
        return endpoints.get(random.nextInt(size));
    }
}
