package com.example.demo0713.zpgateway.inbound;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class InboundServer {

//    private int port;
//    private List<String> proxyServer ;
//
//
//    public InboundServer(int port,  List<String> proxyServer){
//        this.port = port;
//        this.proxyServer = proxyServer;
//    }

    @Autowired
    HttpInboundInitializer httpInboundInitializer;

    public void handlerReq()  {

        try {
            ServerBootstrap boot = setNettyBoot();
            // 2 启动nioserver
            Channel channel = boot.bind(8888).sync().channel();
            System.out.println("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + 8888 + '/');
            channel.closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 3 nettyServer中调用传入outbound处理类 并带上传入的port和proxyServer
    }

    public ServerBootstrap setNettyBoot() {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(24);
        ServerBootstrap boot = new ServerBootstrap();
        try {
            // 用于存放未完成3次握手的最大数量
            boot.option(ChannelOption.SO_BACKLOG, 128)
                    // tcp会主动探测空闲连接的有效性 间隔7200s
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // 设置接收和发送缓存区的大小
                    .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                    /*
                     *  nagle优化 把多个小数据包 组成一个大的数据包才发送
                     *  为true 表示关闭nagel优化
                     */
                    .option(ChannelOption.TCP_NODELAY, true)
                    /*
                     *   是否重用4次挥手 但客户端还在time wait转态的连接
                     */
                    .option(ChannelOption.SO_REUSEADDR, true)
                    // 重用缓冲区
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    //  允许多个进程占用同一个端口
                    .option(EpollChannelOption.SO_REUSEPORT, true);

            boot.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(httpInboundInitializer);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

        return boot;
    }
}
