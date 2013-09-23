package com.example.pad.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import com.actionbarsherlock.view.MenuItem;
import com.example.pad.AppContext;
import com.example.pad.AppManager;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.NoticeService;
import com.example.pad.models.DanyuanbiaoChaobiao;

public class Main extends BaseActivity {
    private ImageButton maintain_btn;
    private ImageButton xunjian_btn;
    private ImageButton chaobiao_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        maintain_btn = (ImageButton) findViewById(R.id.maintain_btn);
        maintain_btn.setOnClickListener(new MaintainClickListener());
        xunjian_btn = (ImageButton) findViewById(R.id.xunjian_btn);
        xunjian_btn.setOnClickListener(new XunjianClickListener());
        chaobiao_btn = (ImageButton) findViewById(R.id.chaobiao_btn);
        chaobiao_btn.setOnClickListener(new ChaobiaoClickListener());

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() != 1) {

            exit();
            return true;
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("home item", "item" + item.getItemId());
        switch (item.getItemId()) {
            case android.R.id.home:
                exit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void exit() {
        new AlertDialog.Builder(Main.this).setMessage(R.string.logout_confirm).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                stopService(new Intent(Main.this, NoticeService.class));
                ((AppContext) getApplication()).logout();
                AppManager appManager = AppManager.getAppManager();
                appManager.finishAllActivity();
                System.exit(0);
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

    }

    protected class MaintainClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(Main.this, Maintain.class);
            startActivity(i);
        }
    }

    protected class XunjianClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            redirect(Main.this, XunjianList.class);
        }
    }

    protected class ChaobiaoClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(Main.this, DanyuanbiaochaobiaoList.class);
            startActivity(i);
        }
    }

}
