package com.example.pad.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.Xunjiandan;
import com.example.pad.models.Xunjiandian;
import com.example.pad.models.Xunjianxiangmu;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 7/20/13
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class XunjiandianView extends BaseActivity {
    private Xunjiandan mXunjiandan;


    private ListView mXunjianxiangmus;
    private ArrayList<Xunjianxiangmu> xunjianxiangmus;
    private Xunjiandian mXunjiandian;
    private int xunjiandian_id;
    private int xunjiandan_id;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xunjiandian);
        mXunjianxiangmus = (ListView)findViewById(R.id.xunjianxiangmus);
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

        xunjianxiangmus = (ArrayList<Xunjianxiangmu>) this.mXunjiandian.xunjianxiangmusForXunjiandan(this.mXunjiandan.mRemoteID);
        ArrayList<String> xunjianxiangmuStrs = new ArrayList<String>();
        for (Xunjianxiangmu xujianxiangmu : xunjianxiangmus) {
            xunjianxiangmuStrs.add(xujianxiangmu.mMingcheng);
        }
        mXunjianxiangmus.setAdapter(new ListViewAdapter(xunjianxiangmus));
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




    class ListViewAdapter extends BaseAdapter {
        ArrayList<Xunjianxiangmu> mXunjianxiangmus;

        public ListViewAdapter(ArrayList<Xunjianxiangmu> xunjianxiangmus){
            this.mXunjianxiangmus = xunjianxiangmus;
        }

        @Override
        public int getCount() {
            return mXunjianxiangmus.size();
        }

        @Override
        public Object getItem(int position) {
            return mXunjianxiangmus.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Xunjianxiangmu xunjianxiangmu = mXunjianxiangmus.get(position);
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.yf_ysdx_item, null);

            TextView ysdxName = (TextView) view.findViewById(R.id.ysdx_name);
            ImageView imageView = (ImageView) view.findViewById(R.id.icon);

            if(xunjianxiangmu.isFinish(mXunjiandan)){
                Log.d("abcdef", "yes");
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.yes_icon));
            }else{
                Log.d("abcdef", "no");
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.no_icon));
            }

            ysdxName.setText(xunjianxiangmu.mMingcheng);
            return view;
        }
    }

}