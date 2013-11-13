package com.example.pad.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.example.pad.models.CachedRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 8/3/13
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class PadJsonHttpResponseHandler extends JsonHttpResponseHandler{
    private Context context;
    private ProgressDialog progressDialog;
    private CachedRequest cachedRequest;

    public PadJsonHttpResponseHandler(Context context) {
        this.context = context;
    }
    public PadJsonHttpResponseHandler(Context context, ProgressDialog progressDialog) {
        this.context = context;
        this.progressDialog = progressDialog;

    }

    public PadJsonHttpResponseHandler(Context context, ProgressDialog progressDialog, CachedRequest cachedRequest) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.cachedRequest = cachedRequest;
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        super.onSuccess(jsonObject);
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
        super.onSuccess(jsonArray);
    }

    @Override
    public void onFailure(Throwable throwable, JSONObject jsonObject) {
        Log.d("network", "onFailure jsonObject");

        Log.d("cachedrequest", "fuck");

        if (progressDialog != null) {
            progressDialog.hide();
        }
        UIHelper.showLongToast(context, throwable.toString());
        if (cachedRequest != null) {
            Log.d("cachedrequest", cachedRequest.getRequest());
            cachedRequest.save();
        }

        super.onFailure(throwable, jsonObject);
    }

    @Override
    public void onFailure(Throwable throwable, JSONArray jsonArray) {
        Log.d("network", "onFailure jsonArray");
        super.onFailure(throwable, jsonArray);
        if (progressDialog != null) {
            progressDialog.hide();
        }
        Log.e("network", throwable.toString());
        UIHelper.showLongToast(context, "网络错， 检查网络是否连接以及服务器地址是否设置正确。  " + throwable.toString());

//        save request for later retranssmit
        if (cachedRequest != null) {
            cachedRequest.save();
        }
    }

    @Override
    public void onFailure(Throwable throwable, String string) {
        Log.d("network", "onFailure string");
        super.onFailure(throwable, string);

        if (progressDialog != null) {
            progressDialog.hide();
        }
        UIHelper.showLongToast(context, "网络错， 检查网络是否连接以及服务器地址是否设置正确。" + throwable.toString());
        if (cachedRequest != null) {
            cachedRequest.save();
        }

    }


}
