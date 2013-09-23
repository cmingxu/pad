package com.example.pad.common;


import android.util.Log;
import com.example.pad.AppConfig;
import com.example.pad.AppContext;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/17/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpHelper {
    public AsyncHttpClient client;
    public String login;
    public String password;
    private AppContext appContext;

    public HttpHelper(AppContext context) {
        this.appContext = context;
        if (context.getCurrentUser() == null) {
            login = "login";
            password = "password";
        } else {
            login = appContext.getCurrentUser().login;
            password = appContext.getCurrentUser().password;
        }
        client = new AsyncHttpClient();
        client.setTimeout(Config.HTTP_TIMEOUT);
        client.addHeader("authorization", login + ":" + password);
        client.addHeader("Accept", "application/json");
        client.addHeader("Content-Type", "application/json");
    }

    public void with(String path, RequestParams params, JsonHttpResponseHandler handler) {
        Log.d("network Getting ", absoluteURL(path));
        client.get(absoluteURL(path), params, handler);
    }

    public void post(String path, RequestParams params, JsonHttpResponseHandler handler) {
        Log.d("network Posting ", absoluteURL(path));
        client.post(absoluteURL(path), params, handler);

    }

    private String absoluteURL(String path) {
        return "http://" + AppConfig.getAppConfig(appContext).get(AppConfig.CONF_SERVER) + ":" + AppConfig.getAppConfig(appContext).get(AppConfig.CONF_PORT) + "/" + path;
    }


}
