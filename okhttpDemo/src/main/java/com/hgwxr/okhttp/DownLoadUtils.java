package com.hgwxr.okhttp;

import okhttp3.*;

import java.io.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/15.
 */
public class DownLoadUtils {
    private OkHttpClient okHttpClient;
    static  String url="http://oss.qsygo.com/download/qsygo.apk";
    public static void main(String[] args) {
        downLoad1(url,3,"qsyg.apk","download");

    }
    public static void  downLoad1(String url, int threadCount, String fileName, String path){

        RandomAccessFile rwd = null;
        try {
            rwd = new RandomAccessFile(fileName, "rwd");
            OkHttpClient   okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                    .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                    .build();
            for (int i = 0; i < threadCount; i++) {
                Request request = new Request.Builder().url(url).get().build();
                Call call = okHttpClient.newCall(request);

                int finalI = i;
                RandomAccessFile finalRwd = rwd;
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println(Thread.currentThread().getName());
                        long blockSize = getBlockSize(finalI, response.body().contentLength(), finalI == threadCount - 1);
                        finalRwd.seek(blockSize*finalI);
                        int nextLength=2048;
                        byte[] bytes = new byte[nextLength];
                        InputStream inputStream = response.body().byteStream();
                        inputStream.skip(blockSize*finalI);
                        int len=0;
                        int readLength=0;
                        while (((len=inputStream.read(bytes,0,nextLength))>0)){
                            finalRwd.write(bytes,0,len);
                            readLength+=len;

                             if (readLength+nextLength>blockSize){
                                 nextLength= (int) (blockSize-readLength);
                             }
                             if (readLength==blockSize){
                                 break;
                             }
                        }
                    }
                });
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    public static void  downLoad(String url, int threadCount, String fileName, String path){
        OkHttpClient   okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                long contentLength = response.body().contentLength();
                File file = new File(fileName);
                if (file.exists()){
//                    file.delete();
                }
                RandomAccessFile rwd = new RandomAccessFile(file, "rwd");
                rwd.setLength(contentLength);
//                ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
               // for (int i = 0; i < threadCount; i++) {
                    DownTask command = new DownTask();
                    command.setrAFile(rwd);
                    command.setInputStream(inputStream);
                  //  if (i==threadCount-1){
                      //  command.setPastSize(getBlockSize(threadCount, contentLength, true));
                  //  }else {
                        command.setPastSize(getBlockSize(3, contentLength, false));
                 //   }
                    command.setTaskId(0);
                    command.setTotalLength(contentLength);
                    //executorService.execute(command);
                    new Thread(command).start();
                }
           // }
        });
    }
    private  static  class DownTask implements Runnable{
        private RandomAccessFile rAFile;
        private InputStream inputStream;
        private int taskId;
        private  long pastSize;
        private  long totalLength;

        public RandomAccessFile getrAFile() {
            return rAFile;
        }

        public void setrAFile(RandomAccessFile rAFile) {
            this.rAFile = rAFile;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public long getPastSize() {
            return pastSize;
        }

        public void setPastSize(long pastSize) {
            this.pastSize = pastSize;
        }

        public long getTotalLength() {
            return totalLength;
        }

        public void setTotalLength(long totalLength) {
            this.totalLength = totalLength;
        }

        @Override
        public void run() {

            byte[] bytes = new byte[2048];
            int len=-1;
            try {
                long skipLength= pastSize*taskId;
                rAFile.seek(skipLength);
                int  readLength=0;
                int lengthNext = bytes.length;
                System.out.print(Thread.currentThread().getName());
                inputStream.skip(skipLength);
                while ((len=inputStream.read(bytes, 0, lengthNext))!=-1){
                    rAFile.write(bytes,0,len);
                    readLength+=len;
                    if (readLength+lengthNext>pastSize){
                        lengthNext= (int) (pastSize-readLength);
                    }
                    System.out.println(Thread.currentThread().getName()+"  read     count  "+len+" "+ readLength+" partSize"+ pastSize);
                    if (pastSize==readLength){
                        System.out.println(Thread.currentThread().getName()+"   over");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    rAFile.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static long getBlockSize(int threadCount,long contentLength,boolean isLastThread) {
        if (threadCount<0){
            threadCount=1;
        }
        long size = contentLength / threadCount;
        if (!isLastThread) {
            return size;
        }else{
            return size+contentLength-threadCount*size;
        }
    }
}
