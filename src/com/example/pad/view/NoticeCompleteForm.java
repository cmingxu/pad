package com.example.pad.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
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
public class NoticeCompleteForm extends BaseActivity {
    private TextView sendPerson;
    private TextView sendTime;
    private TextView content;
    private EditText description;
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
        description = (EditText)findViewById(R.id.complete_desc) ;

        sendPerson.setText(n.sendPerson);
        sendTime.setText(n.sendTime);
        content.setText(n.danjuNeirong);
        progressDialog = new ProgressDialog(this);

        httpHelper = new HttpHelper(this, Util.instance().current_user.login, Util.instance().current_user.password);

        accept_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String desc = description.getText().toString();
                progressDialog.setTitle(R.string.wait_please);
                progressDialog.setMessage(getString(R.string.users_reloading));
                progressDialog.show();


                httpHelper.with("/wancheng?id" + n.remoteId + "&desc="  + desc, null, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        n.complete();
                        Toast.makeText(NoticeCompleteForm.this, R.string.wancheng_ok, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent();
                        i.setClass(NoticeCompleteForm.this, Maintain.class);
                        startActivity(i);
                        NoticeCompleteForm.this.finish();
                    }

                    @Override
                    public void onFailure(Throwable throwable, JSONObject jsonObject) {
                        progressDialog.dismiss();
                        Toast.makeText(NoticeCompleteForm.this, R.string.wancheng_failed, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {

                    }
                });

            }
        });



    }

}