package com.example.demo0713.lammbda.testThreadJoin;

public class testJoin {

    public static void main(String[] args) {
        Object obj = new Object();
        Thread thread = new Thread();
        thread.yield();
    }


   public  void  testYeild() {
       Object oooj = new Object();
       synchronized (oooj){
              System.out.println("fuid the jion on");
          }
    }



}
