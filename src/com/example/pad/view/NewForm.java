package com.example.pad.view;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.example.pad.BaseActivity;
import android.provider.MediaStore;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.UIHelper;
import com.example.pad.common.Util;
import com.example.pad.models.AddressChooserResult;
import com.example.pad.models.Weixiudan;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/6/13
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class NewForm extends BaseActivity {

    private Button take_pic_btn;
    private Button saveBtn;
    private Button uploadBtn;
    private EditText address_choose_text;
    private EditText zhuhu_name_text;
    private EditText zhuhu_phone_text;
    private EditText baoxiuneirong;
    private Spinner categories;
    private static final int IMAGE_CAPTURE = 0;
    public static final int CHOOSE_ADDRESS = 1;

    private Uri imageUri;
    private ArrayAdapter<String> adapter;
    Weixiudan weixiudan;
    AddressChooserResult result;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_form);


        take_pic_btn = (Button)findViewById(R.id.take_pic_btn);
        take_pic_btn.setOnClickListener(new TakePicClickListener());

        saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new SaveOnClickListener());
        uploadBtn = (Button)findViewById(R.id.uploadBtn);


        categories = (Spinner)findViewById(R.id.categories);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Weixiudan.categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);

        address_choose_text = (EditText)findViewById(R.id.address_choose_text);
        zhuhu_name_text     = (EditText)findViewById(R.id.zhuhuName);
        zhuhu_phone_text    = (EditText)findViewById(R.id.zhuHuPhone);
        baoxiuneirong       = (EditText)findViewById(R.id.baoxiuneirong);

        address_choose_text.setOnClickListener(new AddressChoseClickListener());
        weixiudan = new Weixiudan();
    }

    public void gatherWeixiudan(){
        weixiudan = new Weixiudan();
        weixiudan.mBaoxiuLeibie =categories.getSelectedItem().toString();
        weixiudan.mBaoxiuNeirong = baoxiuneirong.getText().toString();
        weixiudan.mBaoxiuren   = Util.instance().getCurrentUser().login;
        weixiudan.mBaoXiuRiqi  = Util.instance().formatTime("yyyy/MM/dd", new Date());
        weixiudan.mDanyuanName = result.mDanyuanName;
        weixiudan.mDbSaved = false;
        weixiudan.mLoucengName = result.mLoucengName;
        weixiudan.mLougeName = result.mLougeName;
        weixiudan.mRemoteSaved = false;
        weixiudan.mYezhuName = result.mYezhuName;
        weixiudan.mYezhuPhone = result.mYezhuDianhua;
        weixiudan.mLougeBianhao = result.mLougebianhao;
        weixiudan.mLoucengBianhao = result.mLoucengName;
        weixiudan.mDanyuanBianhao = result.mDanyuanbianhao;

    }

    private class SaveOnClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            gatherWeixiudan();
            weixiudan.mDbSaved  = true;
            weixiudan.save();
            Log.d("toJSon", weixiudan.toQuery());
            HttpHelper.getInstance(Util.instance().current_user.login, Util.instance().current_user.password).post("weixiudans?" + weixiudan.toQuery(), null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int i, JSONArray jsonArray) {

                    weixiudan.mRemoteSaved = true;
                    weixiudan.save();
                    UIHelper.showLongToast(NewForm.this, getResources().getString(R.string.weixiudan_saved));
                    super.onSuccess(i, jsonArray);
                    redirect(NewForm.this, Maintain.class);
                }

                @Override
                public void onFailure(Throwable throwable, JSONObject jsonObject) {
                    UIHelper.showLongToast(NewForm.this, getResources().getString(R.string.weixiudan_saved_failed));
                    super.onFailure(throwable, jsonObject);
                }
            });

        }
    }

    protected class AddressChoseClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(NewForm.this, AddressChooser.class);
            startActivityForResult(i, CHOOSE_ADDRESS);
        }
    }

    protected class TakePicClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), IMAGE_CAPTURE);
    }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_CAPTURE) {
                if (resultCode == RESULT_OK) {
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    File myCaptureFile = new File("sdcard/123456.jpg");
                    try {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);

                        bos.flush();

                        bos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }

            if (requestCode == CHOOSE_ADDRESS){
                result  = (AddressChooserResult)data.getSerializableExtra("result");
                address_choose_text.setText(result.getmLougeName() + "/" + result.getmLoucengName() + "/" + result.getmDanyuanName());
                zhuhu_phone_text.setText(result.getmYezhuDianhua());
                zhuhu_name_text.setText(result.getmYezhuName());

        }
    }
}