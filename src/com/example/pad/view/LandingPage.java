package com.example.pad.view;

import android.os.Bundle;
import android.os.Handler;
import com.example.pad.AppConfig;
import com.example.pad.AppContext;
import com.example.pad.BaseActivity;
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
public class LandingPage extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String autoLoggedInUserLogin = AppConfig.getAppConfig(LandingPage.this).getLoggedUser();
                boolean isAutoLoggedin = (autoLoggedInUserLogin != null || StringUtils.isEmpty(autoLoggedInUserLogin));
                User u = User.find_by_login(autoLoggedInUserLogin);

                if (isAutoLoggedin && u != null) {
                    AppContext appContext = (AppContext) getApplication();
                    appContext.login(u);
                    ;
                    redirect(LandingPage.this, Main.class);

                } else {
                    redirect(LandingPage.this, Login.class);

                }
            }
        };

        handler.postDelayed(runnable, 3 * 1000);

    }
}