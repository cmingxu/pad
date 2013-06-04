package com.example.pad.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.Syssend;
import com.example.pad.models.Weixiudan;

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
    private Button iwant_upload_btn;
    private Button iwant_accept_btn;
    private Button iwant_complete_btn;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.maintain);
        back_btn = (Button)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new BackBtnOnClickListener());
        new_form_btn = (Button)findViewById(R.id.new_form_btn);
        new_form_btn.setOnClickListener(new NewFormOnClickListener());
        iwant_upload_btn = (Button)findViewById(R.id.iwant_upload_btn);
        iwant_upload_btn.setText(getString(R.string.iwant_upload) + "(" + Weixiudan.not_uploaded_count() + ")");
        iwant_upload_btn.setOnClickListener(new IWantUploadOnClickListener());
        iwant_accept_btn = (Button)findViewById(R.id.iwant_accept_btn);
        iwant_accept_btn.setText(getString(R.string.iwant_accept) + "(" + Syssend.accept_count() + ")");
        iwant_accept_btn.setOnClickListener(new IWantAcceptOnClickListener());

        iwant_complete_btn = (Button)findViewById(R.id.iwant_complete);
        iwant_complete_btn.setText(getString(R.string.iwant_complete) + "(" + Syssend.complete_count() + ")");
        iwant_complete_btn.setOnClickListener(new IWantCompleteOnClickListener());



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
            Maintain.this.finish();;
        }
    }

    protected class IWantUploadOnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(Maintain.this, WeixiudanList.class);
            startActivity(i);
            Maintain.this.finish();;
        }
    }

    protected class IWantAcceptOnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(Maintain.this, SyssendAcceptList.class);
            startActivity(i);
            Maintain.this.finish();;
        }
    }

    protected class IWantCompleteOnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(Maintain.this, SyssendCompleteList.class);
            startActivity(i);
            Maintain.this.finish();;
        }
    }
}