package com.example.demo0713.zpgateway.outbound;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class NamedThreadFactory implements ThreadFactory {

    // thread 线程组
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    // name 前缀
    private final String namePrefix;
    // 是否后台运行
    private final boolean daemon;
    
    public NamedThreadFactory(String namePrefix, boolean daemon) {
        this.daemon = daemon;
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix;
    }
    
    public NamedThreadFactory(String namePrefix) {
        this(namePrefix, false);
    }
    
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + "-thread-" + threadNumber.getAndIncrement(), 0);
        t.setDaemon(daemon);
        return t;
    }
}