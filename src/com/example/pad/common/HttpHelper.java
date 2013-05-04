package com.example.pad.common;


import com.loopj.android.http.*;



/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/17/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpHelper {
    public  AsyncHttpClient client;
    private static  HttpHelper instance;

    public static  HttpHelper getInstance(String login, String password){
        if (instance == null) {
            instance = new HttpHelper();
            instance.client = new AsyncHttpClient();
            instance.client.setTimeout(Config.HTTP_TIMEOUT);
            instance.client.setBasicAuth(login, password);
            instance.client.addHeader("Accept", "application/json");
            instance.client.addHeader("Content-Type", "application/json");

        }
        return instance;
    }


    public void with(String path, RequestParams params, JsonHttpResponseHandler handler){
        client.get(absoluteURL(path), params, handler);
    }


    private String absoluteURL(String path){
        return Config.SERVER_BASE_URL + path;
    }


}
