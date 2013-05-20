package com.example.pad.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.example.pad.view.Login;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/18/13
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class UIHelper {
    private static UIHelper instance;

    public static UIHelper instance(){
        if (instance == null) {
            instance = new UIHelper();
        }
        return instance;
    }


public static void showLongToast(Context context, String content){
    Toast.makeText(context, content, Toast.LENGTH_LONG);
}

    public static void showShortToast(Context context, String content){
        Toast.makeText(context, content, Toast.LENGTH_SHORT);
    }


    public static void showLongToast(Login context, int select_a_login) {
        Toast.makeText(context, context.getString(select_a_login), Toast.LENGTH_SHORT).show();

    }
}
