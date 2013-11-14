package com.example.pad.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import com.actionbarsherlock.view.MenuItem;
import com.example.pad.BaseActivity;
import com.example.pad.R;


public class Main extends BaseActivity {
    private ImageButton maintain_btn;
    private ImageButton xunjian_btn;
    private ImageButton chaobiao_btn;
    private ImageButton shoulou_btn;
    private ImageButton setting_btn;


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
        setting_btn = (ImageButton) findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(new SettingClickListener());
        shoulou_btn = (ImageButton) findViewById(R.id.shoulou_btn);
        shoulou_btn.setOnClickListener(new ShoulouClickListener());

    }


    protected class MaintainClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            redirect(Main.this, Maintain.class);
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
            redirect(Main.this, DanyuanbiaochaobiaoList.class);

        }
    }

    protected class SettingClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            redirect(Main.this, Setting.class);

        }
    }

    protected class ShoulouClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            redirect(Main.this, YFShoulouView.class);

        }
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            lastActivityWarnning();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                new AlertDialog.Builder(this).setMessage(R.string.logout_confirm).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appContext.logout();
                        Intent intent = new Intent(Main.this, LandingPage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Exit", true);
                        startActivity(intent);
                        Main.this.finish();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return  true;
    }


}
