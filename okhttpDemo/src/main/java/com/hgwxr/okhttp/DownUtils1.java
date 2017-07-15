package com.hgwxr.okhttp;

import okhttp3.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/15.
 */
public class DownUtils1 {
    static  String url="http://oss.qsygo.com/download/qsygo.apk";
    private static OkHttpClient okHttpClient;
    private static String  name = "qsyg1.apk";

    public static void main(String[] args) {
        //设置超时时间
//设置读取超时时间
//设置写入超时时间
        System.out.println(System.currentTimeMillis());
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        Request request = new Request.Builder()
                .url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                   //创建文件
                RandomAccessFile rwd = new RandomAccessFile(name, "rwd");
                rwd.setLength(response.body().contentLength());
                //开启多线程去下载
                ThreadTask1 threadTask = new ThreadTask1();
                threadTask.setTaskId(0);
                threadTask.setThreadCount(3);
               new Thread(threadTask).start();

                ThreadTask1 threadTask1 = new ThreadTask1();
                threadTask1.setTaskId(1);
                threadTask1.setThreadCount(3);
                new Thread(threadTask1).start();

                ThreadTask1 threadTask2 = new ThreadTask1();
                threadTask2.setTaskId(2);
                threadTask2.setThreadCount(3);
                new Thread(threadTask2).start();
            }
        });
    }
    private static class ThreadTask1 implements  Runnable{
        private  int  taskId;
        private  int threadCount;
        private  String  range=" bytes=%d-%d";//Range: bytes=40-100：第40个字节到第100个字节之间的数据.

        private  String  rangeLast="bytes=%d-";
        public void setThreadCount(int threadCount) {
            this.threadCount = threadCount;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            RandomAccessFile rwd = null;
            Request request=null;
            try {
                rwd = new RandomAccessFile(name, "rwd");
                long length = rwd.length();
                System.out.println("part  "+length);
                long blockSize = getBlockSize(threadCount, length, false);
                long startIndex = blockSize * taskId;
                long endIndex = startIndex+blockSize-1;

                String format = "";
                if (taskId==threadCount-1){
                     format = String.format(rangeLast, startIndex);
                }else {
                    format = String.format(range, startIndex, endIndex);
                }
                request = new Request.Builder().addHeader("Range", format).url(url).get().build();
                System.out.println(format);
                Call call = okHttpClient.newCall(request);
                RandomAccessFile finalRwd = rwd;
                call.enqueue(new Callback() {
                      @Override
                      public void onFailure(Call call, IOException e) {
                      }
                      @Override
                      public void onResponse(Call call, Response response) throws IOException {
                          System.out.println(response.body().contentLength()+" contentklength  "+taskId);
                          InputStream inputStream = response.body().byteStream();
                          byte[] bytes = new byte[2048];
                          int readLen;
                          int readLenCurr=0;
                          RandomAccessFile   rwd = new RandomAccessFile(name, "rwd");
                          rwd.seek(blockSize*taskId);
                          while((readLen=inputStream.read(bytes,0,2048))>0){
                              finalRwd.write(bytes,0,readLen);
                              readLenCurr+=readLen;
                              System.out.println(Thread.currentThread().getName()+taskId+" 下载 进度 "+readLenCurr);
                          }
                          System.out.println(Thread.currentThread().getName()+taskId+" over  "+System.currentTimeMillis());
                      }
                  });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static class ThreadTask implements  Runnable{
       private  int  taskId;
       private  int threadCount;

        public void setThreadCount(int threadCount) {
            this.threadCount = threadCount;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            Request request = new Request.Builder().url(url).get().build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream inputStream = response.body().byteStream();
                    RandomAccessFile rwd = new RandomAccessFile(name, "rwd");
                    long blockSize = getBlockSize(threadCount, rwd.length(), taskId == threadCount - 1);
                    rwd.seek(blockSize*taskId);
                    int nextLength=2048;
                    byte[] bytes = new byte[nextLength];
                    int readLen;
                    inputStream.skip(blockSize*taskId);
                    while((readLen=inputStream.read(bytes,0,nextLength))>0){
                        rwd.write(bytes,0,readLen);
                        readLen+=readLen;
                        if (readLen+nextLength>blockSize){
                            nextLength= (int) (blockSize-readLen);
                        }
                    }
                    System.out.println(Thread.currentThread().getName()+"    ok");
                    System.out.println(System.currentTimeMillis());
                }
            });
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
    public static  void downLoad(){

    }
}
