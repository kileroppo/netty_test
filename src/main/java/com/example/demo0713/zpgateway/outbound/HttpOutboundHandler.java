package com.example.demo0713.zpgateway.outbound;

import com.example.demo0713.zpgateway.filter.HeaderHttpResponseFilter;
import com.example.demo0713.zpgateway.filter.HttpReqFilterImpl;
import com.example.demo0713.zpgateway.filter.HttpRequestFilter;
import com.example.demo0713.zpgateway.filter.HttpResponseFilter;
import com.example.demo0713.zpgateway.router.HttpRouterImpl;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.util.EntityUtils;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpOutboundHandler extends ChannelOutboundHandlerAdapter {


    private ExecutorService executorService;
    // 后端调整地址
    private List<String> backendUrls;

    CloseableHttpAsyncClient httpClient;

    // router
    HttpRouterImpl router =  new HttpRouterImpl();
    // 过滤器
    HttpResponseFilter filter = new HeaderHttpResponseFilter();

    int cores = Runtime.getRuntime().availableProcessors();
    int queueSize = 2048;


     public  HttpOutboundHandler(List<String> backends){
            this.backendUrls = backends.parallelStream().map(this::formateUrl)
                    .collect(Collectors.toList());

         executorService = new ThreadPoolExecutor(cores, cores * 2, 1000, TimeUnit.MILLISECONDS
                    , new ArrayBlockingQueue<>(queueSize) ,new NamedThreadFactory("proxyService")
                    , new ThreadPoolExecutor.CallerRunsPolicy());
         // IO连接配置
         IOReactorConfig ioConfig = IOReactorConfig.custom()
                 .setConnectTimeout(1000)
                 .setSoTimeout(1000)
                 .setIoThreadCount(cores)
                 .setRcvBufSize(32 * 1024)
                 .build();

         httpClient = HttpAsyncClients.custom().setMaxConnTotal(40)
                 .setMaxConnPerRoute(8)
                 .setDefaultIOReactorConfig(ioConfig)
                 .setKeepAliveStrategy((response,context) -> 6000)
                 .build();
         httpClient.start();
    }

    private String formateUrl(String backend) {
         return  backend.endsWith("/")? backend.substring(0, backend.length() - 1):backend;
    }

    // 处理方法
    public void handle(final FullHttpRequest fullReq, final ChannelHandlerContext ctx,
                       final HttpRequestFilter filter){
        // 路由url
        String routerUrl = this.router.router(backendUrls);
        String realUrl = backendUrls + routerUrl;
        // 过滤器  在header中添加字段
        filter.reqFilter(fullReq, ctx);
        // 往线程池提交任务
        executorService.submit(()->fetchGet(fullReq, ctx, realUrl));
    }

    public void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url){
        HttpGet httpGet = new HttpGet(url);
        httpClient.execute(httpGet, new FutureCallback<HttpResponse>() {
                @SneakyThrows
                @Override
                public void completed(HttpResponse result) {
                    handleResponse(inbound, ctx, result);
                }

                @Override
                public void failed(Exception ex) {
                    httpGet.abort();
                    ex.printStackTrace();
                }

                @Override
                public void cancelled() {
                    httpGet.abort();
                }
            });

    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) throws Exception {
        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length",
                    Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));
            filter.filter(response);


        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }

    }

}
