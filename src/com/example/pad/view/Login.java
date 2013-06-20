package com.example.pad.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.pad.*;
import com.example.pad.common.*;
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
    private TextView appNameTv;
    private EditText passwordField;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private HttpHelper httpHelper;
    private Handler handler;
    public static final int UPDATE_USER_SELECTOR = 1;


    public void onCreate(Bundle savedInstanceState) {
        Log.d("SDXACCCCCCCCCCCCCCCCCCCCCCCC", " " + Util.isSDCardExist());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        logoutBtn = (Button)findViewById(R.id.logout_btn);
        loginBtn  = (Button)findViewById(R.id.login_btn);
        downloadBtn = (Button)findViewById(R.id.download_btn);
        passwordField = (EditText)findViewById(R.id.passwordEt);
        settingButton = (Button)findViewById(R.id.setting_btn);
        appNameTv = (TextView)findViewById(R.id.app_name);
        appNameTv.setText(R.string.app_name);
        loginBtn.setOnClickListener(new LoginInOnClickListener());
        logoutBtn.setOnClickListener(new LogoutOnClickListener());
        settingButton.setOnClickListener(new SettingOnClickListener());


        downloadBtn.setOnClickListener(new DownloadBtnOnClickListener());
        progressDialog = new ProgressDialog(this);


        ArrayList<String> m= new ArrayList<String>();
        for(User u : User.all(null)){
            m.add(u.login);
        }

        reloadUsers = (TextView)findViewById(R.id.reload_users);
        reloadUsers.setOnClickListener(new ReloadUserOnClickListener());
        spinner = (Spinner) findViewById(R.id.login_selector);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, m);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        httpHelper = new HttpHelper(getApplicationContext(), "login", "password");

    }
    protected class SettingOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            redirect(Login.this, Preference.class);
        }
    }

    protected class LogoutOnClickListener implements View.OnClickListener{

        @Override


        public void onClick(View view) {
            new AlertDialog.Builder(Login.this).setMessage(R.string.logout_confirm).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AppManager manager = AppManager.getAppManager();
                    manager.AppExit(Login.this);
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();

        }
    }

    protected class ReloadUserOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(!Util.instance().isNetworkConnected(Login.this)){
                Toast.makeText(Login.this, R.string.network_error, Toast.LENGTH_SHORT);
            }
            progressDialog.setTitle(R.string.wait_please);
            progressDialog.setMessage(getString(R.string.users_reloading));
            progressDialog.show();
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case UPDATE_USER_SELECTOR:

                            ArrayList<String> m= new ArrayList<String>();
                            for(User u : User.all(null)){
                                m.add(u.login);
                            }

                            adapter = new ArrayAdapter<String>(Login.this, android.R.layout.simple_spinner_item, m);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);
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
                    Toast.makeText(Login.this, R.string.reload_ok, Toast.LENGTH_SHORT).show();

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
            if (spinner.getSelectedItem()  == null) {
                UIHelper.showLongToast(Login.this, getString(R.string.select_a_login));
                return;
            }

            if (Config.passwordRequired() && passwordField.getText().toString() == null) {
                UIHelper.showLongToast(Login.this, getString(R.string.input_password));
                return;
            }
            User u = User.find_by_login_and_password(spinner.getSelectedItem().toString(),  passwordField.getText().toString());
            if (u != null){
                Util.instance().setCurrentUser(u);
                redirect(Login.this, Main.class);
                startService(new Intent(Login.this, NoticeService.class));

            }else{
                Toast.makeText(Login.this, R.string.input_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DownloadBtnOnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            if (!Util.instance().isNetworkConnected(Login.this)) {
                Toast.makeText(Login.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.show();
            progressDialog.setTitle("消息同步");
            progressDialog.setMessage("数据同步中...");
            User.deleteAll();
            Danyuan.deleteAll();
            Louceng.deleteAll();
            Loupan.deleteAll();
            Louge.deleteAll();
            Zhuhu.deleteAll();

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

            httpHelper.with("zhuhus", null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONArray jsonArray) {
                    try {
                        ArrayList<Zhuhu> zhuhus = Zhuhu.fromJsonArray(jsonArray);
                        Log.d("asssss", zhuhus.toString());
                        for(Zhuhu l : zhuhus) l.save();
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

                    progressDialog.dismiss();
                    Toast.makeText(Login.this, R.string.data_sync_ok, Toast.LENGTH_SHORT).show();


                }
            });
        }
    }
}