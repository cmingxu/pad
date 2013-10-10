package com.example.pad.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.*;
import com.example.pad.models.CachedRequest;
import com.example.pad.models.DanyuanbiaoChaobiao;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 9/23/13
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class DanyuanbiaochaobiaoForm extends BaseActivity {
    private EditText dangqianbiaoshu;
    private TextView biaomingcheng;
    private TextView lastCountTv;
    private Button chaobiaoButton;
    private DanyuanbiaoChaobiao danyuanbiaoChaobiao;
    private String danyuan;
    HttpHelper httpHelper;
    Handler handler;
    ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chaobiao_form);
        dangqianbiaoshu = (EditText)findViewById(R.id.dangqiandushu);
        biaomingcheng = (TextView)findViewById(R.id.biaomingcheng);
        chaobiaoButton = (Button)findViewById(R.id.chaobiao);
        lastCountTv = (TextView)findViewById(R.id.lastCount);

        danyuanbiaoChaobiao = DanyuanbiaoChaobiao.findByRemoteId(getIntent().getIntExtra("danyuanbiaochaobiao_id", 0));

        danyuan = getIntent().getStringExtra("danyuan");
        biaomingcheng.setText(danyuan + "/" + danyuanbiaoChaobiao.mBiaomingcheng);
        lastCountTv.setText(StringUtils.parseToDushu(danyuanbiaoChaobiao.mShangciDushu));

        chaobiaoButton.setOnClickListener(new ChaobiaoButtonListener());

        progressDialog = new ProgressDialog(DanyuanbiaochaobiaoForm.this);



    }

    class ChaobiaoButtonListener implements Button.OnClickListener{

        @Override
        public void onClick(View v) {
            if (!Util.instance().isNetworkConnected(DanyuanbiaochaobiaoForm.this)) {
                UIHelper.showLongToast(DanyuanbiaochaobiaoForm.this, "当前网络不可用， 请重试");

                return;
            }
            String biaoshu  = dangqianbiaoshu.getText().toString();
            if (StringUtils.isEmpty(biaoshu)){
                UIHelper.showLongToast(DanyuanbiaochaobiaoForm.this, "请输入当前表数");
                return;
            }


            CachedRequest cachedRequest = new CachedRequest();
            cachedRequest.setHappenedAt(new Date());
            cachedRequest.setRequest("chaobiao?id=" + danyuanbiaoChaobiao.mRemoteID + "&bencidushu=" + biaoshu);
            cachedRequest.setType("抄表");

            progressDialog.setTitle("");
            progressDialog.setMessage("表数上传中， 请稍后");
            progressDialog.show();

            httpHelper = new HttpHelper(appContext);
            httpHelper.post("chaobiao?id=" + danyuanbiaoChaobiao.mRemoteID + "&bencidushu=" + biaoshu, null,
                    new PadJsonHttpResponseHandler(DanyuanbiaochaobiaoForm.this, progressDialog, cachedRequest){
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    progressDialog.hide();
                    UIHelper.showLongToast(DanyuanbiaochaobiaoForm.this, "上传成功");
                    super.onSuccess(jsonObject);
                }

                @Override
                public void onFailure(Throwable throwable, JSONObject jsonObject) {
                    progressDialog.hide();
                    UIHelper.showLongToast(DanyuanbiaochaobiaoForm.this, "上传失败");
                    super.onFailure(throwable, jsonObject);
                }
            });


        }
    }
}