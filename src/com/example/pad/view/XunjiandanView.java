package com.example.pad.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.StringUtils;
import com.example.pad.common.UIHelper;
import com.example.pad.models.Xunjiandan;
import com.example.pad.models.Xunjiandian;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/31/13
 * Time: 7:28 AM
 * To change this template use File | Settings | File Templates.
 */


public class XunjiandanView extends BaseActivity {
    Xunjiandan xunjiandan;
    ArrayList<Xunjiandian> mXunjiandians;
    private EditText mBarCodeEditText;
    private Button mScanButton;
    private Button mChaxunButton;
    public String barcode;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xunjian);

        mBarCodeEditText = (EditText) findViewById(R.id.barcode);
        mScanButton = (Button) findViewById(R.id.scan);
        mChaxunButton = (Button) findViewById(R.id.chaxun_btn);

        mScanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(XunjiandanView.this);
                intentIntegrator.initiateScan();
            }

        });




        xunjiandan = Xunjiandan.findByRemoteId(getIntent().getIntExtra("xunjiandan_id", 0));
        mChaxunButton.setOnClickListener(new ChaxunButtonClickListener(xunjiandan));
        Log.d("abcdef", "create");
        setupData();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            Log.i("SCAN", "scan result: " + scanResult);
            mBarCodeEditText.setText(scanResult.getContents());
            barcode = scanResult.getContents();
        } else
            Log.e("SCAN", "Sorry, the scan was unsuccessful...");
        UIHelper.showLongToast(XunjiandanView.this, "对不起， 扫描编码失败！");
    }

    class ChaxunButtonClickListener implements Button.OnClickListener {
        Xunjiandan xunjiandan;

        public ChaxunButtonClickListener(Xunjiandan xunjiandan) {
            this.xunjiandan = xunjiandan;
        }

        @Override
        public void onClick(View v) {
            Log.d("size a", "zheli                 ............");
            barcode = mBarCodeEditText.getText().toString();
            if (StringUtils.isEmpty(barcode)) {
                UIHelper.showLongToast(XunjiandanView.this, "对不起， 请扫描或者输入编码！");
                return;
            }
            ArrayList<Xunjiandian> mXunjiandians = new ArrayList<Xunjiandian>();
            for (Xunjiandian xunjiandian : xunjiandan.xunjiandians()) {
                if(xunjiandian.mBianma.equals(barcode)){
                    mXunjiandians.add(xunjiandian);
                }
            }

            listView.setAdapter(new ListViewAdapter(mXunjiandians));
            Log.d("size a","" + mXunjiandians.size());
            listView.setOnItemClickListener(new OnItemClickListener());
            listView.invalidate();

        }
    }

    @Override
    protected void onResume() {
        Log.d("abcdef", "resume");
        super.onResume();
        setupData();
    }

    public void setupData() {
        Log.d("abcdef draw", "setupdata");
        mXunjiandians = (ArrayList<Xunjiandian>) xunjiandan.xunjiandians();

        listView = (ListView) findViewById(R.id.xunjianxiangmus);
        listView.setAdapter(new ListViewAdapter(mXunjiandians));
        listView.setOnItemClickListener(new OnItemClickListener());

    }


    public class OnItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                redirct to new activity
                Intent intent = new Intent();
                intent.setClass(XunjiandanView.this, XunjiandianView.class);
                intent.putExtra("xunjiandian_id", mXunjiandians.get(position).mRemoteId);
                intent.putExtra("xunjiandan_id", xunjiandan.mRemoteID);
                startActivity(intent);
        }
    }


    class ListViewAdapter extends BaseAdapter{
        ArrayList<Xunjiandian> mXunjiandians;

        public ListViewAdapter(ArrayList<Xunjiandian> xunjiandians){
            this.mXunjiandians = xunjiandians;
        }

        @Override
        public int getCount() {
            return mXunjiandians.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d("abcdef draw", "" + position);
            Xunjiandian xunjiandian = mXunjiandians.get(position);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.xunjiandian_item, null);
            }

            TextView ysdxName = (TextView) convertView.findViewById(R.id.xunjiandian_name);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.xunjiandian_icon);
            if(xunjiandian.finish(xunjiandan)){
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.yes_icon));
            }{
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.no_icon));
            }
            ysdxName.setText(xunjiandian.mMingcheng);
            return convertView;
        }
    }

}