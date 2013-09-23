package com.example.pad.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.StringUtils;
import com.example.pad.common.UIHelper;
import com.example.pad.models.Xunjiandan;
import com.example.pad.models.Xunjiandian;
import com.example.pad.models.Xunjianxiangmu;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 7/20/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class XunjiandianView extends BaseActivity {
    public String barcode;
    private Xunjiandan mXunjiandan;
    private EditText mBarCodeEditText;
    private Button mScanButton;
    private Button mChaxunButton;
    private ListView mXunjianxiangmus;
    private ArrayList<Xunjianxiangmu> xunjianxiangmus;
    private Xunjiandian mXunjiandian;
    private int xunjiandian_id;
    private int xunjiandan_id;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xunjiandian);
        this.xunjiandan_id = getIntent().getIntExtra("xunjiandan_id", 0);
        this.xunjiandian_id = getIntent().getIntExtra("xunjiandian_id", 0);

        this.mXunjiandian = Xunjiandian.findByRemoteId(this.xunjiandian_id);
        this.mXunjiandan = Xunjiandan.findByRemoteId(this.xunjiandan_id);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowTitleEnabled(true);
        bar.setIcon(getResources().getDrawable(R.drawable.icon_zhuye));
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.top));
        bar.setTitle(this.mXunjiandian.mMingcheng);

        mBarCodeEditText = (EditText) findViewById(R.id.barcode);
        mBarCodeEditText.setText(this.mXunjiandian.mBianma);
        mScanButton = (Button) findViewById(R.id.scan);
        mChaxunButton = (Button) findViewById(R.id.chaxun_btn);
        mXunjianxiangmus = (ListView) findViewById(R.id.xunjianxiangmus);

        mScanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(XunjiandianView.this);
                intentIntegrator.initiateScan();
            }

        });

        mChaxunButton.setOnClickListener(new ChaxunButtonClickListener(this.mXunjiandan));

        xunjianxiangmus = (ArrayList<Xunjianxiangmu>) this.mXunjiandian.xunjianxiangmusForXunjiandan(this.mXunjiandan.mRemoteID);
        ArrayList<String> xunjianxiangmuStrs = new ArrayList<String>();
        for (Xunjianxiangmu xujianxiangmu : xunjianxiangmus) {
            xunjianxiangmuStrs.add(xujianxiangmu.mMingcheng);
        }
        mXunjianxiangmus.setAdapter(new ArrayAdapter<String>(XunjiandianView.this, android.R.layout.simple_list_item_1, xunjianxiangmuStrs));
        mXunjianxiangmus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(XunjiandianView.this, XunjianmingxiView.class);
                intent.putExtra("xunjianxiangmu_id", xunjianxiangmus.get(position).mRemoteID);
                intent.putExtra("xunjiandan_id", mXunjiandan.mRemoteID);
                startActivity(intent);
            }
        });

        Log.d("ddd", "" + xunjianxiangmus.size());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            Log.i("SCAN", "scan result: " + scanResult);
            mBarCodeEditText.setText(scanResult.getContents());
            barcode = scanResult.getContents();
        } else
            Log.e("SCAN", "Sorry, the scan was unsuccessful...");
        UIHelper.showLongToast(XunjiandianView.this, "对不起， 扫描编码失败！");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                XunjiandianView.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class ChaxunButtonClickListener implements Button.OnClickListener {
        Xunjiandan xunjiandan;
        Xunjiandian xunjiandian;
        ArrayList<Xunjiandian> xunjiandians;


        public ChaxunButtonClickListener(Xunjiandan xunjiandan) {
            this.xunjiandan = xunjiandan;
            xunjiandians = (ArrayList<Xunjiandian>) xunjiandan.xunjiandians(false);
        }

        @Override
        public void onClick(View v) {
            barcode = mBarCodeEditText.getText().toString();
            if (StringUtils.isEmpty(barcode)) {
                UIHelper.showLongToast(XunjiandianView.this, "对不起， 请扫描或者输入编码！");
                return;
            }

            for (Xunjiandian x : xunjiandians) {
                if (x.mBianma.equals(barcode)) {
                    xunjiandian = x;
                    break;
                }
            }

            if (xunjiandian == null) {
                UIHelper.showLongToast(XunjiandianView.this, "对不起， 没能找到此未完成巡检点！");
                return;
            } else {
                Log.d("xunjiandian", xunjiandian.mBianma);
                Log.d("xunjiandian", xunjiandan.mDanjuBianHao);

                Intent intent = new Intent();
                intent.putExtra("xunjiandan_id", mXunjiandan.mRemoteID);
                intent.putExtra("xunjiandian_id", xunjiandian.mRemoteId);
                intent.setClass(XunjiandianView.this, XunjiandianView.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }

        }
    }

}