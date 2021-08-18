package com.example.demo0713.zpgateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpRequestFilter {


    void reqFilter(FullHttpRequest fullReq, ChannelHandlerContext ctx);
}
