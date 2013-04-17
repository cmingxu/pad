package com.example.pad.common;

import android.util.Log;
import com.example.pad.models.User;
import com.loopj.android.http.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/17/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpHelper {
    public static final String BASE_URL = "http://192.168.0.113:9000/";
    public  AsyncHttpClient client;
    private static  HttpHelper instance;

    public static  HttpHelper getInstance(String login, String password){
        if (instance == null) {
            instance = new HttpHelper();
            instance.client = new AsyncHttpClient();
            instance.client.setBasicAuth(login, password);
        }
        return instance;
    }


    public void withUsers(RequestParams params, JsonHttpResponseHandler handler){
        Log.d("aa", absoluteURL("users"));
        client.get(absoluteURL("users"), params, handler);
    }


    private String absoluteURL(String path){
        return BASE_URL + path;
    }



}
