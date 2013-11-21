package com.example.pad.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.StringUtils;
import com.example.pad.models.AddressChooserResult;
import com.example.pad.models.DanyuanbiaoChaobiao;
import com.example.pad.models.Louceng;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 9/23/13
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class DanyuanbiaochaobiaoList extends BaseActivity {
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

        danyuanChooserEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setClass(DanyuanbiaochaobiaoList.this, CengAddressChooser.class);
                startActivityForResult(i, ADDRESS_REQUEST_CODE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADDRESS_REQUEST_CODE:
                if (data != null) {
                    Log.d("danyuanbianhao", "danyuanbianhao");
                    result = (AddressChooserResult) data.getSerializableExtra("result");

                    danyuanBianhao = result.getmDanyuanbianhao();
                    danyuanChooserEditText.setText(result.getmLoupanName() + "/" + result.getmLougeName() + "/" + result.getmLoucengName());


                    final ArrayList<DanyuanbiaoChaobiao> danyuanbiaoChaobiaos =  Louceng.danyuanbiaochaobiaoByLoucengBianhao(result.getmLoucengbianhao()) ;
                    danyuanbiaosListView.setAdapter(new ListViewAdapter(danyuanbiaoChaobiaos));
                    danyuanbiaosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            DanyuanbiaoChaobiao danyuanbiaoChaobiao = danyuanbiaoChaobiaos.get(position);
                            Intent intent = new Intent();
                            intent.setClass(DanyuanbiaochaobiaoList.this, DanyuanbiaochaobiaoForm.class);
                            intent.putExtra("danyuanbiaochaobiao_id", danyuanbiaoChaobiao.mRemoteID);
                            intent.putExtra("louceng", result.getmLoucengName());
                            startActivity(intent);
                        }
                    });
                    danyuanbiaosListView.invalidate();
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class ListViewAdapter extends BaseAdapter{
        ArrayList<DanyuanbiaoChaobiao> danyuanbiaochaobiaos;

        ListViewAdapter(ArrayList<DanyuanbiaoChaobiao> danyuanbiaochaobiaos) {
            this.danyuanbiaochaobiaos = danyuanbiaochaobiaos;
        }

        @Override
        public int getCount() {
            return danyuanbiaochaobiaos.size();
        }

        @Override
        public Object getItem(int position) {
            return danyuanbiaochaobiaos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            DanyuanbiaoChaobiao danyuanbiaoChaobiao = danyuanbiaochaobiaos.get(position);

            View view =  getLayoutInflater().inflate(R.layout.danyuanbiaochaobiao_item, null);
            TextView biaomingcheng = (TextView)view.findViewById(R.id.biaomingcheng);
            TextView shangcidushu = (TextView)view.findViewById(R.id.shangcibiaoshu);

            biaomingcheng.setText(danyuanbiaoChaobiao.mBiaomingcheng);
            shangcidushu.setText(StringUtils.parseToDushu(danyuanbiaoChaobiao.mShangciDushu));

            return view;
        }
    }
}