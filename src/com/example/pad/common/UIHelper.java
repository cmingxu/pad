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
    Toast.makeText(context, content, Toast.LENGTH_LONG).show();
}

    public static void showShortToast(Context context, String content){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }



}
