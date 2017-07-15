package com.wl.rxjava.utils;

import java.io.*;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Administrator on 2017/6/5.
 */
public class SignsUtils {

    private static boolean isCanAdd;
    private static BufferedWriter bufferedWriter;
    private static BufferedReader bufferedReader;
    private static String tempString;

    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) throws IOException {
        String api = "/interface.php/mine/detail";//"/interface.php/mine/chargemoney";//"/interface.php/mine/doaddressnew";
        String signs=getSigns(api);
        String signs1 = SignsUtils.getSigns(api);
       // System.out.println(signs1);

       // System.out.println(signs);

        File file = new File("interface.txt");
        File interfaceNew = new File("interfaceNew.txt");


        try {
            bufferedWriter = new BufferedWriter(new FileWriter("interfaceNew.txt"));
            bufferedReader = new BufferedReader(new FileReader("interface.txt"));
            String line="";
            while ((line= bufferedReader.readLine())!=null){
               // System.out.println(line);
                line=line.trim();
                //if (!Character.isDigit(line.charAt(0))){
                if (line.matches("[0-9]+.*")){
                    tempString =line;
                    StringBuffer append=new StringBuffer();
                    append.append(  "\n\t");
                    String signs2 = getSigns(line);
                   append.append("signs : " +signs2)
                          .append(  "\n\t")
                          .append("id : "+"1002844")
                          .append(  "\n\t")
                          .append("xmid : d5adc9b78057d1b76ca36b232416bb3a");
                    line=line+append.toString();
                }
                //if (line.startsWith("signs")||line.startsWith("xmid")||line.startsWith("id"))
                  //  continue;
                if (line.startsWith("xm_id")){
                   line="xmid : d5adc9b78057d1b76ca36b232416bb3a";
                }
                if (line.startsWith("参数id:")||line.startsWith("    参数：user_id:用户ID")){
                    line="id : 1002844";
                }
                if (line.contains("用户ID")){
                    line="参数：id: 1002844";
                }
                if (line.startsWith("signs")||line.contains("signs")){
                    String signs2 = getSigns(tempString);
                    line="signs : " +signs2;
                }
                if (line.contains("id,signs,xm_id")){
                    StringBuffer append=new StringBuffer();
                    append.append(  "\n\t");
                    String signs2 = getSigns(tempString);
                    append.append("signs : " +signs2)
                            .append(  "\n\t")
                            .append("id : "+"1002844")
                            .append(  "\n\t")
                            .append("xmid : d5adc9b78057d1b76ca36b232416bb3a");
                    line="\t参数 : \n"+append.toString();
                }
                bufferedWriter.newLine();
                bufferedWriter.write("\t"+line);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (bufferedWriter!=null) {
                bufferedWriter.close();
                bufferedReader.close();
            }
        }
    }

    public static String getSigns(String api) {
        String[] split = api.split("/");
        for (int i = 0; i < split.length; i++) {
           // System.out.println(i+"   ===    "+split[i]);
        }
        String in =  split[2];
        String ga = split[3];
        String userId = "1002844";
        String xmid = "d5adc9b78057d1b76ca36b232416bb3a";
        String str = getCurrentTime_yyyyMMdd();
        String mdm = xmid + userId + str + ga + in;
        String signs = MD5(mdm);
        System.out.println(api +"================="+ signs);
        return signs;
    }


    public static String getCurrentTime_yyyyMMdd() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String format = formatter.format(curDate);
        return format;
    }
    public static String MD5(String str) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


}
