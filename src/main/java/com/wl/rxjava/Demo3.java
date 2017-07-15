package com.wl.rxjava;

/**
 * Created by Administrator on 2017/6/27.
 */
public class Demo3 {

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {

            @Override
            public synchronized void run() {
                for (int i = 0; ; i++) {
                    String x = Thread.currentThread().getName() + " 数据" + i + "  输出";
                    for (int j = 0; j < x.length(); j++) {
                        System.out.print(x.charAt(j));
                    }
                    System.out.println();

                }
            }
        };
        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}
