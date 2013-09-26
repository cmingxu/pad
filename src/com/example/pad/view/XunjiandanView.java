package com.example.pad.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.ActionBar;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.Xunjiandian;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/31/13
 * Time: 7:28 AM
 * To change this template use File | Settings | File Templates.
 */


public class XunjiandanView extends BaseActivity implements ActionBar.TabListener {
    com.example.pad.models.Xunjiandan xunjiandan;
    ArrayList<Xunjiandian> mFinishedXunjiandians;
    ArrayList<Xunjiandian> mNotFinishedxunjiandians;
    ArrayAdapter<String> mFinishedListViewAdapter;
    ArrayAdapter<String> mNotFinishedListViewAdapter;
    boolean tabOneSelected;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xunjian);

        xunjiandan = com.example.pad.models.Xunjiandan.findByRemoteId(getIntent().getIntExtra("xunjiandan_id", 0));

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowTitleEnabled(true);
        bar.setIcon(getResources().getDrawable(R.drawable.icon_zhuye));
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.top));
        bar.setTitle("巡检单： " + xunjiandan.mDanjuBianHao);

        ActionBar.Tab tab = getSupportActionBar().newTab();
        tab.setText("已完成");
        tab.setTag("tab1");
        tab.setTabListener(this);
        getSupportActionBar().addTab(tab);


        ActionBar.Tab tab2 = getSupportActionBar().newTab();
        tab2.setText("未完成");
        tab2.setTabListener(this);
        tab2.setTag("tab2");
        getSupportActionBar().addTab(tab2);

        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 1));
        }

        setupData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("wee", "onresume");
        setupData();
        mFinishedListViewAdapter.notifyDataSetChanged();
        mNotFinishedListViewAdapter.notifyDataSetChanged();
        listView.invalidate();
    }

    public void setupData() {
        mFinishedXunjiandians = (ArrayList<Xunjiandian>) xunjiandan.finishedXunjiandians();
        mNotFinishedxunjiandians = (ArrayList<Xunjiandian>) xunjiandan.notFinishedXunjiandians();

        listView = (ListView) findViewById(R.id.xunjianxiangmus);

        ArrayList<String> finishedXunjiandianStrs = new ArrayList<String>();
        ArrayList<String> notFinishedXunjiandianStrs = new ArrayList<String>();

        for (Xunjiandian xunjiandian : mFinishedXunjiandians) {
            finishedXunjiandianStrs.add(xunjiandian.mFangchanQuyu + " " + xunjiandian.mMingcheng);
        }

        for (Xunjiandian xunjiandian : mNotFinishedxunjiandians) {
            notFinishedXunjiandianStrs.add(xunjiandian.mFangchanQuyu + " " + xunjiandian.mMingcheng);
        }

        tabOneSelected = true;

        mFinishedListViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, finishedXunjiandianStrs);
        mNotFinishedListViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notFinishedXunjiandianStrs);
        listView.setAdapter(mFinishedListViewAdapter);
        listView.setOnItemClickListener(new OnItemClickListener());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getSupportActionBar()
                .getSelectedNavigationIndex());
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        listView = (ListView) findViewById(R.id.xunjianxiangmus);

        if (tab.getTag() != null && ((String) tab.getTag()).equals("tab1")) {
            tabOneSelected = true;
            listView.setAdapter(mFinishedListViewAdapter);
        } else {

            tabOneSelected = false;
            listView.setAdapter(mNotFinishedListViewAdapter);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public class OnItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (tabOneSelected) {
//                do nothing
                Log.d("qoo", mFinishedXunjiandians.get(position).mBianhao);
            } else {
//                redirct to new activity
                Intent intent = new Intent();
                intent.setClass(XunjiandanView.this, XunjiandianView.class);
                intent.putExtra("xunjiandian_id", mNotFinishedxunjiandians.get(position).mRemoteId);
                intent.putExtra("xunjiandan_id", xunjiandan.mRemoteID);
                startActivity(intent);
            }
        }
    }

}