package com.example.pad.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.activeandroid.query.Select;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.UIHelper;
import com.example.pad.common.Util;
import com.example.pad.models.Notice;
import com.example.pad.models.Weixiudan;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/27/13
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class WeixiudanList extends BaseActivity
{
    public static final int WEIXIUDAN_SAVE_OK = 1;
    public static final int WEIXIUDAN_SAVE_FAILE = 2;
    public static final int NOT_ACCEPT_NOTICE_SAVE_OK = 3;
    public static final int NOT_ACCEPT_NOTICE_SAVE_FAILED = 4;
    public static final int NOT_COMPLETE_NOTICE_SAVE_OK = 5;
    public static final int NOT_COMPLETE_NOTICE_SAVE_FAILED = 6;
    ListView listView;
    enum ViewType{
        WEIXIUDAN_HEADER,
        WEIXIUDAN_VIEW,
        NOT_ACCEPT_NOTICE_HEADER,
        NOT_ACCEPT_NOTICE_VIEW,
        NOT_COMPLETE_NOTICE_HEADER,
        NOT_COMPLETE_NOTICE_VIEW

    }  ;

    ProgressDialog progressDialog;
    android.os.Handler handler;
    HttpHelper httpHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weixiudan_list);
        listView = (ListView)findViewById(R.id.list_view);
        final List<Weixiudan> weixiudans = new Select().from(Weixiudan.class).where("mRemoteSaved = 0").execute();
        final List<Notice> notUploadedAcceptList = new Select().from(Notice.class).where("isAccept=1 and acceptUploaded=0").execute();
        final List<Notice> notUploadCompleteList = new Select().from(Notice.class).where("(isComplete=1 and completeUploaded=0) or (isDaixiu=1 and daixiuUpload=0)").execute();

        listView.setAdapter(new ListViewAdapter(weixiudans, notUploadedAcceptList, notUploadCompleteList));

        progressDialog = new ProgressDialog(WeixiudanList.this);
        progressDialog.setTitle(R.string.wait_please);
        progressDialog.setMessage(getString(R.string.save_and_upload_inprogress));


        handler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case WEIXIUDAN_SAVE_OK:
                        progressDialog.dismiss();
                        UIHelper.showLongToast(WeixiudanList.this, getString(R.string.weixiudan_saved));
                        redirect(WeixiudanList.this, Maintain.class);
                        break;
                    case WEIXIUDAN_SAVE_FAILE:
                        progressDialog.dismiss();
                        UIHelper.showLongToast(WeixiudanList.this, getString(R.string.weixiudan_saved_failed));
                        break;
                    case NOT_ACCEPT_NOTICE_SAVE_OK:
                        progressDialog.dismiss();
                        UIHelper.showLongToast(WeixiudanList.this, getString(R.string.accept_notice_saved_ok));
                        break;
                    case NOT_ACCEPT_NOTICE_SAVE_FAILED:
                        progressDialog.dismiss();
                        UIHelper.showLongToast(WeixiudanList.this, getString(R.string.accept_notice_saved_failed));
                        break;
                    case NOT_COMPLETE_NOTICE_SAVE_OK:
                        progressDialog.dismiss();
                        UIHelper.showLongToast(WeixiudanList.this, getString(R.string.complete_notice_saved_ok));
                        break;
                    case NOT_COMPLETE_NOTICE_SAVE_FAILED:
                        progressDialog.dismiss();
                        UIHelper.showLongToast(WeixiudanList.this, getString(R.string.complete_notice_saved_fail));
                        break;
                    default:
                }

            }
        } ;

        httpHelper = new HttpHelper(WeixiudanList.this, Util.instance().current_user.login, Util.instance().current_user.password);
    }


    public class ListViewAdapter extends BaseAdapter{
        List<Weixiudan> weixiudans;
        List<Notice> notAcceptNoticeList;
        List<Notice> notCompleteNoticetList;
        ArrayList<ViewType> viewTypes = new ArrayList<ViewType>();

        public ListViewAdapter(List<Weixiudan> weixiudans, List<Notice> notAcceptNoticeList, List<Notice> notCompleteNoticetList) {

            this.weixiudans = weixiudans;
            this.notAcceptNoticeList = notAcceptNoticeList;
            this.notCompleteNoticetList = notCompleteNoticetList;

            viewTypes.add(ViewType.WEIXIUDAN_HEADER);
            for (int i = 0; i < weixiudans.size(); i++){
               viewTypes.add(ViewType.WEIXIUDAN_VIEW);
            }
            viewTypes.add(ViewType.NOT_ACCEPT_NOTICE_HEADER);
            for (int i = 0; i < notAcceptNoticeList.size(); i++)      {
                viewTypes.add(ViewType.NOT_ACCEPT_NOTICE_VIEW);
            }

            viewTypes.add(ViewType.NOT_COMPLETE_NOTICE_HEADER);

            for(int i = 0; i < notCompleteNoticetList.size(); i ++){
                viewTypes.add(ViewType.NOT_COMPLETE_NOTICE_VIEW);
            }

        }

        @Override
        public int getCount() {
            return 3 + weixiudans.size() + notAcceptNoticeList.size() + notCompleteNoticetList.size();
        }

        @Override
        public Object getItem(int position) {
            return weixiudans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position,  View convertView, ViewGroup parent) {
            View result = null;

            LayoutInflater inflater = (LayoutInflater)WeixiudanList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            switch (viewTypes.get(position)){
                case WEIXIUDAN_HEADER:
                    View list_seperator = inflater.inflate(R.layout.weixiudan_list_seperator, null);
                    TextView groupName = (TextView)list_seperator.findViewById(R.id.display);
                    Button uploadWeixiudan      = (Button)list_seperator.findViewById(R.id.upload);
                    uploadWeixiudan.setOnClickListener(new onUploadWeixiudanClick());
                    groupName.setText("维修单");
                    result = list_seperator;
                    break;
                case WEIXIUDAN_VIEW:
                    Weixiudan weixiudan = weixiudans.get( position - 1);
                    View weixiudan_item = inflater.inflate(R.layout.weixiudan_item, null);
                    TextView title = (TextView) weixiudan_item.findViewById(R.id.title);
                    title.setText(weixiudan.mYezhuName + "(" + weixiudan.address() + ")");
                    TextView detail  = (TextView)weixiudan_item.findViewById(R.id.content);
                    detail.setText(weixiudan.mBaoxiuNeirong);
                    result = weixiudan_item;
                    break;
                case NOT_ACCEPT_NOTICE_HEADER:
                    View not_accept_notice_list_seperator = inflater.inflate(R.layout.weixiudan_list_seperator, null);
                    TextView not_accept_group_name = (TextView)not_accept_notice_list_seperator.findViewById(R.id.display);
                    Button uploadAcceptNotice      = (Button)not_accept_notice_list_seperator.findViewById(R.id.upload);
                    uploadAcceptNotice.setOnClickListener(new OnUploadAcceptNoticeClick());
                    not_accept_group_name.setText("接单");
                    result = not_accept_notice_list_seperator;
                    break;
                case NOT_ACCEPT_NOTICE_VIEW:
                    Notice notice =  notAcceptNoticeList.get(position - 2 - weixiudans.size());
                    View not_accept_notice_item = inflater.inflate(R.layout.not_upload_accept_notice_item, null);
                    TextView not_accept_notice_title = (TextView) not_accept_notice_item.findViewById(R.id.title);
                    not_accept_notice_title.setText(notice.danjuBiaoti + "(" + notice.danjuLeixing + ")");
                    TextView not_accept_detail  = (TextView)not_accept_notice_item.findViewById(R.id.content);
                    not_accept_detail.setText(notice.danjuNeirong);
                    result = not_accept_notice_item;
                    break;
                case NOT_COMPLETE_NOTICE_HEADER:
                    View not_complete_notice_list_seperator = inflater.inflate(R.layout.weixiudan_list_seperator, null);
                    TextView not_complete_group_name = (TextView)not_complete_notice_list_seperator.findViewById(R.id.display);
                    Button uploadCompleteNotice      = (Button)not_complete_notice_list_seperator.findViewById(R.id.upload);
                    uploadCompleteNotice.setOnClickListener(new OnUploadCompleteNoticeClick());
                    not_complete_group_name.setText("完成单据");
                    result = not_complete_notice_list_seperator;
                    break;
                case NOT_COMPLETE_NOTICE_VIEW:
                    Notice na_notice = notCompleteNoticetList.get(position - 3 - weixiudans.size() - notAcceptNoticeList.size());
                    View not_complete_notice_item = inflater.inflate(R.layout.not_upload_complete_notice_item, null);
                    TextView not_complete_notice_title = (TextView) not_complete_notice_item.findViewById(R.id.title);
                    not_complete_notice_title.setText(na_notice.danjuBiaoti + "(" + na_notice.danjuLeixing + ")");
                    TextView not_complete_detail  = (TextView)not_complete_notice_item.findViewById(R.id.content);
                    not_complete_detail.setText(na_notice.danjuNeirong);
                    result = not_complete_notice_item;
                    break;
                default:
                    break;

            }


            return result;
        }

        public class onUploadWeixiudanClick implements Button.OnClickListener{

            @Override
            public void onClick(View v) {
                if(!Util.instance().isNetworkConnected(WeixiudanList.this)){
                    UIHelper.showLongToast(WeixiudanList.this, getString(R.string.network_error));

                    return;
                }
                progressDialog.show();
                for(final Weixiudan weixiudan : weixiudans)    {
                    RequestParams params = new RequestParams();
                    try {

                        if( weixiudan.image1 != null) {
                            params.put("image1", new File("/sdcard/" + WeixiudanList.this.getPackageName() + "/" + weixiudan.image1));
                        }
                        if (weixiudan.image2 != null) {
                            params.put("image2", new File("/sdcard/" + WeixiudanList.this.getPackageName() + "/" + weixiudan.image2));
                        }
                        if (weixiudan.image3 != null) {
                            params.put("image3", new File("/sdcard/" + WeixiudanList.this.getPackageName() + "/" + weixiudan.image3));
                        }
                    } catch(FileNotFoundException e) {}
                    httpHelper.post("weixiudans?" + weixiudan.toQuery(), params, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int i, JSONObject jsonObject) {
                            Log.d("onSuccess", "onSuccess");
                            weixiudan.mRemoteSaved = 1;
                            weixiudan.save();

                            super.onSuccess(i, jsonObject);
                            Message message = new Message();
                            message.what = WEIXIUDAN_SAVE_OK;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onFailure(Throwable throwable, JSONObject jsonObject) {
                            Log.d("onFailure", "throwable");
                            super.onFailure(throwable, jsonObject);
                            Message message = new Message();
                            message.what = WEIXIUDAN_SAVE_FAILE;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            Log.d("onFInish", "finish");

                        }
                    });
                }

            }
        }

        public class OnUploadAcceptNoticeClick implements Button.OnClickListener{

            @Override
            public void onClick(View v) {
                if(!Util.instance().isNetworkConnected(WeixiudanList.this)){
                    UIHelper.showLongToast(WeixiudanList.this, getString(R.string.network_error));
                    return;
                }
                progressDialog.show();
                for(final Notice notice : notAcceptNoticeList){
                    httpHelper.with("jiedan?id=" + notice.remoteId, null, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            notice.acceptUpload = true;
                            notice.save();

                            Message message = new Message();
                            message.what = NOT_ACCEPT_NOTICE_SAVE_OK;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onFailure(Throwable throwable, JSONObject jsonObject) {
                            Message message = new Message();
                            message.what = NOT_ACCEPT_NOTICE_SAVE_FAILED;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onFinish() {

                        }
                    });
                }
            }
        }

        public class OnUploadCompleteNoticeClick implements Button.OnClickListener{

            @Override
            public void onClick(View v) {
                if(!Util.instance().isNetworkConnected(WeixiudanList.this)){
                    UIHelper.showLongToast(WeixiudanList.this, getString(R.string.network_error));
                    return;
                }
                progressDialog.show();
                for(final Notice notice : notCompleteNoticetList){
                    final String path = notice.isComplete ? "wancheng" : "daixiu";
                    httpHelper.with(path + "?id=" + notice.remoteId, null, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            if(path.equals("wancheng")){
                                notice.completeUpload = true;
                                notice.save();
                            }else{
                                notice.daixiuUpload = true;
                                notice.save();
                            }

                            Message message = new Message();
                            message.what = NOT_COMPLETE_NOTICE_SAVE_OK;
                            handler.sendMessage(message);

                        }

                        @Override
                        public void onFailure(Throwable throwable, JSONObject jsonObject) {
                            Message message = new Message();
                            message.what = NOT_COMPLETE_NOTICE_SAVE_FAILED;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onFinish() {

                        }
                    });
                }
            }
        }
    }


}
