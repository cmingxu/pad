package com.example.pad.view;

import android.os.Bundle;
import android.view.View;
import com.example.pad.BaseActivity;
import com.example.pad.R;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 10/10/13
 * Time: 3:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class YezhuMain extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yezhu_main);
    }

    public void redirectToJiaofeidanList(View view){
        redirect(YezhuMain.this, YezhuJiaofeidanList.class);
    }

    public void redirectToChaxunList(View view){
        redirect(YezhuMain.this, YezhuChaxunList.class);
    }


    public void redirectToBaoxiuList(View view){
        redirect(YezhuMain.this, YezhuBaoxiu.class);
    }



}