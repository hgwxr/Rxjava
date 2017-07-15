package com.wl.rxjava;

/**
 * Created by Administrator on 2017/6/30.
 */
public class VerificationUtils {
    public static String EMAIL_CHECK="email";
    public static String PHONE_CHECK="phone";
    public static String NULL_CHECK="null";
    public static String LENGTH_CHECK="length";
    public static String SPACE_CHECK="contains_space";
    public static class CheckModelWrapper<T extends DemoModel>{
        private T model;
    }
}
