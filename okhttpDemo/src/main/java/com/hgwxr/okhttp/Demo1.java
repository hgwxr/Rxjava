package com.hgwxr.okhttp;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/24.
 */
public class Demo1 {
  public  static   OkHttpClient client = new OkHttpClient();

  public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    public static  String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public static void   postAsync(String url,String params){
       RequestBody body = RequestBody.create(JSON, params);
       Request request = new Request.Builder().url(url).post(body).build();
          client.newCall(request).enqueue(new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {

              }

              @Override
              public void onResponse(Call call, Response response) throws IOException {
                  System.out.println(Thread.currentThread().getName()+"   ===   "+ response.body().string());
              }
          });
   }
    public static void main(String[] args) {
        String str = null;
        try {
            postAsync("https://api.douban.com/v2/book/1220562","");
            System.out.println(Thread.currentThread().getName()+"==="+str);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
