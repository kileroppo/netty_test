package com.example.demo0713.zpgateway.inbound;

import com.example.demo0713.zpgateway.filter.HttpReqFilterImpl;
import com.example.demo0713.zpgateway.outbound.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private List<String> proxyServers;

    private HttpOutboundHandler handler;

    HttpReqFilterImpl  htpfilter =  new HttpReqFilterImpl();


    public HttpInboundHandler(List<String> proxyServers) {
        this.proxyServers = proxyServers;
        this.handler = new HttpOutboundHandler(proxyServers);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)   {
        FullHttpRequest fullRequest = (FullHttpRequest) msg;
        handler.handle(fullRequest, ctx, htpfilter);
    }

}
