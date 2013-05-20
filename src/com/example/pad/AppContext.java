package com.example.pad;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.CacheManager;
import android.widget.Toast;
import com.example.pad.common.StringUtils;
import com.example.pad.models.User;

import java.io.*;
import java.util.Hashtable;
import java.util.Properties;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppContext extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //注册App异常崩溃处理器
    }


}
