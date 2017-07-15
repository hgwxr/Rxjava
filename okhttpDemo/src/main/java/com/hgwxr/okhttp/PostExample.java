package com.hgwxr.okhttp;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/20.
 */
public class PostExample {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
   public void  postWay(String url){
       RequestBody requestBody = RequestBody.create(JSON, "{}");
       //RequestBody.create(JSON)

       Request builder = new Request.Builder().url(url).post(requestBody).build();
       client.newCall(builder).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               System.out.println("e = " + e.toString());
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               System.out.println(response.body().string());
           }
       });
   }
    String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }

    public static void main(String[] args) throws IOException {
        PostExample example = new PostExample();
      /*  String json = example.bowlingJson("Jesse", "Jake");
        String response = example.post("http://www.roundsapp.com/post", json);
        System.out.println(response);*/
      example.postWay("https://test2.qsygo.com/interface.php/goods/published?page=0&count=10");
    }
}
