package com.third.hilltrend.common.hession;

/**
 * Created by rui on 15/8/21.
 */
public final class ZDHessianReceive {

    private ZDHessianReceive() {}

    private static ThreadLocal<String> receives = new ThreadLocal<String>();

    public static void put(String requestString){
        receives.set(requestString);
    }

    public static String get() {
        return receives.get();
    }


    public static void remove() {
        receives.remove();
    }

}
