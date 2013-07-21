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
    private EditText mBarCodeEditText;
    private Button mScanButton;
    private ListView mXunjianxiangmus;
    private ArrayList<Xunjianxiangmu> xunjianxiangmus;
    private Xunjiandian mXunjiandian;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xunjiandian);

        this.mXunjiandian = com.example.pad.models.Xunjiandian.findByRemoteId(getIntent().getIntExtra("xunjiandian_id", 0));

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowTitleEnabled(true);
        bar.setIcon(getResources().getDrawable(R.drawable.icon_zhuye));
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.top));
        bar.setTitle(this.mXunjiandian.mMingcheng);

        mBarCodeEditText = (EditText)findViewById(R.id.barcode);
        mScanButton      = (Button)findViewById(R.id.scan);
        mXunjianxiangmus = (ListView)findViewById(R.id.xunjianxiangmus);

        mScanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(XunjiandianView.this);
                intentIntegrator.initiateScan();
            }

        });


        xunjianxiangmus = (ArrayList<Xunjianxiangmu>)this.mXunjiandian.xunjianxiangmus();
        ArrayList<String> xunjianxiangmuStrs = new ArrayList<String>();
        for (Xunjianxiangmu xujianxiangmu : xunjianxiangmus) {
           xunjianxiangmuStrs.add(xujianxiangmu.mMingcheng);
        }
        mXunjianxiangmus.setAdapter(new ArrayAdapter<String>(XunjiandianView.this, android.R.layout.simple_list_item_1, xunjianxiangmuStrs));
        mXunjianxiangmus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(XunjiandianView.this, XunjianxiangmuView.class);
                intent.putExtra("xunjianxiangmu_id", xunjianxiangmus.get(position).mRemoteID);
                startActivity(intent);
            }
        });

        Log.d("ddd", "" + xunjianxiangmus.size());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(scanResult != null) {
            Log.i("SCAN", "scan result: " + scanResult);
            mBarCodeEditText.setText(scanResult.getContents());
        } else
            Log.e("SCAN", "Sorry, the scan was unsuccessful...");
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

}