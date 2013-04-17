package com.example.pad.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private TextView reloadUsers;
    private Spinner spinner;
    private static final String[] m={"user1", "user2", "user3"};
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private HttpHelper httpHelper = HttpHelper.getInstance("login", "password");



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        logoutBtn = (Button)findViewById(R.id.logout_btn);
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

            httpHelper.withUsers(null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(JSONArray s) {
                    try {
                        ArrayList<User> users = User.usersFromJsonArray(s);
                    } catch (JSONException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable throwable, JSONObject jsonObject) {
                    Toast.makeText(Login.this, R.string.network_error, Toast.LENGTH_SHORT).show();
                }

            });


        }
    }
}