package com.example.pad.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.UIHelper;
import com.example.pad.common.Util;
import com.example.pad.models.Cidian;
import com.example.pad.models.Notice;
import com.example.pad.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private Spinner description;
    private Button submit;
    ProgressDialog progressDialog;
    private RadioGroup radioGroup;
    private RadioButton wancheng_radio;
    private RadioButton daixiu_raidio;
    HttpHelper httpHelper;
    ArrayAdapter<String> adapter;

    public void onCreate(Bundle savedInstanceState) {
        final Notice n = Notice.findById(getIntent().getLongExtra("notice_id", 0l));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_complete_form);

        sendPerson = (TextView)findViewById(R.id.sendperson);
        sendTime   = (TextView)findViewById(R.id.sendtime);
        content    = (TextView)findViewById(R.id.content);
        submit = (Button)findViewById(R.id.submit_btn);
        description = (Spinner)findViewById(R.id.description);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        wancheng_radio = (RadioButton)findViewById(R.id.wancheng_radio);
        daixiu_raidio  = (RadioButton)findViewById(R.id.daixiu_radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()){
                    case R.id.wancheng_radio:
                        ArrayList<String> m= new ArrayList<String>();
                        for(Cidian u : Cidian.allWanCheng()){
                            m.add(u.mMingcheng);
                        }

                        adapter = new ArrayAdapter<String>(NoticeCompleteForm.this,android.R.layout.simple_spinner_item, m);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        description.setAdapter(adapter);
                        break;
                    case R.id.daixiu_radio:
                        ArrayList<String> s= new ArrayList<String>();
                        for(Cidian u : Cidian.allJiedan()){
                            s.add(u.mMingcheng);
                        }

                        adapter = new ArrayAdapter<String>(NoticeCompleteForm.this,android.R.layout.simple_spinner_item, s);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        description.setAdapter(adapter);
                        break;
                    default:
                        break;
                }


            }
        });

        sendPerson.setText(n.sendPerson);
        sendTime.setText(n.sendTime);
        content.setText(n.danjuNeirong);
        progressDialog = new ProgressDialog(this);

        ArrayList<String> m= new ArrayList<String>();
        for(Cidian u : Cidian.allWanCheng()){
            m.add(u.mMingcheng);
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        description.setAdapter(adapter);


        httpHelper = new HttpHelper(this, Util.instance().current_user.login, Util.instance().current_user.password);

        submit.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String desc ="";
                if(description.getSelectedItem() != null) {
                    desc = description.getSelectedItem().toString();
                }

                String path;
                if(radioGroup.getCheckedRadioButtonId() == R.id.wancheng_radio){
                    path = "wancheng";
                    n.complete();
                }  else{
                    path = "daixiu";
                    n.daixiu();
                }
                if(!Util.instance().isNetworkConnected(NoticeCompleteForm.this)){
                    UIHelper.showLongToast(NoticeCompleteForm.this, getString(R.string.network_error));

                    return;
                }
                progressDialog.setTitle(R.string.wait_please);
                progressDialog.setMessage("保存中");
                progressDialog.show();

                httpHelper.with(path + "?id=" + n.remoteId + "&desc="  + desc, null, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        if (radioGroup.getCheckedRadioButtonId() == R.id.wancheng_radio){
                           n.completeUpload = true;
                        }   else {
                            n.daixiuUpload = true;
                        }
                        n.save();
                        Toast.makeText(NoticeCompleteForm.this, R.string.wancheng_ok, Toast.LENGTH_SHORT).show();
                        redirectWithClearTop(NoticeCompleteForm.this, Maintain.class);

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