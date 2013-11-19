package com.example.pad.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.example.pad.R;
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

    @Override
    public void onSuccess(JSONObject jsonObject) {
        super.onSuccess(jsonObject);
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
        super.onSuccess(jsonArray);
    }

    public void failure(String message) {

    }

    @Override
    public void onFailure(Throwable throwable, JSONObject jsonObject) {
        failure(throwable.getMessage());
        super.onFailure(throwable, jsonObject);
    }

    @Override
    public void onFailure(Throwable throwable, JSONArray jsonArray) {
        failure(throwable.getMessage());
        super.onFailure(throwable, jsonArray);

    }

    @Override
    public void onFailure(Throwable throwable, String string) {
        failure(throwable.getMessage());
        super.onFailure(throwable, string);

    }


}
