package com.example.pad.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.YFFjlx;
import com.example.pad.models.YFYsdx;

import java.util.ArrayList;

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

    public void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yf_ysdx);
        ysdxs = (ListView) findViewById(R.id.ysdxs);
        fjlx_id = extras.getInt("fjlx_id");
        yfFjlx = YFFjlx.findByRemoteId(fjlx_id);
        ysdxes = (ArrayList<YFYsdx>)yfFjlx.ysdxs();

        ArrayList<String> ysdxStrs = new ArrayList<String>();
        for (YFYsdx ysdx : ysdxes) {
            ysdxStrs.add(ysdx.mDxmc + " " + ysdx.mDxbh);
        }
        ysdxs.setAdapter(new ArrayAdapter<String>(YFYsdxView.this, android.R.layout.simple_list_item_1, ysdxStrs));



    }



}