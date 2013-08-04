package com.example.pad.common;

import android.app.ProgressDialog;
import android.content.Context;
import com.example.pad.view.XunjianList;
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
    public void onFailure(Throwable throwable, String string) {
        super.onFailure(throwable, string);

        if (progressDialog != null) {
            progressDialog.hide();
        }
        UIHelper.showLongToast(context, throwable.toString());

    }
}
