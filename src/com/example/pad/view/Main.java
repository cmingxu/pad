package com.example.pad.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.NoticeService;
import com.example.pad.common.Util;

public class Main extends BaseActivity {
    private ImageButton logout_btn;
    private ImageButton maintain_btn;
    private ImageButton xunjian_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            new AlertDialog.Builder(Main.this).setMessage(R.string.logout_confirm).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                 redirect(Main.this, Login.class);
                  Main.this.finish();
                    Util.instance().setCurrentUser(null);
                    stopService(new Intent(Main.this, NoticeService.class)) ;
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();

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
