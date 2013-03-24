package com.example.pad.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.example.pad.R;

/**
 * Created with IntelliJ IDEA.
 * User: xucming
 * Date: 3/24/13
 * Time: 7:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Login extends Activity {
    private Button logoutBtn;
    private Spinner spinner;
    private static final String[] m={"A型","B型","O型","AB型","其他"};
    private ArrayAdapter<String> adapter;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        logoutBtn = (Button)findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new LogoutOnClickListener());


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
}