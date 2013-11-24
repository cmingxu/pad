package com.example.pad.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.pad.AppContext;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.PadJsonHttpResponseHandler;
import com.example.pad.common.UIHelper;
import com.example.pad.models.CachedRequest;
import com.example.pad.models.Cidian;
import com.example.pad.models.Notice;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 6/20/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoticeCompleteForm extends BaseActivity {
    ProgressDialog progressDialog;
    HttpHelper httpHelper;
    ArrayAdapter<String> adapter;
    private TextView content;
    private Spinner description;
    private Button submit;
    private RadioGroup radioGroup;
    private RadioButton wancheng_radio;
    private RadioButton daixiu_raidio;

    public void onCreate(Bundle savedInstanceState) {
        final Notice n = Notice.findById(getIntent().getLongExtra("notice_id", 0l));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_complete_form);


        content = (TextView) findViewById(R.id.content);
        submit = (Button) findViewById(R.id.submit_btn);
        description = (Spinner) findViewById(R.id.description);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        wancheng_radio = (RadioButton) findViewById(R.id.wancheng_radio);
        daixiu_raidio = (RadioButton) findViewById(R.id.daixiu_radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.wancheng_radio:
                        ArrayList<String> m = new ArrayList<String>();
                        for (Cidian u : Cidian.allWanCheng()) {
                            m.add(u.mMingcheng);
                        }

                        adapter = new ArrayAdapter<String>(NoticeCompleteForm.this, android.R.layout.simple_spinner_item, m);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        description.setAdapter(adapter);
                        break;
                    case R.id.daixiu_radio:
                        ArrayList<String> s = new ArrayList<String>();
                        for (Cidian u : Cidian.allJiedan()) {
                            s.add(u.mMingcheng);
                        }

                        adapter = new ArrayAdapter<String>(NoticeCompleteForm.this, android.R.layout.simple_spinner_item, s);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        description.setAdapter(adapter);
                        break;
                    default:
                        break;
                }


            }
        });


        content.setText(n.danjuNeirong);
        progressDialog = new ProgressDialog(this);

        ArrayList<String> m = new ArrayList<String>();
        for (Cidian u : Cidian.allWanCheng()) {
            m.add(u.mMingcheng);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        description.setAdapter(adapter);


        httpHelper = new HttpHelper((AppContext) getApplication());

        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String desc = "";
                if (description.getSelectedItem() != null) {
                    desc = description.getSelectedItem().toString();
                }

                String path;
                if (radioGroup.getCheckedRadioButtonId() == R.id.wancheng_radio) {
                    path = "wancheng";
                    n.complete();
                } else {
                    path = "daixiu";
                    n.daixiu();
                }

                n.desc = desc;
                n.res = path;
                n.save();

                RequestParams requestParams = new RequestParams();

                final CachedRequest cachedRequest = new CachedRequest();
                cachedRequest.happenedAt = new Date();
                cachedRequest.request_path = path;
                cachedRequest.resource_type = "维修单完成";
                cachedRequest.resource_id = n.getId();

                requestParams.put("id", "" + n.remoteId);
                requestParams.put("desc", desc);
                progressDialog.setTitle(R.string.wait_please);
                progressDialog.setMessage("保存中");
                progressDialog.show();

                httpHelper.with(path, requestParams,
                        new PadJsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                progressDialog.dismiss();
                                NoticeCompleteForm.this.finish();
                                n.delete();
                                Toast.makeText(NoticeCompleteForm.this, R.string.wancheng_ok, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void failure(String message) {
                                NoticeCompleteForm.this.finish();
                                if (progressDialog != null) {
                                    progressDialog.hide();
                                }
                                UIHelper.showLongToast(NoticeCompleteForm.this, message);
                                if (cachedRequest != null) {
                                    Log.d("cachedrequest", cachedRequest.request_path);
                                    UIHelper.showLongToast(NoticeCompleteForm.this, R.string.notice_complete_saved_failed_will_retry_for_you);
                                    n.display = false;
                                    n.save();
                                    cachedRequest.save();
                                }
                                super.failure(message);
                            }

                        });

            }
        });


    }

}