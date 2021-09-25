package com.example.demo0713.zpgateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

//    private List<String> proxyServer;
//
//    public HttpInboundInitializer(List<String> proxyServer) {
//        this.proxyServer = proxyServer;
//    }
// new HttpInboundHandler( Arrays.asList("http://127.0.0.1:8803"))



    @Autowired
    HttpInboundHandler httpInboundHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel)  {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast( httpInboundHandler );
    }
}
