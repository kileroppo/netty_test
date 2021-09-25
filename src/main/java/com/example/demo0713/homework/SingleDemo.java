package com.example.demo0713.homework;

/*
 * @param null
 * @return 单列演示
 * @author killer
 * @date 2021-08-22 16:51
 */
public class SingleDemo {

    public static SingleDemo instance =null;

    public static  class LazyHolder{
        public static final SingleDemo INSTANCE = new SingleDemo();
    }

    //
    // 写法1
    public static SingleDemo getInstance(){
         if (null ==null){
             instance = new SingleDemo();
         }
         return instance;
    }
    // 写法2
    public static  synchronized SingleDemo   getInstance2(){
        if (null ==null){
            instance = new SingleDemo();
        }
        return instance;
    }
    // 写法3 double check
    public static  synchronized SingleDemo   getInstance3(){
        if (null ==null){
            synchronized (SingleDemo.class){
                if (null == instance) {
                    instance = new SingleDemo();
                }
            }
        }
        return instance;
    }

    // 写法4 静态内部类
    private SingleDemo (){}
    public static final SingleDemo getInstance4() {
        return LazyHolder.INSTANCE;
    }
}
