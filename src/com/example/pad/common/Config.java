package com.example.pad.common;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/17/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    public static final String SERVER_BASE_URL = "http://101.20.142.1:9000/";
//    public static final String SERVER_BASE_URL = "http://192.168.0.113:9000/";

    public static final int NOTICE_FETCH_INTERVAL = 10 * 1000;
    public static final int HTTP_TIMEOUT = 30 * 1000;

    public static boolean passwordRequired(){
        return false;
    }
}
