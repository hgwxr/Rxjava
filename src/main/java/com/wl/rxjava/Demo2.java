package com.wl.rxjava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/6/24.
 */
public class Demo2 {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        initData(arrayList);
        Iterator<String> iterator = arrayList.iterator();
        System.out.println("==============Iterator 遍历");
        while (iterator.hasNext()){
            String string = iterator.next().toString();
            if (string.contains("9")) {
                iterator.remove();
//                arrayList.remove(string);
                System.out.println("removed:"+string);
            }else{
                System.out.println(string);
            }


        }
        System.out.println("=============for each 遍历");
        for (String string : arrayList) {

            if (string.contains("8")) {
                //iterator.remove();
                //arrayList.remove(string);
                System.out.println("removed:"+string);
            }else{
                System.out.println(string);
            }
        }
        System.out.println("=============LinkedList Iterator 遍历");
        LinkedList<String> linkedList = new LinkedList<>();
        initData(linkedList);
        Iterator<String> iterator1 = linkedList.iterator();
        while(iterator1.hasNext()){
            String string = iterator1.next().toString();
            if (string.contains("8")) {
//                iterator1.remove();
                arrayList.remove(string);
                System.out.println("removed:"+string);
            }else{
                System.out.println(string);
            }
        }
    }

    private static void initData(LinkedList<String> linkedList) {
        for (int i = 0; i < 10; i++) {
            linkedList.add("data"+"===="+i);
        }
    }

    private static void initData(ArrayList<String> arrayList) {
        for (int i = 0; i < 10; i++) {
            arrayList.add("data"+"===="+i);
        }
    }
}
