package com.wl.rxjava;

/**
 * Created by Administrator on 2017/6/19.
 */
public class Test {
    public static void main(String[] args) {
        Producer producer = Producer.getInstance();
        Consumer consumer = new Consumer(producer);
        producer.setmConsumer(consumer);

        Thread threadProcuder = new Thread(producer);
        Thread threadConsumer = new Thread(consumer);
        threadProcuder.start();
        threadConsumer.start();
    }
}
