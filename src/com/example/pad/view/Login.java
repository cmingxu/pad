package com.example.pad.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.example.pad.AppManager;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.models.User;
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
    private TextView reloadUsers;
    private Spinner spinner;
    private static String[] m={"user1", "user2", "user3"};
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
        loginBtn.setOnClickListener(new LoginInOnClickListener());
        logoutBtn.setOnClickListener(new LogoutOnClickListener());
        progressDialog = new ProgressDialog(this);



        reloadUsers = (TextView)findViewById(R.id.reload_users);
        reloadUsers.setOnClickListener(new ReloadUserOnClickListener());
        spinner = (Spinner) findViewById(R.id.login_selector);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        //添加事件Spinner事件监听
//        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
//        spinner.setVisibility(View.VISIBLE);
    }

    protected class LogoutOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Login.this.finish();
        }
    }

    protected class ReloadUserOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            progressDialog.setTitle(R.string.wait_please);
            progressDialog.show();
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case UPDATE_USER_SELECTOR:
                            adapter.notifyDataSetChanged();
                            break;
                        default:
                            break;

                    }
                }
            };


            httpHelper.withUsers(null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONArray s) {
//                    User.deleteAll();
                    try {
                        ArrayList<User> users = User.usersFromJsonArray(s);
                        for(User u : users) u.save();
                        m = new String[]{"abd", "def"};
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
}