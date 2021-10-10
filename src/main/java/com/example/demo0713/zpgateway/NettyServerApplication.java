package com.example.demo0713.zpgateway;

import com.example.demo0713.zpgateway.inbound.InboundServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class NettyServerApplication {

    //1 client 传入请求的ip port  后端收到调inboundserver方法

    //2 在inbound方法里面 调用outbound(把inboundserver调成netty版)
    @Autowired
    InboundServer inboundServer;

    public static void main(String[] args) {
//        SpringApplication.run(NettyServerApplication.class, args);

        // 1 获取多个真正的后端请求端口地址
//        InboundServer inboundServer = new InboundServer(Integer.parseInt(proxyPort), proxyServers);
//        inboundServer.handlerReq();
        // 2 把网关代理端口和真正地址放入inboundserver
    }
}
