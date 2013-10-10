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
import com.example.pad.view.LandingPage;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/6/13
 * Time: 10:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends SherlockActivity {

    public AppConfig appConfig;
    public AppContext appContext;
    public AppManager appManager;


    public  BaseActivity(){
        super();
        appManager = AppManager.getAppManager();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        bar.setIcon(R.drawable.icon_zhuye);
        bar.setTitle(getResources().getString(R.string.app_name));
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.top));

        appContext = (AppContext)getApplication();
        appConfig  = AppConfig.getAppConfig(appContext);
        appManager.addActivity(this);
        Log.d("onCreate", this.getTitle().toString());

    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    protected void onDestroy() {
        Log.d("onDestroy", this.getTitle().toString());
        super.onDestroy();
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
        from.startActivity(i);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.common, menu);
        if(appConfig.getLoggedUser() != null){
            MenuItem menuItem = menu.findItem(R.id.action_logout) ;
            menuItem.setTitle("退出" + appConfig.getLoggedUser());
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.action_logout:
                lastActivityWarnning();

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void lastActivityWarnning(){
        new AlertDialog.Builder(this).setMessage(R.string.logout_confirm).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                appContext.logout();
                Intent intent = new Intent(BaseActivity.this, LandingPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Exit", true);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }


}