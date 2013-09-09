package com.example.pad;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.WindowManager;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/6/13
 * Time: 10:26 AM
 * To change this template use File | Settings | File Templates.
 */
//public class BaseActivity extends SherlockActivity {
    public class BaseActivity extends SherlockActivity {

        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                AppManager.getAppManager().addActivity(this);
                ActionBar bar = getSupportActionBar();
                bar.setIcon(R.drawable.icon_zhuye);
                bar.setTitle("PMP");
                bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.top));
        }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }


    protected void redirect(Context from, Class to){
        Intent i = new Intent();
        i.setClass(from, to);
        from.startActivity(i);

    }
    protected void redirectWithClearTop(Context from, Class to){
        Intent i = new Intent();
        i.setClass(from, to);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        from.startActivity(i);

    }


}