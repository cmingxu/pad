package com.example.pad.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.UIHelper;
import com.example.pad.models.AddressChooserResult;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 11/14/13
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class YFShoulouView extends BaseActivity {
    public static final int ADDRESS_REQUEST_CODE = 0;
    private EditText mDanyuanEditText;
    private AddressChooserResult result;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yf_shoulou);

        mDanyuanEditText = (EditText) findViewById(R.id.chooseDanyuan);

        mDanyuanEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("title", "收楼地址");
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setClass(YFShoulouView.this, DanyuanAddressChooser.class);
                startActivityForResult(i, ADDRESS_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ADDRESS_REQUEST_CODE:
                if (data != null) {
                    result = (AddressChooserResult) data.getSerializableExtra("result");

                    UIHelper.showLongToast(YFShoulouView.this, result.getmDanyuanName());
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}