package com.example.demo0713.executorpool;

import jodd.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class DataRecordHandler {

    public static ExecutorService executorService;
    public static final int cores = Runtime.getRuntime().availableProcessors();
    public static final long keepAliveTime = 1000;
    public static final int queueSize = 2048;
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("xh-pool-%d").get();

    public static ExecutorService pool = new ThreadPoolExecutor(cores,cores,keepAliveTime
    ,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(queueSize),namedThreadFactory
            ,new ThreadPoolExecutor.CallerRunsPolicy());

    public static ExecutorService getexecPool(){
        return  pool;
    }
}
