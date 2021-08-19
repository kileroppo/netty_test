package com.example.demo0713.zpgateway;

import com.example.demo0713.zpgateway.inbound.InboundServer;

import java.util.Arrays;
import java.util.List;

public class NettyServerApplication {

    //1 client 传入请求的ip port  后端收到调inboundserver方法

    //2 在inbound方法里面 调用outbound(把inboundserver调成netty版)

    public static void main(String[] args) {
        String proxyPort = System.getProperty("proxyPort","8888");
        List<String> proxyServers = Arrays.asList("http://127.0.0.1:8803");
        // 1 获取多个真正的后端请求端口地址
        InboundServer inboundServer = new InboundServer(Integer.parseInt(proxyPort), proxyServers);
        inboundServer.handlerReq();
        // 2 把网关代理端口和真正地址放入inboundserver
    }
}
