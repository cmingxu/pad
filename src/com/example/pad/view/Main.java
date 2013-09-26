package com.example.pad.view;

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
                lastActivityWarnning();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
