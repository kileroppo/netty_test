package com.example.demo0713.conc0819;

import java.util.concurrent.CompletableFuture;

import static jodd.util.ArraysUtil.join;

public class CompleteableFutureDemo {

    //1 结果转换
    public static void main(String[] args) {

        String join = CompletableFuture.supplyAsync(() -> "Hello").thenApplyAsync(v -> v + "World").join();
        System.out.println(join);
        // 2 消费
        CompletableFuture.supplyAsync(()->"111111").thenAccept(v->{
            System.out.println("consumer:");
            System.out.printf("2:" + v +"\n");
        });
        // 3 组合
        String result3 = CompletableFuture.supplyAsync(
                () -> {
                    try {
//                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "aaaaaa";
                }
        ).thenCombine(
                CompletableFuture.supplyAsync(
                        () -> "bbbbbb"
                ), (s1, s2) -> s1 + " " + s2).join();
        System.out.println("res:" + result3);
        // 4 竞争
        String res4 = CompletableFuture.supplyAsync(() -> "hi boy")
                .applyToEither(CompletableFuture.supplyAsync(()->"hi girl"), s-> s  ).join();
        System.out.println("res4:"+ res4);
        // 5  补偿异常
      String res5 =  CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(1000);
            }catch (InterruptedException ingored) {
            }
            if(true){
                throw new RuntimeException("这是错误信息测试");
            }
          return "正常运行";
      }).exceptionally(e->{
            System.out.printf(e.getMessage()+"\n");
            return "收到错误信息 返回处理结果";
        }).join();
        System.out.println(res5);
    }






}
