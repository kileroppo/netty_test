package com.example.demo0713.zpgateway.inbound;

import com.example.demo0713.zpgateway.filter.HttpReqFilterImpl;
import com.example.demo0713.zpgateway.outbound.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private HttpOutboundHandler handler;

    @Autowired
    HttpReqFilterImpl  htpfilter ;


//    public HttpInboundHandler(List<String> proxyServers) {
//        this.handler = new HttpOutboundHandler(proxyServers);
//    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)   {
        FullHttpRequest fullRequest = (FullHttpRequest) msg;
        handler.handle(fullRequest, ctx, htpfilter);
    }

}
