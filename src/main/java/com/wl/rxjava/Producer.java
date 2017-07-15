package com.wl.rxjava;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */
public class Producer implements  Runnable{
    public List<Product> mProducts;
    private Consumer mConsumer;

    public void setmConsumer(Consumer mConsumer) {
        this.mConsumer = mConsumer;
    }

    public Consumer getmConsumer() {
        return mConsumer;
    }

    private Producer(){
        mProducts=new ArrayList<>();
    }
   public static Producer getInstance(){
        return new Producer();
   }
    public  void  handleCreateProcute(){
        for (int i = 0; i < 10; i++) {
            Product.Builder builder = new Product.Builder();
            Product product = builder.buildName(" produce" + " " + i).buildPrice(10 + i).build();
            mProducts.add(product);
            System.out.println("procuder====="+product.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        noticeConsumer();

    }

    private void noticeConsumer() {
        mConsumer.handleConsumer();
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getmProducts() {
        return mProducts;
    }

    @Override
    public void run() {
        handleCreateProcute();
    }

    static class Product{
        private String name;
        private int price;
        public static class Builder{
            private Product mProduct;
            public Builder(){
                mProduct=new Product();
            }
            public Builder buildName(String name){
                mProduct.setName(name);
                return this;
            }
            public  Builder buildPrice(int price){
               mProduct.setPrice(price);
                return this;
            }
            public Product  build(){
                return mProduct;
            }
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
