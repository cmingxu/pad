package com.example.pad.view;

import android.app.Activity;
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
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddressChooser extends BaseActivity {
    private Button back_btn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_chooser);
        back_btn = (Button)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new BackBtnClickListener());
    }

    protected class BackBtnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(AddressChooser.this, NewForm.class);
            startActivity(i);
        }
    }
}