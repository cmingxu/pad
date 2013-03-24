package com.example.pad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import com.example.pad.view.Login;

import javax.security.auth.login.LoginException;

public class Main extends Activity {
    private ImageButton logout_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        logout_btn = (ImageButton)findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new LogoutClickListener());

    }

    protected class LogoutClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(Main.this, Login.class);
            startActivity(i);
        }
    }
}
