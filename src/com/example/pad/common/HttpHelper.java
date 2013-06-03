package com.example.pad.common;


import android.content.Context;
import android.util.Log;
import com.example.pad.AppConfig;
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
    private Context context;

    public HttpHelper(Context context, String login, String password) {
        this.context = context;
        client = new AsyncHttpClient();
        client.setTimeout(Config.HTTP_TIMEOUT);
        client.setBasicAuth(login, password);
        client.addHeader("Accept", "application/json");
        client.addHeader("Content-Type", "application/json");
    }


    public void with(String path, RequestParams params, JsonHttpResponseHandler handler){
        Log.d("Getting ", absoluteURL(path));
        client.get(absoluteURL(path), params, handler);
    }

    public void post(String path,  RequestParams params, JsonHttpResponseHandler handler){
        Log.d("Posting ", absoluteURL(path));
        client.post(absoluteURL(path), params, handler);

    }


    private String absoluteURL(String path){
        return "http://" + AppConfig.getAppConfig(context).get(AppConfig.CONF_SERVER) + ":" + AppConfig.getAppConfig(context).get(AppConfig.CONF_PORT) + "/" + path;
    }


}
