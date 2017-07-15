package com.wl.rxjava;

import java.util.Iterator;

/**
 * Created by Administrator on 2017/6/19.
 */
public class Consumer  implements  Runnable {
    private  Producer mProducer;

   public Consumer(Producer producer){
       this.mProducer=producer;
   }
    public void handleConsumer(){
        Iterator<Producer.Product> iterator = mProducer.getmProducts().iterator();
        while (iterator.hasNext()) {
            Producer.Product next = iterator.next();
            System.out.println("consumer====="+next.toString());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mProducer.getmProducts().clear();
        //
        noticeProducer();
    }

    private void noticeProducer() {
         mProducer.handleCreateProcute();
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
          if (mProducer.getmProducts().isEmpty()){
              try {
                  wait();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }else
              handleConsumer();
    }
}
