package com.example.pad.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.pad.BaseActivity;
import com.example.pad.R;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 10/18/13
 * Time: 8:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class YezhuWuyeChooser extends BaseActivity {
    private Button yezhu_btn;
    private Button wuye_btn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yezhu_wuye_chooser);

        yezhu_btn = (Button)findViewById(R.id.iamyezhu);
        wuye_btn  = (Button)findViewById(R.id.iamwuye);


        yezhu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect(YezhuWuyeChooser.this, YezhuMain.class);
            }
        });

        wuye_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                redirect(YezhuWuyeChooser.this, Login.class);
            }
        });
    }


}