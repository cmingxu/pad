package com.example.pad.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.example.pad.R;
import com.example.pad.models.User;
import com.example.pad.view.Main;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 6/3/13
 * Time: 6:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class SyssendService extends Service {
    private Timer timer;
    HttpHelper httpHelper;

    public SyssendService() {
        httpHelper = new HttpHelper(SyssendService.this, Util.instance().current_user.login, Util.instance().current_user.password);
    }

    private TimerTask updateTask = new TimerTask() {
        @Override
        public void run() {
            Log.i("ddd", "Timer task doing work");

            httpHelper.with("syssends", null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONArray s) {
                    Log.d("123", s.toString());

                    NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification n = new Notification(R.drawable.sns_qq_icon, "Hello,there!", System.currentTimeMillis());
                    n.flags = Notification.FLAG_AUTO_CANCEL;
                    Intent i = new Intent(SyssendService.this, Main.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
//PendingIntent
                    PendingIntent contentIntent = PendingIntent.getActivity(
                            SyssendService.this,
                            R.string.app_name,
                            i,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    n.setLatestEventInfo(
                            SyssendService.this,
                            "Hello,there!",
                            "Hello,there,I'm john.",
                            contentIntent);
                    nm.notify(R.string.app_name, n);
                }

                @Override
                public void onFailure(Throwable throwable, JSONObject jsonObject) {
                    Log.d("failed", jsonObject.toString());

                }

            });

        }
    };
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("qhwuqwwe", "service stop");
        UIHelper.showLongToast(SyssendService.this, "Syssend Service on stop");
        timer.cancel();
        timer = null;

    }

    @Override
    public void onCreate() {
        Log.d("sfwefew", "service start");
        super.onCreate();
        UIHelper.showLongToast(SyssendService.this, "Syssend Service on create");
        timer = new Timer("TweetCollectorTimer");
        timer.schedule(updateTask, 1000L, 10 * 1000L);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
