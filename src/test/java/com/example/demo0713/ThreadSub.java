package com.example.demo0713;

public class ThreadSub implements  Runnable{
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        synchronized (this){
            for (int i = 0; i< 10000; i++){
                System.out.printf("test executorPool:" + i + "\n");
            }
        }

        System.out.printf(String.valueOf(System.currentTimeMillis() - startTime));
    }
}
