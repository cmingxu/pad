package com.example.pad.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import com.example.pad.BaseActivity;
import com.example.pad.R;

public class Main extends BaseActivity {
    private ImageButton logout_btn;
    private ImageButton maintain_btn;
    private ImageButton xunjian_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        logout_btn = (ImageButton)findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new LogoutClickListener());
        maintain_btn = (ImageButton)findViewById(R.id.maintain_btn);
        maintain_btn.setOnClickListener(new MaintainClickListener());
        xunjian_btn = (ImageButton)findViewById(R.id.xunjian_btn);
        xunjian_btn.setOnClickListener(new XunjianClickListener());



    }

    protected class LogoutClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(Main.this, Login.class);
            startActivity(i);
        }
    }

    protected class MaintainClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(Main.this, Maintain.class);
            startActivity(i);
        }
    }

    protected class XunjianClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            redirect(Main.this, XunjianList.class);
        }
    }
}
