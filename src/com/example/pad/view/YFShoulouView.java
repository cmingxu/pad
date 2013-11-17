package com.example.pad.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.StringUtils;
import com.example.pad.common.UIHelper;
import com.example.pad.models.AddressChooserResult;
import com.example.pad.models.Danyuan;
import com.example.pad.models.YFFjlx;

import java.util.ArrayList;
import java.util.Collections;

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
    private ListView shoulous;
    private ArrayList<YFFjlx> fjlxes;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yf_shoulou);

        mDanyuanEditText = (EditText) findViewById(R.id.chooseDanyuan);
        shoulous = (ListView) findViewById(R.id.shoulous);

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
        switch (requestCode) {
            case ADDRESS_REQUEST_CODE:
                if (data != null) {
                    result = (AddressChooserResult) data.getSerializableExtra("result");
                    mDanyuanEditText.setText(result.toHumanJiangeString());

                    final Danyuan danyuan = Danyuan.findByDanyuanbianhao(result.getmDanyuanbianhao());
                    if (!StringUtils.isEmpty(danyuan.mJiange)) {
                        fjlxes = (ArrayList<YFFjlx>) danyuan.huxing().fjlxes();
                        Collections.sort(fjlxes);
                        ArrayList<String> fjlxs = new ArrayList<String>();
                        for (YFFjlx o : fjlxes) {
                            fjlxs.add(o.mFjmc + "( " + o.mFjbh + ")");
                        }
                        shoulous.setAdapter(new ArrayAdapter<String>(YFShoulouView.this, android.R.layout.simple_list_item_1, fjlxs));
                        shoulous.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent i = new Intent();
                                i.setClass(YFShoulouView.this, YFYsdxView.class);
                                Log.d("fjlx","" + fjlxes.get(position).mRemoteId );
                                i.putExtra("fjlx_id", fjlxes.get(position).mRemoteId);
                                i.putExtra("danyuanbianhao", danyuan.mDanyuanbianhao);
                                YFShoulouView.this.startActivity(i);
                            }
                        });
                    } else {
                        UIHelper.showLongToast(YFShoulouView.this, "此单元户型未确定");

                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }


}