package com.example.pad.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.example.pad.R;
import com.example.pad.models.AddressChooserResult;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 9/23/13
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DanyuanbiaochaobiaoList extends Activity {
    public static final int ADDRESS_REQUEST_CODE = 1;
    public EditText danyuanChooserEditText;
    public ListView danyuanbiaosListView;
    public AddressChooserResult result;
    public String danyuanBianhao;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chaobiao_list);

        danyuanChooserEditText = (EditText) findViewById(R.id.chooseDanyuan);
        danyuanbiaosListView = (ListView) findViewById(R.id.danyuanbiaos);

//        danyuanChooserEditText.setEnabled(false);
        danyuanChooserEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(DanyuanbiaochaobiaoList.this, AddressChooser.class);
                startActivityForResult(i, ADDRESS_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADDRESS_REQUEST_CODE:
                if (data != null) {
                    result = (AddressChooserResult) data.getSerializableExtra("result");
                    Log.d("result", result.toString());
                    danyuanBianhao = result.getmDanyuanbianhao();
                    danyuanChooserEditText.setText(result.getmLoupanName() + "/" + result.getmLougeName() + "/" + result.getmDanyuanName());
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}