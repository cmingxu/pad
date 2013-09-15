package com.example.pad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/6/13
 * Time: 10:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends SherlockActivity {

    AppConfig config;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.common, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("home item", "home item");
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_logout:
                new AlertDialog.Builder(this).setMessage(R.string.logout_confirm).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppManager manager = AppManager.getAppManager();
                        manager.AppExit(BaseActivity.this);
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}