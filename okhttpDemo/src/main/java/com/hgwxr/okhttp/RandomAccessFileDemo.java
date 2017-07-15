package com.hgwxr.okhttp;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by Administrator on 2017/7/15.
 */
public class RandomAccessFileDemo {

    public static void main(String[] args) throws IOException {
        // create a new RandomAccessFile with filename test

        RandomAccessFile raf=null;
        try {
             raf = new RandomAccessFile("test.txt", "rw");
            // write something in the file
            raf.writeUTF("HellolWorld");

            // set the file pointer at 0 position
            raf.seek(0);

            // print the string
            String line="";

              while ((line=raf.readLine())!=null){
                  System.out.println(line);
              }

            // set the file pointer at 5 position
//            raf.seek(5);

            // write something in the file
//            raf.writeUTF("This is an example");

            // set the file pointer at 0 position
//            raf.seek(0);

            // print the string
//            System.out.println("1 ——" + raf.readUTF());
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            raf.close();
        }

    }
}