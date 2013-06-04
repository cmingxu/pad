package com.example.pad.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.Util;
import com.example.pad.models.Syssend;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 6/4/13
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SyssendForm extends Activity {
    private TextView sendperson;
    private TextView sendtime;
    private TextView content;
    private Button action;
    Syssend syssend;
    ProgressDialog progressDialog;
    HttpHelper httpHelper;
    String path = "";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long syssend_id = getIntent().getLongExtra("syssend_id", 0);
        syssend = Syssend.findById(syssend_id);

        setContentView(R.layout.syssend_form);

        sendperson = (TextView)findViewById(R.id.sendperson);
        sendperson.setText(syssend.sendperson);
        sendtime   = (TextView)findViewById(R.id.sendtime);
        sendtime.setText(syssend.sendtime);
        content    = (TextView)findViewById(R.id.content);
        content.setText(syssend.content);

        action     = (Button)findViewById(R.id.action);
        if(syssend.ifComplete.equals("0")){
            action.setText("完成");
            path  = "wancheng";
        }
        if(syssend.ifck.equals("0")){
            action.setText("接单");
            path = "jiedan";
        }

        action.setOnClickListener(new OnClickListener());

        progressDialog = new ProgressDialog(SyssendForm.this);
        httpHelper = new HttpHelper(SyssendForm.this, Util.instance().current_user.login, Util.instance().current_user.password);

    }

    class OnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View v) {
            progressDialog.setTitle(R.string.wait_please);
            progressDialog.setMessage(getString(R.string.users_reloading));
            progressDialog.show();
            httpHelper.with(path + "?syssend_id=" + syssend.remoteId, null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONObject s) {
                    progressDialog.dismiss();
                    Toast.makeText(SyssendForm.this, R.string.jiedan_ok, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.setClass(SyssendForm.this, Maintain.class);
                    startActivity(i);
                    SyssendForm.this.finish();
                }

                @Override
                public void onFailure(Throwable throwable, JSONObject jsonObject) {
                    progressDialog.dismiss();
                    Toast.makeText(SyssendForm.this, R.string.jiedan_failed, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
