package com.example.pad.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.StringUtils;
import com.example.pad.common.UIHelper;
import com.example.pad.models.DanyuanbiaoChaobiao;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;

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


    }

    class ChaobiaoButtonListener implements Button.OnClickListener{

        @Override
        public void onClick(View v) {
            String biaoshu  = dangqianbiaoshu.getText().toString();
            if (StringUtils.isEmpty(biaoshu)){
                UIHelper.showLongToast(DanyuanbiaochaobiaoForm.this, "请输入当前表数");
            }


            httpHelper = new HttpHelper(appContext);
            httpHelper.post("chaobiao?id=" + danyuanbiaoChaobiao.mRemoteID + "&bencidushu=" + biaoshu, null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    super.onSuccess(jsonObject);
                }
            });


        }
    }
}