package com.example.pad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.example.pad.common.StringUtils;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppConfig{

    private final static String APP_CONFIG = "config";

    public final static String CONF_COOKIE = "cookie";
    public final static String CONF_ACCESSTOKEN = "accessToken";
    public final static String CONF_ACCESSSECRET = "accessSecret";
    public final static String CONF_EXPIRESIN = "expiresIn";
    public final static String CONF_LOAD_IMAGE = "perf_loadimage";
    public static final String CONF_SERVER = "SERVER_IP";
    public static final String CONF_PORT = "SERVER_PORT";

    private Context mContext;
    private static AppConfig appConfig;

    public static AppConfig getAppConfig(Context context)
    {
        if(appConfig == null){
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }

    /**
     * 获取Preference设置
     */
    public static SharedPreferences getSharedPreferences(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 是否加载显示文章图片
     */
    public static boolean isLoadImage(Context context)
    {
        return getSharedPreferences(context)
                .getBoolean(CONF_LOAD_IMAGE, true);
    }

    public String getCookie(){
        return get(CONF_COOKIE);
    }

    public void setAccessToken(String accessToken){
        set(CONF_ACCESSTOKEN, accessToken);
    }

    public String getAccessToken(){
        return get(CONF_ACCESSTOKEN);
    }

    public void setAccessSecret(String accessSecret){
        set(CONF_ACCESSSECRET, accessSecret);
    }

    public String getAccessSecret(){
        return get(CONF_ACCESSSECRET);
    }

    public void setExpiresIn(long expiresIn){
        set(CONF_EXPIRESIN, String.valueOf(expiresIn));
    }

    public long getExpiresIn(){
        return StringUtils.toLong(get(CONF_EXPIRESIN));
    }



    public String get(String key)
    {
        Properties props = get();
        return (props!=null)?props.getProperty(key):null;
    }

    public Properties get() {
        FileInputStream fis = null;
        Properties props = new Properties();
        try{
            //读取files目录下的config
            //fis = activity.openFileInput(APP_CONFIG);

            //读取app_config目录下的config
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            fis = new FileInputStream(dirConf.getPath() + File.separator + APP_CONFIG);

            props.load(fis);
        }catch(Exception e){
        }finally{
            try {
                fis.close();
            } catch (Exception e) {}
        }
        return props;
    }

    private void setProps(Properties p) {
        FileOutputStream fos = null;
        try{
            //把config建在files目录下
            //fos = activity.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);

            //把config建在(自定义)app_config的目录下
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            File conf = new File(dirConf, APP_CONFIG);
            fos = new FileOutputStream(conf);

            p.store(fos, null);
            fos.flush();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                fos.close();
            } catch (Exception e) {}
        }
    }

    public void set(Properties ps)
    {
        Properties props = get();
        props.putAll(ps);
        setProps(props);
    }

    public void set(String key,String value)
    {
        Properties props = get();
        props.setProperty(key, value);
        setProps(props);
    }

    public void remove(String...key)
    {
        Properties props = get();
        for(String k : key)
            props.remove(k);
        setProps(props);
    }
}
