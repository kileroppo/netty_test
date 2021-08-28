package com.example.demo0713.zpgateway.outbound;

import com.example.demo0713.zpgateway.filter.HeaderHttpResponseFilter;
import com.example.demo0713.zpgateway.filter.HttpReqFilterImpl;
import com.example.demo0713.zpgateway.filter.HttpRequestFilter;
import com.example.demo0713.zpgateway.filter.HttpResponseFilter;
import com.example.demo0713.zpgateway.router.HttpRouterImpl;
import com.example.demo0713.zpgateway.router.HttppointRouter;
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
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Component
public class HttpOutboundHandler extends ChannelOutboundHandlerAdapter {


    private ExecutorService executorService;

    CloseableHttpAsyncClient httpClient;

    // 后端调整地址
//    private List<String> backendUrls;

    @Value("${gateway.server}")
    private List<String> backendUrls;


    @Autowired
    NamedThreadFactory namedThreadFactory;

    @Autowired
    HttpRouterImpl router;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    // 过滤器
    HttpResponseFilter filter = new HeaderHttpResponseFilter();

    int cores = Runtime.getRuntime().availableProcessors();
    int queueSize = 2048;


     public  HttpOutboundHandler(List<String> backends){

            this.backendUrls = backends.parallelStream().map(this::formateUrl)
                    .collect(Collectors.toList());
             // 设置线程池
             setThreadPool();

             // 设置httpclient
              setHttpClient();

             httpClient.start();
    }



    // 处理方法
    public void handle(final FullHttpRequest fullReq, final ChannelHandlerContext ctx,
                       final HttpRequestFilter filter){
        try {
            // 路由url
            String routerUrl = router.router(backendUrls);
            String realUrl = routerUrl + fullReq.uri();
            // 过滤器  在header中添加字段
            filter.reqFilter(fullReq, ctx);

            // 往线程池提交任务
            Future<?> submit = executorService.submit(() -> fetchGet(fullReq, ctx, realUrl));
            // 获取结果
            Object reqRes = submit.get(2000, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }


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


    // io config
    public IOReactorConfig getIoConfig(){
        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();
        return ioConfig;
    }
    // thread config
    public  ExecutorService setThreadPool(){
        executorService = new ThreadPoolExecutor(cores, cores * 2, 1000, TimeUnit.MILLISECONDS
                , new ArrayBlockingQueue<>(queueSize) ,namedThreadFactory
                , new ThreadPoolExecutor.CallerRunsPolicy());
//        threadPoolExecutor.
        return executorService;
    }
    // http config
    public HttpAsyncClient setHttpClient(){
        httpClient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(getIoConfig())
                .setKeepAliveStrategy((response,context) -> 6000)
                .build();
        return httpClient;
    }
    // foreach url
    private String formateUrl(String backend) {
        return  backend.endsWith("/")? backend.substring(0, backend.length() - 1):backend;
    }

}
