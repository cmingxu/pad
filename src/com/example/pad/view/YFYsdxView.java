package com.example.pad.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.YFFjlx;
import com.example.pad.models.YFYsdx;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 11/14/13
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class YFYsdxView extends BaseActivity {
    private ListView ysdxs;
    private ArrayList<YFYsdx> ysdxes;
    private YFFjlx yfFjlx;
    private int fjlx_id;
    private String danyuanbianhao;

    public void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yf_ysdx);
        ysdxs = (ListView) findViewById(R.id.ysdxs);
        fjlx_id = extras.getInt("fjlx_id");
        danyuanbianhao = extras.getString("danyuanbianhao");
        yfFjlx = YFFjlx.findByRemoteId(fjlx_id);
        getActionBar().setTitle(yfFjlx.mFjmc);
        ysdxes = (ArrayList<YFYsdx>)yfFjlx.ysdxs();

        Collections.sort(ysdxes);

        ysdxs.setAdapter(new YsdxAdapter(ysdxes));
        ysdxs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YFYsdx ysdx = ysdxes.get(position);
                Intent i = new Intent();
                i.setClass(YFYsdxView.this, YFForm.class);
                i.putExtra("fjlx_id", fjlx_id);
                i.putExtra("danyuanbianhao", danyuanbianhao);
                i.putExtra("ysdx_id", ysdx.mRemoteId);
                YFYsdxView.this.startActivity(i);
            }
        });


    }


    class YsdxAdapter extends BaseAdapter{
        ArrayList<YFYsdx> ysdxes;

        YsdxAdapter(ArrayList<YFYsdx> ysdxes) {
            this.ysdxes = ysdxes;
        }

        @Override
        public int getCount() {
            return ysdxes.size();
        }

        @Override
        public Object getItem(int position) {
            return ysdxes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            YFYsdx ysdx = ysdxes.get(position);
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.yf_ysdx_item, null);

            TextView ysdxName = (TextView) view.findViewById(R.id.ysdx_name);
            ImageView imageView = (ImageView) view.findViewById(R.id.icon);
            imageView.setImageDrawable(ysdx.indicationIcon(YFYsdxView.this, danyuanbianhao, fjlx_id));
            ysdxName.setText(ysdx.mDxmc + " " + ysdx.mDxbh);
            return view;
        }
    }

}