package com.example.pad.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.UIHelper;
import com.example.pad.common.Util;
import com.example.pad.models.Notice;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 6/20/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoticeAcceptForm extends BaseActivity {
    private TextView sendPerson;
    private TextView sendTime;
    private TextView content;
    private Button accept_btn;
    ProgressDialog progressDialog;
    HttpHelper httpHelper;

    public void onCreate(Bundle savedInstanceState) {
        final Notice n = Notice.findById(getIntent().getLongExtra("notice_id", 0l));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_accept_form);

        sendPerson = (TextView)findViewById(R.id.sendperson);
        sendTime   = (TextView)findViewById(R.id.sendtime);
        content    = (TextView)findViewById(R.id.content);
        accept_btn = (Button)findViewById(R.id.action);

        sendPerson.setText(n.sendPerson);
        sendTime.setText(n.sendTime);
        content.setText(n.danjuNeirong);

        progressDialog = new ProgressDialog(this);
        httpHelper = new HttpHelper(appContext);

        accept_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                n.accept();
                if(!Util.instance().isNetworkConnected(NoticeAcceptForm.this)){
                    UIHelper.showLongToast(NoticeAcceptForm.this, getString(R.string.network_error));

                    return;
                }

                progressDialog.setTitle(R.string.wait_please);
                progressDialog.setMessage("接单中");
                progressDialog.show();

                httpHelper.with("jiedan?id=" + n.remoteId, null, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        n.acceptUpload = true;
                        n.save();
                        redirectWithClearTop(NoticeAcceptForm.this, Maintain.class);
                    }

                    @Override
                    public void onFailure(Throwable throwable, JSONObject jsonObject) {
                        progressDialog.dismiss();
                        Toast.makeText(NoticeAcceptForm.this, R.string.jiedan_failed, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {

                    }
                });

            }
        });



    }
}