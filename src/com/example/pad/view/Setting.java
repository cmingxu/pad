package com.example.pad.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.PadJsonHttpResponseHandler;
import com.example.pad.common.UIHelper;
import com.example.pad.common.Util;
import com.example.pad.models.*;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 10/9/13
 * Time: 2:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Setting extends BaseActivity {
    public static final int UPLOAD_SCUESS = 0;
    public static final int UPLOAD_FAIL = 1;
    public RelativeLayout bdscRelativeLayout;
    public RelativeLayout jdscRelativeLayout;
    public RelativeLayout wcscRelativeLayout;
    public RelativeLayout xjscRelativeLayout;
    public RelativeLayout cbscRelativeLayout;
    public RelativeLayout yfscRelativeLayout;
    public TextView bdscTextView;
    public TextView jdscTextView;
    public TextView wcscTextView;
    public TextView xjscTextView;
    public TextView cbscTextView;
    public TextView yfscTextView;
    public ArrayList<CachedRequest> cachedWeixiudanRequests;
    public ArrayList<CachedRequest> cachedCompleteNoticeRequests;
    public ArrayList<CachedRequest> cachedJiedanNoticeRequests;
    public ArrayList<CachedRequest> cachedXunjianRequests;
    public ArrayList<CachedRequest> cachedChaobiaoRequests;
    public ArrayList<CachedRequest> cachedYanfangRequests;
    ProgressDialog progressDialog;
    Handler handler;
    int cachedRequestCount;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        bdscRelativeLayout = (RelativeLayout) findViewById(R.id.bdsc_relative_layout);
        jdscRelativeLayout = (RelativeLayout) findViewById(R.id.jdsc_relative_layout);
        wcscRelativeLayout = (RelativeLayout) findViewById(R.id.wcsc_relative_layout);
        xjscRelativeLayout = (RelativeLayout) findViewById(R.id.xjsc_relative_layout);
        cbscRelativeLayout = (RelativeLayout) findViewById(R.id.cbsc_relative_layout);
        yfscRelativeLayout = (RelativeLayout) findViewById(R.id.yfsc_relative_layout);

        bdscTextView = (TextView) findViewById(R.id.bdsc_view);
        jdscTextView = (TextView) findViewById(R.id.jdsc_view);
        wcscTextView = (TextView) findViewById(R.id.wcsc_view);
        xjscTextView = (TextView) findViewById(R.id.xjsc_view);
        cbscTextView = (TextView) findViewById(R.id.cbsc_view);
        yfscTextView = (TextView) findViewById(R.id.yfsc_view);

        bdscRelativeLayout.setOnClickListener(new ViewController());
        jdscRelativeLayout.setOnClickListener(new ViewController());
        wcscRelativeLayout.setOnClickListener(new ViewController());
        xjscRelativeLayout.setOnClickListener(new ViewController());
        cbscRelativeLayout.setOnClickListener(new ViewController());
        yfscRelativeLayout.setOnClickListener(new ViewController());

        cachedWeixiudanRequests = (ArrayList<CachedRequest>) CachedRequest.unsavedWeixiudanCachedRequests();
        cachedCompleteNoticeRequests = (ArrayList<CachedRequest>) CachedRequest.unsavedWanchengCachedRequests();
        cachedJiedanNoticeRequests = (ArrayList<CachedRequest>) CachedRequest.unsavedJiedanCachedRequests();
        cachedXunjianRequests = (ArrayList<CachedRequest>) CachedRequest.unsavedXunjianCachedRequests();
        cachedChaobiaoRequests = (ArrayList<CachedRequest>) CachedRequest.unsavedChaobiaoCachedRequests();
        cachedYanfangRequests = (ArrayList<CachedRequest>) CachedRequest.unsavedYanfangCachedRequests();

        bdscTextView.setText("(" + cachedWeixiudanRequests.size() + ")");
        jdscTextView.setText("(" + cachedJiedanNoticeRequests.size() + ")");
        wcscTextView.setText("(" + cachedCompleteNoticeRequests.size() + ")");
        xjscTextView.setText("(" + cachedXunjianRequests.size() + ")");
        cbscTextView.setText("(" + cachedChaobiaoRequests.size() + ")");
        yfscTextView.setText("(" + cachedYanfangRequests.size() + ")");


        progressDialog = new ProgressDialog(Setting.this);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPLOAD_SCUESS:
                        cachedRequestCount -= 1;

                        break;
                    case UPLOAD_FAIL:
                        cachedRequestCount -= 1;

                        break;
                    default:
                        break;
                }
                if (cachedRequestCount == 0 && progressDialog.isShowing()) {
                    progressDialog.hide();
                }
                super.handleMessage(msg);
            }
        };

    }

    public void bdsc() {
        for (final CachedRequest cachedRequest : cachedWeixiudanRequests) {
            Weixiudan weixiudan = cachedRequest.weixiudan();
            RequestParams params = new RequestParams();
            File imagesDir = new File(weixiudan.mImageDir);
            if (imagesDir.exists() && imagesDir.isDirectory()) {
                int i = 0;
                for (File image : imagesDir.listFiles()) {
                    Log.d("image", "images");
                    try {
                        params.put("image" + i, image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    i = i + 1;
                }
            }

            new HttpHelper(appContext).post("weixiudans?" + weixiudan.toQuery(), params,
                    new PadJsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int i, JSONObject jsonObject) {
                            Message message = new Message();
                            message.what = UPLOAD_SCUESS;
                            handler.sendMessage(message);
                            UIHelper.showLongToast(Setting.this, R.string.weixiudan_saved);

                            cachedRequest.delete();
                        }

                        @Override
                        public void failure(String message) {
                            Message failMessage = new Message();
                            failMessage.what = UPLOAD_FAIL;
                            handler.sendMessage(failMessage);
                            UIHelper.showLongToast(Setting.this, message);
                            super.failure(message);
                        }

                    });
        }


    }

    public void jdsc() {
        for (final CachedRequest cachedRequest : cachedJiedanNoticeRequests) {
            Notice notice = cachedRequest.jiedan_notice();
            RequestParams requestParams = new RequestParams();
            requestParams.put("id", "" + notice.remoteId);

            new HttpHelper(appContext).with("jiedan", requestParams,
                    new PadJsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            Message message = new Message();
                            message.what = UPLOAD_SCUESS;
                            handler.sendMessage(message);
                            UIHelper.showLongToast(Setting.this, R.string.jiedan_ok);

                            cachedRequest.delete();
                        }

                        @Override
                        public void failure(String message) {
                            Message failMessage = new Message();
                            failMessage.what = UPLOAD_FAIL;
                            handler.sendMessage(failMessage);
                            UIHelper.showLongToast(Setting.this, message);
                            super.failure(message);
                        }
                    });
        }


    }

    public void wcjd() {
        for (final CachedRequest cachedRequest : cachedCompleteNoticeRequests) {
            final Notice notice = cachedRequest.jiedan_notice();
            RequestParams requestParams = new RequestParams();
            requestParams.put("id", "" + notice.remoteId);
            requestParams.put("desc", "" + notice.desc);

            new HttpHelper(appContext).with(notice.res, requestParams,
                    new PadJsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            Message message = new Message();
                            message.what = UPLOAD_SCUESS;
                            handler.sendMessage(message);
                            UIHelper.showLongToast(Setting.this, R.string.jiedan_ok);

                            cachedRequest.delete();
                            notice.delete();
                        }

                        @Override
                        public void failure(String message) {
                            Message failMessage = new Message();
                            failMessage.what = UPLOAD_FAIL;
                            handler.sendMessage(failMessage);
                            UIHelper.showLongToast(Setting.this, message);
                            super.failure(message);
                        }
                    });
        }
    }

    public void xjsc() {
    }

    public void cbsc() {
        for (final CachedRequest cachedRequest : cachedChaobiaoRequests) {
            final DanyuanbiaoChaobiao danyuanbiaochao  = cachedRequest.danyuanbiaoChaobiao();
            RequestParams requestParams = new RequestParams();
            requestParams.put("id", "" + danyuanbiaochao.mRemoteID);
            requestParams.put("bencidushu", danyuanbiaochao.biaoshu);
            new HttpHelper(appContext).post("chaobiao", requestParams,
                    new PadJsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            Message message = new Message();
                            message.what = UPLOAD_SCUESS;
                            handler.sendMessage(message);
                            UIHelper.showLongToast(Setting.this, R.string.save_success);

                            cachedRequest.delete();
                            danyuanbiaochao.delete();
                        }

                        @Override
                        public void failure(String message) {
                            Message failMessage = new Message();
                            failMessage.what = UPLOAD_FAIL;
                            handler.sendMessage(failMessage);
                            UIHelper.showLongToast(Setting.this, message);
                            super.failure(message);
                        }
                    });
        }


    }

    public void yfsc() {
        for (final CachedRequest cachedRequest : cachedCompleteNoticeRequests) {
            final YFYfRecord yfRecord = cachedRequest.yfRecord() ;
            RequestParams params = new RequestParams();
            params.put("mDesc", yfRecord.mDesc);
            params.put("mDxID", "" +yfRecord.mDxID);
            params.put("mFjlxID", "" + yfRecord.mFjlxID);
            params.put("mDanyuanbianhao", yfRecord.mDanyuanbianhao);
            File imagesDir = new File(yfRecord.mImageDir);
            if(imagesDir.exists() && imagesDir.isDirectory()){
                int i = 0;
                for (File image : imagesDir.listFiles()) {
                    try {
                        params.put("image" + i, image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    i = i + 1;
                }
            }


            new HttpHelper(appContext).post("yf_yz_yfd", params, new PadJsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    RequestResponse requestResponse = new RequestResponse(jsonObject);
                    if(requestResponse.ok()){
                        UIHelper.showLongToast(Setting.this, "验房单保存成功");
                        Setting.this.finish();
                        cachedRequest.delete();
                        yfRecord.delete();

                    }else{
                        UIHelper.showLongToast(Setting.this, requestResponse.message);
                    }
                }

                @Override
                public void failure(String message) {
                    super.failure(message);
                }

            });
        }






    }

    class ViewController implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            if (!Util.instance().isNetworkConnected(Setting.this)) {
                Toast.makeText(Setting.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Util.instance().isServerReachable(getApplicationContext())) {
                Toast.makeText(Setting.this, R.string.server_not_reachable, Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(Setting.this)
                    .setTitle("表单上传")
                    .setMessage("点击确认保存资料到软件?")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog.setTitle(R.string.wait_please);
                            progressDialog.setMessage(getString(R.string.save_and_upload_inprogress));
                            progressDialog.show();

                            int viewId = v.getId();
                            if (viewId == R.id.bdsc_relative_layout) {
                                cachedRequestCount = cachedWeixiudanRequests.size();
                                bdsc();
                            } else if (viewId == R.id.jdsc_relative_layout) {
                                cachedRequestCount = cachedJiedanNoticeRequests.size();
                                jdsc();

                            } else if (viewId == R.id.wcsc_relative_layout) {
                                cachedRequestCount = cachedCompleteNoticeRequests.size();
                                wcjd();

                            } else if (viewId == R.id.xjsc_relative_layout) {
                                cachedRequestCount = cachedXunjianRequests.size();
                                xjsc();

                            } else if (viewId == R.id.cbsc_relative_layout) {
                                cachedRequestCount = cachedChaobiaoRequests.size();
                                cbsc();

                            } else if (viewId == R.id.yfsc_relative_layout) {
                                cachedRequestCount = cachedYanfangRequests.size();
                                yfsc();

                            }
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();


        }
    }

}