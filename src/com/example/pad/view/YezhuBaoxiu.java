package com.example.pad.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.Weixiudan;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 10/10/13
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class YezhuBaoxiu extends BaseActivity {
    private Spinner spinner;
    ArrayAdapter<String> adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yezhu_baoxiu);

        spinner = (Spinner)findViewById(R.id.categories);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Weixiudan.categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
