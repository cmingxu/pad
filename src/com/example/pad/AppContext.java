package com.example.pad;

import android.content.Intent;
import android.util.Log;
import com.example.pad.common.NoticeService;
import com.example.pad.models.User;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppContext extends com.activeandroid.app.Application {

    private User current_user;

    public User getCurrentUser() {
        return current_user;
    }

    public void logout(){
        this.current_user = null;
        AppConfig.getAppConfig(getApplicationContext()).setLoggedUser("");
    }

    public void login(User user){
        this.current_user = user;
        AppConfig.getAppConfig(getApplicationContext()).setLoggedUser(user.login);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //注册App异常崩溃处理器
    }

    @Override
    public void onTerminate() {
        stopService(new Intent(this, NoticeService.class)) ;
        Log.d("stop service", "stop service");
        super.onTerminate();

    }
}
