package com.example.pad.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
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

    public PadJsonHttpResponseHandler(Context context) {
        this.context = context;
    }

    public PadJsonHttpResponseHandler(Context context, ProgressDialog progressDialog) {
        this.context = context;
        this.progressDialog = progressDialog;
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
        Log.d("network", "onFailure");
        super.onFailure(throwable, jsonObject);
        if (progressDialog != null) {
            progressDialog.hide();
        }
        UIHelper.showLongToast(context, throwable.toString());
    }

    @Override
    public void onFailure(Throwable throwable, JSONArray jsonArray) {
        Log.d("network", "onFailure");
        super.onFailure(throwable, jsonArray);
        if (progressDialog != null) {
            progressDialog.hide();
        }
        UIHelper.showLongToast(context, throwable.toString());
    }

    @Override
    public void onFailure(Throwable throwable, String string) {
        Log.d("network", "onFailure");
        super.onFailure(throwable, string);

        if (progressDialog != null) {
            progressDialog.hide();
        }
        UIHelper.showLongToast(context, throwable.toString());

    }
}
