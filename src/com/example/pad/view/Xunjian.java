package com.example.pad.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.example.pad.R;
import com.example.pad.common.Util;
import com.example.pad.models.Xunjiandan;
import com.example.pad.models.Xunjiandian;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/31/13
 * Time: 7:28 AM
 * To change this template use File | Settings | File Templates.
 */


public class Xunjian extends SherlockFragmentActivity implements ActionBar.TabListener{
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xunjian);

        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowTitleEnabled(true);
        bar.setTitle("Activity Title");

        ActionBar.Tab tab = getSupportActionBar().newTab();
        tab.setText("未完成");
        tab.setTag(1);
        tab.setTabListener(this);
        getSupportActionBar().addTab(tab);


        ActionBar.Tab tab2 = getSupportActionBar().newTab();
        tab2.setText("已完成");
        tab2.setTabListener(this);
        tab.setTag(2);
        getSupportActionBar().addTab(tab2);

        if (savedInstanceState != null) {
            bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }

        listView = (ListView)findViewById(R.id.xunjianxiangmus);
        ArrayList<String> xunjiandians = new ArrayList<String>();
        for(Xunjiandian xunjiandian : Xunjiandan.xunjiandians("")){
//            for(Xunjiandian xunjiandian : Xunjiandan.xunjiandians(Util.instance().current_user.login)){

                xunjiandians.add(xunjiandian.mBianhao);
        }

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, xunjiandians);
        listView.setAdapter(listViewAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
//                Intent intent = new Intent(this, DashboardActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getSupportActionBar()
                .getSelectedNavigationIndex());
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}