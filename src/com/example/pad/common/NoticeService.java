package com.example.pad.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.example.pad.AppContext;
import com.example.pad.R;
import com.example.pad.models.Notice;
import com.example.pad.view.Maintain;
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
public class NoticeService extends Service {
    private Timer timer;
    HttpHelper httpHelper;
    AppContext appContext;

    public NoticeService() {
    }

    private TimerTask updateTask = new TimerTask() {
        @Override
        public void run() {
            Log.i("ddd", "Timer task doing work");

            httpHelper.with("notices", null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONArray s) {

                    try {
                        ArrayList<Notice> notices = Notice.fromJsonArray(s, (AppContext)getApplication());
                        for (Notice notice : notices){
                            if (Notice.findByRemoteId(notice.remoteId) == null) {
                                notice.save();

                                NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification n = new Notification(R.drawable.icon_gengxin, "新维修单!", System.currentTimeMillis());
                                long[] vibrate = new long[] { 1000, 1000, 1000, 1000, 1000 };
                                n.vibrate = vibrate;
                                n.flags = Notification.FLAG_AUTO_CANCEL;
                                Intent i = new Intent(NoticeService.this, Maintain.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                PendingIntent contentIntent = PendingIntent.getActivity(
                                        NoticeService.this,
                                        R.string.app_name,
                                        i,
                                        PendingIntent.FLAG_UPDATE_CURRENT);

                                n.setLatestEventInfo(
                                        NoticeService.this,
                                        "新维修单",
                                        notice.danjuNeirong,
                                        contentIntent);
                                nm.notify(R.string.app_name, n);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


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
        UIHelper.showLongToast(NoticeService.this, "Notice Service on stop");
        timer.cancel();
        timer = null;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        UIHelper.showLongToast(NoticeService.this, "Notice Service on create");
        appContext =(AppContext)getApplication();
        httpHelper = new HttpHelper(appContext);
        timer = new Timer("TweetCollectorTimer");
        timer.schedule(updateTask, 1000L, Config.NOTICE_FETCH_INTERVAL);

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
