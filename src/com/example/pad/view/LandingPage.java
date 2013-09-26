package com.example.pad.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.example.pad.AppConfig;
import com.example.pad.AppContext;
import com.example.pad.R;
import com.example.pad.common.StringUtils;
import com.example.pad.models.User;


/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 9/23/13
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class LandingPage extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loading);


        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String autoLoggedInUserLogin = AppConfig.getAppConfig(LandingPage.this).getLoggedUser();

                Log.d("landing", autoLoggedInUserLogin);
                boolean isAutoLoggedin = (!StringUtils.isEmpty(autoLoggedInUserLogin));
                User u = User.find_by_login(autoLoggedInUserLogin);

                if (isAutoLoggedin && u != null) {
                    AppContext appContext = (AppContext) getApplication();
                    appContext.login(u);
                    redirect(LandingPage.this, Main.class);

                } else {
                    redirect(LandingPage.this, Login.class);

                }
            }
        };

        handler.postDelayed(runnable, 3 * 1000);
    }

    protected void redirect(Context from, Class to){
        Intent i = new Intent();
        i.setClass(from, to);
        from.startActivity(i);
    }
}