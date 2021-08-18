package com.example.demo0713.zpgateway.router;

import java.util.List;
import java.util.Random;

public class HttpRouterImpl implements  HttppointRouter
{
    @Override
    public String router(List<String> endpoints) {
        int size = endpoints.size();
        Random random = new Random(System.currentTimeMillis());
        return endpoints.get(random.nextInt(size));
    }
}
