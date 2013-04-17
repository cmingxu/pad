package com.example.pad.common;

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
    private HttpHelper instance;

    public HttpHelper getInstance(String login, String password){
        if (instance == null) {
            instance = new HttpHelper();
            instance.client = new AsyncHttpClient();
            instance.client.setBasicAuth(login, password);
        }
        return instance;
    }


    public void withUsers(RequestParams params, AsyncHttpResponseHandler handler){
        client.get(BASE_URL + "users", params, handler);
    }


}
