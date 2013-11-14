package com.example.pad.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.example.pad.AppConfig;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/18/13
 * Time: 12:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;
    private static Util instance;

    public static Util instance(){
        if (instance == null) {
           instance = new Util();
        }
        return instance;
    }


    /**
     * 获取当前网络类型
     * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
     */
    public int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if(!StringUtils.isEmpty(extraInfo)){
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }


    public boolean isNetworkConnected(Context context) {
        return this.getNetworkType(context) != 0;
    }

    public boolean isServerReachable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                String urlString = "http://" + AppConfig.getAppConfig(context).get(AppConfig.CONF_SERVER) + ":" + AppConfig.getAppConfig(context).get(AppConfig.CONF_PORT);

                URL url = new URL(urlString);   // Change to "http://google.com" for www  test.
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(10 * 1000);          // 10 s.
                urlc.connect();
                if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                    Log.wtf("Connection", "Success !");
                    return true;
                } else {
                    return false;
                }
            } catch (MalformedURLException e1) {
                return false;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public String formatTime(String format, Date d){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

    public static boolean isNoticeServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (NoticeService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public static ArrayList<String>  mapKeys(Map map){
        ArrayList<String> keys = new ArrayList<String>();
        for (Object key : map.keySet()){
               String s = (String)key;
            keys.add(s);
        }
        return keys;
    }
}
