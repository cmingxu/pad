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
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.Util;
import com.example.pad.models.Syssend;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 6/4/13
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SyssendCompleteForm extends Activity {
    private TextView sendperson;
    private TextView sendtime;
    private TextView content;
    private Button action;
    private EditText complete_desc;
    Syssend syssend;
    ProgressDialog progressDialog;
    HttpHelper httpHelper;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long syssend_id = getIntent().getLongExtra("syssend_id", 0);
        syssend = Syssend.findById(syssend_id);

        setContentView(R.layout.syssend_accept_form);

        sendperson = (TextView)findViewById(R.id.sendperson);
        sendperson.setText(syssend.sendperson);
        sendtime   = (TextView)findViewById(R.id.sendtime);
        sendtime.setText(syssend.sendtime);
        content    = (TextView)findViewById(R.id.content);
        content.setText(syssend.content);
        complete_desc = (EditText)findViewById(R.id.complete_desc);

        action     = (Button)findViewById(R.id.action);

        action.setOnClickListener(new OnClickListener());

        progressDialog = new ProgressDialog(SyssendCompleteForm.this);
        httpHelper = new HttpHelper(SyssendCompleteForm.this, Util.instance().current_user.login, Util.instance().current_user.password);

    }

    class OnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View v) {
            progressDialog.setTitle(R.string.wait_please);
            progressDialog.setMessage(getString(R.string.users_reloading));
            progressDialog.show();
            httpHelper.with("syssends/complete" + "?syssend_id=" + syssend.remoteId, null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONObject s) {
                    Toast.makeText(SyssendCompleteForm.this, R.string.jiedan_ok, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent i = new Intent();
                    i.setClass(SyssendCompleteForm.this, Maintain.class);
                    startActivity(i);
                    SyssendCompleteForm.this.finish();
                }

                @Override
                public void onFailure(Throwable throwable, JSONObject jsonObject) {
                    progressDialog.dismiss();
                    Toast.makeText(SyssendCompleteForm.this, R.string.jiedan_failed, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
