package com.hgwxr.okhttp;

import okhttp3.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/15.
 */
public class DownLoad {
  static  String url="http://oss.qsygo.com/download/qsygo.apk";
    private static OkHttpClient okHttpClient;

    public static void main(String[] args) {
        long a=1500106291090L;
        long b=1500106287750L;
        long c=1500106190632L;
        long d=1500106187573L;
        System.out.println((a-b)+"   "+ (c-d));
        //初始化OkHttpClient
        //设置超时时间
//设置读取超时时间
//设置写入超时时间
      /*  okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        downLoadApk();*/
    }

    private static void downLoadApk() {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        long  a=11101918;
        long b=3;
        long x = a / b;
        System.out.println(getBlockSize(11101918,false)+" "+ getBlockSize(11101918,true));
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                9459114432
//                11101918
                 //create  file
                File file = new File("qsyg.apk");
                if (file.exists()) {
                    System.out.println("已下载过！");
                    file.delete();
                }
                    //write  bytes  to  file
                    long contentLength = response.body().contentLength();
                    System.out.println("大小: "+contentLength);
                    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                BufferedInputStream inputStream = new BufferedInputStream(response.body().byteStream());
                RandomAccessFile randmFile = new RandomAccessFile(file, "rwd");
            /**
             "r"    以只读方式打开。调用结果对象的任何 write 方法都将导致抛出 IOException。
             "rw"   打开以便读取和写入。
             "rws"  打开以便读取和写入。相对于 "rw"，"rws" 还要求对“文件的内容”或“元数据”的每个更新都同步写入到基础存储设备。
             "rwd"  打开以便读取和写入，相对于 "rw"，"rwd" 还要求对“文件的内容”的每个更新都同步写入到基础存储设备。
             */
                randmFile.setLength(contentLength);
                try {
//                    byte[] bytes = new byte[2048];
//                    int len = -1;
//                    inputStream.read()
//                    while ((len = inputStream.read(bytes, 0, bytes.length)) != -1) {
//                            System.out.println(len);
////                        outputStream.write(bytes, 0, len);
////                           randmFile.seek(11101918/2);
//                           randmFile.write(bytes, 0, len);
//                    }
                }finally {
                    outputStream.close();
                }
//                byte[] bytes = response.body().bytes();
//                byte[] data;
//                int totalLen = bytes.length;
                int threadCount=3;
//             long  blockSize  = getBlockSize(contentLength);
                InputStream byteStream = response.body().byteStream();
                byte[] bytes = response.body().bytes();
                Runnable target0 = partTask(bytes, contentLength, outputStream, randmFile,0);
                Runnable target1 = partTask(bytes, contentLength, outputStream, randmFile,1);
                Runnable target2 = partTask(bytes, contentLength, outputStream, randmFile,2);
                new Thread(target0).start();
                new Thread(target1).start();
                new Thread(target2).start();
//                threePart(response, contentLength, outputStream, randmFile);
//                writeWay1(outputStream, inputStream);
//                writeWay2(response, file, outputStream);
            }

            private Runnable partTask(final byte[] bytes1, final long contentLength, final BufferedOutputStream outputStream, final RandomAccessFile randmFile,int part) {
                return new Runnable() {

                                @Override
                                public void run() {
                                    System.out.println(Thread.currentThread().getName()+"  start");
                                    try {
                                        byte[] bytes = new byte[2048];
                                        int len = -1;
                                        long blockSize = getBlockSize(contentLength, false);
                                            randmFile.seek(blockSize*part);
                                            if (part!=2) {
                                                byte[] bytes2 = new byte[(int) blockSize];
                                                System.arraycopy(bytes1, ((int) (blockSize * part)),bytes2,0, (int) blockSize);
                                                System.out.println(bytes2.length+" "+part);
                                                randmFile.write(bytes2,0, bytes2.length);
                                            }else{
                                                byte[] bytes2 = new byte[(int) getBlockSize(contentLength,true)];
                                                System.arraycopy(bytes1, ((int) (blockSize * part)),bytes2,0, (int) (int) getBlockSize(contentLength,true));
                                                System.out.println(bytes2.length+" "+part);
                                                randmFile.write(bytes2,0, bytes2.length);
                                            }


                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            outputStream.close();
                                            System.out.println(Thread.currentThread().getName()+"  over");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            };
            }

        });
    }
  public static  class PartTask implements Runnable{

      @Override
      public void run() {

      }
  }
    private static void threePart(Response response, long contentLength, BufferedOutputStream outputStream, RandomAccessFile randmFile) throws IOException {
        InputStream byteStream = response.body().byteStream();
        try {
            byte[] bytes = new byte[2048];
            int len = -1;
            long blockSize = getBlockSize(contentLength, false);
            byte[] bytes1 = response.body().bytes();
            for (int i = 0; i < 3; i++) {
                randmFile.seek(blockSize*i);
                if (i!=2) {
                    byte[] bytes2 = new byte[(int) blockSize];
                    System.arraycopy(bytes1, ((int) (blockSize * i)),bytes2,0, (int) blockSize);
                    System.out.println(bytes2.length+" "+i);
                    randmFile.write(bytes2,0, bytes2.length);
                }else{
                    byte[] bytes2 = new byte[(int) getBlockSize(contentLength,true)];
                    System.arraycopy(bytes1, ((int) (blockSize * i)),bytes2,0, (int) (int) getBlockSize(contentLength,true));
                    System.out.println(bytes2.length+" "+i);
                    randmFile.write(bytes2,0, bytes2.length);
                }
            }

        }finally {
            outputStream.close();
        }
    }

    private static long getBlockSize(long contentLength,boolean isLastThread) {

        int  threadCount=3;
        long size = contentLength / threadCount;
        if (!isLastThread) {
            return size;
        }else{
            return size+contentLength-threadCount*size;
        }
    }
    private static long getBlockSize(long contentLength,int threadId) {


        int  threadCount=3;
        return  threadCount == 0 ? contentLength : contentLength / threadCount;
    }



    private static void writeWay2(Response response, File file, BufferedOutputStream outputStream) throws IOException {
        try {
            outputStream.write(response.body().bytes());
            outputStream.flush();
        }finally {
            outputStream.close();
            System.out.println("文件 下载后大小："+file.length());
        }
    }

    private static void writeWay1(BufferedOutputStream outputStream, BufferedInputStream inputStream) throws IOException {
        try {
            byte[] bytes = new byte[2048];
            int len = -1;
            while ((len = inputStream.read(bytes, 0, bytes.length)) != -1) {
                System.out.println(len);
                outputStream.write(bytes, 0, len);
            }
        }finally {
            outputStream.close();
        }
    }
}
