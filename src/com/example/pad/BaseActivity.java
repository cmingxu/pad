package com.example.pad;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/6/13
 * Time: 10:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }
}