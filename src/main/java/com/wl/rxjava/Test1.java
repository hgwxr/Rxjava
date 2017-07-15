package com.wl.rxjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/6/27.
 */
public class Test1
{
    public volatile static int i = 0;

    public static class AddThread extends Thread
    {
        @Override
        public void run()
        {
            for (i = 0; i < 10000000; i++)
                ;
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        AddThread at = new AddThread();
        at.start();
       //at.join();
        System.out.println(i);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //ExecutorService executorService1 = new ExecutorService();
    }
}
