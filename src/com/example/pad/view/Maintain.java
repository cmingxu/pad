package com.example.pad.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.pad.BaseActivity;
import com.example.pad.R;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/6/13
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class Maintain extends BaseActivity {
    public static final String LOG_TAG = "Maintain_Activity";
    private Button back_btn;
    private Button new_form_btn;
    private Button not_uploaded_btn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.maintain);
        back_btn = (Button)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new BackBtnOnClickListener());
        new_form_btn = (Button)findViewById(R.id.new_form_btn);
        new_form_btn.setOnClickListener(new NewFormOnClickListener());
        not_uploaded_btn = (Button)findViewById(R.id.not_uploaded_btn);
        not_uploaded_btn.setOnClickListener(new NotUploadedOnClickListener());

    }

    protected class BackBtnOnClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(Maintain.this, Main.class);
            startActivity(i);
        }
    }

    protected class NewFormOnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(Maintain.this, NewForm.class);
            startActivity(i);
        }
    }

    protected class NotUploadedOnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(Maintain.this, NewForm.class);
            startActivity(i);
        }
    }
}