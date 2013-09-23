package com.example.pad.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.example.pad.AppContext;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.*;
import com.example.pad.models.Xunjiandan;
import com.example.pad.models.Xunjiandanmingxi;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.*;

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
    ProgressDialog progressDialog;
    AppContext appContext;

    HttpHelper httpHelper;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appContext = (AppContext)getApplication();

        timeRange = new HashMap<String, Integer>();
        timeRange.put("最近一小时", 60 * 60)       ;
        timeRange.put("最近三小时", 3 * 60 * 60);
        timeRange.put("最近五小时", 5 * 60 * 60);
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

        httpHelper =  new HttpHelper(appContext);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("下载中");
        progressDialog.setMessage("巡检单下载中， 请耐心等候");
        downloadBtn = (Button)findViewById(R.id.download);
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.instance().isNetworkConnected(XunjiandanListSelection.this)){
                    progressDialog.show();

                    RequestParams rp = new RequestParams();
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.SECOND, (Integer)timeRange.get(spinner.getSelectedItem().toString()));
                    rp.put("before", StringUtils.formatTime(c.getTime()));

                    httpHelper.with("xunjiandans", rp, new PadJsonHttpResponseHandler(XunjiandanListSelection.this, progressDialog) {
                        @Override
                        public void onSuccess(JSONArray jsonArray) {
                            try {
                                ArrayList<Xunjiandan> xunjiandans = Xunjiandan.fromJsonArray(jsonArray);
                                for (Xunjiandan u : xunjiandans)
                                    if (!Xunjiandan.exists(u.mRemoteID))
                                        u.save();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.hide();
                            UIHelper.showLongToast(XunjiandanListSelection.this, R.string.save_success);

                        }


                    });

                    httpHelper.with("xunjiandanmingxis", null, new PadJsonHttpResponseHandler(XunjiandanListSelection.this, progressDialog){
                        @Override
                        public void onSuccess(JSONArray jsonArray) {
                            try {
                                ArrayList<Xunjiandanmingxi> xunjiandanmingxis = Xunjiandanmingxi.fromJsonArray(jsonArray);
                                for(Xunjiandanmingxi u : xunjiandanmingxis)
                                    if(!Xunjiandanmingxi.exists(u.mRemoteID))
                                        u.save();

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