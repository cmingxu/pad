package com.example.pad.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.PadJsonHttpResponseHandler;
import com.example.pad.common.UIHelper;
import com.example.pad.common.Util;
import com.example.pad.models.CachedRequest;
import com.example.pad.models.Notice;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 6/20/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoticeAcceptForm extends BaseActivity {
    ProgressDialog progressDialog;
    HttpHelper httpHelper;
    private TextView sendPerson;
    private TextView sendTime;
    private TextView content;
    private Button accept_btn;

    public void onCreate(Bundle savedInstanceState) {
        final Notice n = Notice.findById(getIntent().getLongExtra("notice_id", 0l));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_accept_form);
//
//        sendPerson = (TextView)findViewById(R.id.sendperson);
//        sendTime   = (TextView)findViewById(R.id.sendtime);
        content = (TextView) findViewById(R.id.content);
        accept_btn = (Button) findViewById(R.id.action);

//        sendPerson.setText(n.sendPerson);
//        sendTime.setText(n.sendTime);
        content.setText(n.danjuNeirong);

        progressDialog = new ProgressDialog(this);
        httpHelper = new HttpHelper(appContext);

        accept_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                n.accept();
                if (!Util.instance().isNetworkConnected(NoticeAcceptForm.this)) {
                    UIHelper.showLongToast(NoticeAcceptForm.this, getString(R.string.network_error));

                    return;
                }

                final CachedRequest cachedRequest = new CachedRequest();
                cachedRequest.happenedAt = new Date();
                cachedRequest.request_path = "jiedan?id=" + n.remoteId;
                cachedRequest.resource_type = "维修单接单";
                cachedRequest.resource_id = n.getId();

                progressDialog.setTitle(R.string.wait_please);
                progressDialog.setMessage("接单中");
                progressDialog.show();

                httpHelper.with("jiedan?id=" + n.remoteId, null,
                        new PadJsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                progressDialog.dismiss();
                                NoticeAcceptForm.this.finish();
                                UIHelper.showLongToast(NoticeAcceptForm.this, R.string.jiedan_ok);
                                n.save();
                            }

                            @Override
                            public void failure(String message) {
                                NoticeAcceptForm.this.finish();
                                if (progressDialog != null) {
                                    progressDialog.hide();
                                }
                                UIHelper.showLongToast(NoticeAcceptForm.this, message);
                                if (cachedRequest != null) {
                                    Log.d("cachedrequest", cachedRequest.request_path);
                                    UIHelper.showLongToast(NoticeAcceptForm.this, R.string.jiedan_saved_failed_will_retry_for_you);
                                    cachedRequest.save();
                                }
                                super.failure(message);
                            }
                        });

            }
        });


    }
}