package com.example.demo0713.lammbda.demo1;

import com.example.demo0713.lammbda.demo1.Cook;

public class Demo05InvokeCook {
    public static void main(String[] args) { 
        // TODO 请在此使用Lambda【标准格式】调用invokeCook方法
          invokeCook(() -> {
              System.out.println("“吃饭啦！”");
          });
    }
    private static void invokeCook(Cook cook) {
        cook.makeFood();
    }
}