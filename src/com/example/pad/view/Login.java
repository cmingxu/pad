package com.example.pad.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import com.activeandroid.query.Select;
import android.widget.*;
import com.example.pad.AppConfig;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.models.*;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xucming
 * Date: 3/24/13
 * Time: 7:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Login extends BaseActivity {
    private Button logoutBtn;
    private Button loginBtn;
    private Button downloadBtn;
    private Button settingButton;
    private TextView reloadUsers;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private HttpHelper httpHelper = HttpHelper.getInstance("login", "password");
    private Handler handler;
    public static final int UPDATE_USER_SELECTOR = 1;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        logoutBtn = (Button)findViewById(R.id.logout_btn);
        loginBtn  = (Button)findViewById(R.id.login_btn);
        downloadBtn = (Button)findViewById(R.id.download_btn);
        settingButton = (Button)findViewById(R.id.setting_btn);
        loginBtn.setOnClickListener(new LoginInOnClickListener());
        logoutBtn.setOnClickListener(new LogoutOnClickListener());
        settingButton.setOnClickListener(new SettingOnClickListener());


        downloadBtn.setOnClickListener(new DownloadBtnOnClickListener());
        progressDialog = new ProgressDialog(this);


        ArrayList<String> m= new ArrayList<String>();
        Log.d("sdsssds", new Select().from(User.class).toSql());

        for(User u : User.all(null)){
            m.add(u.login);
        }
        m.add("System");
        m.add("Administrator");
        Log.d("123", m.toString() );
        reloadUsers = (TextView)findViewById(R.id.reload_users);
        reloadUsers.setOnClickListener(new ReloadUserOnClickListener());
        spinner = (Spinner) findViewById(R.id.login_selector);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    protected class SettingOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            AppConfig.getAppConfig(Login.this).set("key", "value");
            Log.d("absdddd", AppConfig.getAppConfig(Login.this).get("key"));
            redirect(Login.this, Preference.class);

        }
    }

    protected class LogoutOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Log.d("aas", User.all(null).toString());
//            Log.d("aewfe", Loupan.first().mLoupanbianhao);
//            Log.d("aewfe", Loupan.first().mLoupanmingcheng);
//            Log.d("aewfe", Louge.first().mLougemingcheng);
//
//            Log.d("aewfe", Loupan.first().louges().size() + "");
//            Log.d("aewfe", Louge.first().loucengs().size() + "");
//            Log.d("aewfe", Louceng.first().danyuans().size() + "");
        }
    }

    protected class ReloadUserOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            progressDialog.setTitle(R.string.wait_please);
            progressDialog.setMessage(getString(R.string.users_reloading));
            progressDialog.show();
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case UPDATE_USER_SELECTOR:
                            spinner.invalidate();
                            break;

                        default:
                            break;

                    }
                }
            };


            httpHelper.with("users", null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONArray s) {
                    User.deleteAll();
                    try {
                        ArrayList<User> users = User.fromJsonArray(s);
                        for(User u : users) u.save();
                        Message m = new Message();
                        m.what = UPDATE_USER_SELECTOR;
                        handler.sendMessage(m);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable throwable, JSONObject jsonObject) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                }

            });


        }
    }

    private class LoginInOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            redirect(Login.this, Main.class);
        }
    }

    private class DownloadBtnOnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            progressDialog.show();
            progressDialog.setTitle("消息同步");
            progressDialog.setMessage("数据同步中...");
            User.deleteAll();
            Danyuan.deleteAll();
            Louceng.deleteAll();
            Loupan.deleteAll();
            Louge.deleteAll();

            httpHelper.with("users", null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONArray jsonArray) {
                    try {
                        ArrayList<User> users = User.fromJsonArray(jsonArray);
                        for(User u : users) u.save();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            httpHelper.with("danyuans", null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONArray jsonArray) {
                    try {
                        ArrayList<Danyuan> danyuans = Danyuan.fromJsonArray(jsonArray);
                        for(Danyuan d : danyuans) d.save();
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            httpHelper.with("loupans", null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONArray jsonArray) {
                    try {
                        ArrayList<Loupan> loupans = Loupan.fromJsonArray(jsonArray);
                        for(Loupan l : loupans) l.save();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            httpHelper.with("louges", null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONArray jsonArray) {
                    try {
                        ArrayList<Louge> louges = Louge.fromJsonArray(jsonArray);
                        for(Louge l : louges) l.save();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            httpHelper.with("loucengs", null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONArray jsonArray) {
                    try {
                        ArrayList<Louceng> loucengs = Louceng.fromJsonArray(jsonArray);
                        for(Louceng l : loucengs) l.save();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}