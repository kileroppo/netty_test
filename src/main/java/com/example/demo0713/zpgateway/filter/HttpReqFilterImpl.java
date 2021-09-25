package com.example.demo0713.zpgateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class HttpReqFilterImpl implements  HttpRequestFilter{
    @Override
    public void reqFilter(FullHttpRequest fullReq, ChannelHandlerContext ctx) {
        fullReq.headers().set("ddd",6666);
    }
}
