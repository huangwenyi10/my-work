package com.example.interview.test;

/**
 * Created by Ay on 2017/9/23.
 */
class ThreadException implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("An exception has been captured\n");
        //打印线程id
        System.out.printf("Thread:%s\n",t.getId());
        //打印异常名称 和 异常信息
        System.out.printf("Exception:%s:%s\n",e.getClass().getName(),e.getMessage());
        System.out.println("Stack Trace:");
        e.printStackTrace(System.out);
        System.out.printf("Thread status:%s\n",t.getState());
    }
}

/**
 * 线程类
 */
class ThreadExceptionRun implements Runnable {
    @Override
    public void run() {
         //出现问题
         int i = Integer.parseInt("sss");
         System.out.println(i);
    }
}

public class ThreadExceptionMain {
    public static void main(String[] args){
        //初始化线程
        Thread thread = new Thread(new ThreadExceptionRun());
        thread.setUncaughtExceptionHandler(new ThreadException());
        thread.start();
    }

}
