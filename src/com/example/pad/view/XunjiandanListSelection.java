package com.example.pad.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.UIHelper;
import com.example.pad.common.Util;
import com.example.pad.models.User;
import com.example.pad.models.Xunjiandan;
import com.example.pad.models.Xunjiandanmingxi;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 7/12/13
 * Time: 10:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class XunjiandanListSelection extends BaseActivity {

    Spinner spinner;
    Button downloadBtn;
    Map timeRange;
    ArrayList<String> timeRangeList;
    int defaultTime = 60 * 60;
    ProgressDialog progressDialog;
    HttpHelper httpHelper;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timeRange = new HashMap<String, Integer>();
        timeRange.put("最近一小时", 60 * 60)       ;
        timeRange.put("最近三小时", 3 * 60 * 60);
        timeRange.put("最近五小时", 3 * 60 * 60);
        timeRange.put("最近一天", 24 * 60 * 60);
        timeRange.put("最近三天", 3 * 24 * 60 * 60);
        timeRange.put("最近一周", 7 * 24 * 60 * 60);
        timeRange.put("最近一月", 30  * 24 * 60 * 60);

        timeRangeList = new ArrayList<String>();
        timeRangeList.add("最近一小时");
        timeRangeList.add("最近三小时");
        timeRangeList.add("最近五小时");
        timeRangeList.add("最近一天");
        timeRangeList.add("最近三天");
        timeRangeList.add("最近一周");
        timeRangeList.add("最近一月");




        ArrayList<String> keys = Util.mapKeys(timeRange);

        setContentView(R.layout.xunjiandan_list_selection);
        spinner = (Spinner)findViewById(R.id.time_range_select);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                timeRangeList);
        spinner.setAdapter(spinnerArrayAdapter);

        httpHelper =  new HttpHelper(this, Util.instance().current_user.login, Util.instance().current_user.password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("下载中");
        progressDialog.setMessage("巡检单下载中， 请耐心等候");
        downloadBtn = (Button)findViewById(R.id.download);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.instance().isNetworkConnected(XunjiandanListSelection.this)){
                    progressDialog.show();

                    httpHelper.with("xunjiandans", null, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(JSONArray jsonArray) {
                            try {
                                ArrayList<Xunjiandan> xunjiandans = Xunjiandan.fromJsonArray(jsonArray);
                                for(Xunjiandan u : xunjiandans) u.save();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.hide();
                            UIHelper.showLongToast(XunjiandanListSelection.this, R.string.save_success);

                        }
                    });

                    httpHelper.with("xunjiandanmingxis", null, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(JSONArray jsonArray) {
                            try {
                                ArrayList<Xunjiandanmingxi> xunjiandanmingxis = Xunjiandanmingxi.fromJsonArray(jsonArray);
                                for(Xunjiandanmingxi u : xunjiandanmingxis) u.save();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }else{
                    UIHelper.showLongToast(XunjiandanListSelection.this, R.string.network_error);
                }
            }
        });
    }
}