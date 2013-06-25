package com.example.pad.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.activeandroid.query.Select;
import com.example.pad.BaseActivity;
import android.provider.MediaStore;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.StringUtils;
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
import java.util.ArrayList;
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

    public  static  final  int WEIXIUDAN_SAVE_OK = 1;
    public  static final   int WEIXIUDAN_SAVE_FAILE = 2;
    public  static final   int WEIXIUDAN_SAVE_EXCEPTION = 3;
    public static final int WEIXIUDAN_SAVE_NO_RESULT = 4;




    private Button take_pic_btn;
    private Button saveBtn;
    private EditText address_choose_text;
    private EditText zhuhu_name_text;
    private EditText zhuhu_phone_text;
    private EditText baoxiuneirong;
    private EditText time_text;
    private Spinner categories;
    private static final int IMAGE_CAPTURE = 0;
    public static final int CHOOSE_ADDRESS = 1;

    private Uri imageUri;
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private Handler handler;
    private ArrayList<String> images;
    private File tempFile = null;
    Weixiudan weixiudan;
    AddressChooserResult result;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_form);


        Bundle extra = getIntent().getExtras();
        if (extra != null){
            long weixiudan_id = extra.getLong("weixiudan_id");
            weixiudan = (Weixiudan) new Select().from(Weixiudan.class).where("id=" + weixiudan_id).executeSingle();
        }else {
            weixiudan = new Weixiudan();
        }

        take_pic_btn = (Button)findViewById(R.id.take_pic_btn);
        take_pic_btn.setOnClickListener(new TakePicClickListener());

        saveBtn = (Button)findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new SaveOnClickListener());

        categories = (Spinner)findViewById(R.id.categories);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Weixiudan.categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);

        address_choose_text = (EditText)findViewById(R.id.address_choose_text);
        zhuhu_name_text     = (EditText)findViewById(R.id.zhuhuName);
        zhuhu_phone_text    = (EditText)findViewById(R.id.zhuHuPhone);
        baoxiuneirong       = (EditText)findViewById(R.id.baoxiuneirong);
        time_text           = (EditText)findViewById(R.id.time);
        time_text.setText( Util.instance().formatTime("yyyy/MM/dd", new Date()));
        if(weixiudan.getId() != null){
            address_choose_text.setText(weixiudan.address());
            zhuhu_name_text.setText(weixiudan.mYezhuName);
            zhuhu_phone_text.setText(weixiudan.mYezhuPhone);
            baoxiuneirong.setText(weixiudan.mBaoxiuNeirong);
            categories.setPrompt(weixiudan.mBaoxiuLeibie);
            time_text.setText(weixiudan.mBaoXiuRiqi);

            result = new AddressChooserResult();
            result.setmDanyuanbianhao(weixiudan.mDanyuanBianhao);
            result.setmDanyuanName(weixiudan.mDanyuanName);
            result.setmLoucengbianhao(weixiudan.mLoucengBianhao);
            result.setmLoucengName(weixiudan.mLoucengName);
            result.setmLougebianhao(weixiudan.mLougeBianhao);
            result.setmLougeName(weixiudan.mLougeName);
            result.setmYezhuDianhua(weixiudan.mYezhuPhone);
            result.setmYezhuName(weixiudan.mYezhuName);
        }

        address_choose_text.setOnClickListener(new AddressChoseClickListener());



        progressDialog = new ProgressDialog(this);

        images = new ArrayList<String>();


    }

    public void gatherWeixiudan(){
        if (weixiudan == null) {
            weixiudan = new Weixiudan();
        }
        weixiudan.mBaoxiuLeibie =categories.getSelectedItem().toString();
        weixiudan.mBaoxiuNeirong = baoxiuneirong.getText().toString();
        weixiudan.mBaoxiuren   = result.mYezhuName;
        weixiudan.mBaoXiuRiqi  = Util.instance().formatTime("yyyy/MM/dd", new Date());
        weixiudan.mDanyuanName = result.mDanyuanName;
        weixiudan.mDbSaved = 0;
        weixiudan.mLoucengName = result.mLoucengName;
        weixiudan.mLougeName = result.mLougeName;
        weixiudan.mRemoteSaved = 0;
        weixiudan.mYezhuName = result.mYezhuName;
        weixiudan.mYezhuPhone = result.mYezhuDianhua;
        weixiudan.mLougeBianhao = result.mLougebianhao;
        weixiudan.mLoucengBianhao = result.mLoucengName;
        weixiudan.mDanyuanBianhao = result.mDanyuanbianhao;

    }

    private class SaveOnClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {

            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case WEIXIUDAN_SAVE_OK:
                            progressDialog.dismiss();
                            UIHelper.showLongToast(NewForm.this, getString(R.string.weixiudan_saved));

                            redirectWithClearTop(NewForm.this, Maintain.class);
                            break;
                        case WEIXIUDAN_SAVE_FAILE:
                            progressDialog.dismiss();
                            UIHelper.showLongToast(NewForm.this, getString(R.string.weixiudan_saved_failed));
                            break;
                        case WEIXIUDAN_SAVE_EXCEPTION:
                            progressDialog.dismiss();
                            UIHelper.showLongToast(NewForm.this, getString(R.string.weixiudan_saved_exception));
                            break;
                        case WEIXIUDAN_SAVE_NO_RESULT:
                            progressDialog.dismiss();
                            UIHelper.showLongToast(NewForm.this, getString(R.string.weixiudan_saved_no_result));
                            break;
                        default:
                    }

                }
            } ;
            if(null != result){
                gatherWeixiudan();
                weixiudan.mDbSaved  = 1;
                try {
                    weixiudan.save();

                }  catch (Exception e){
                    Log.d("image exception", e.toString());
                    Message message = new Message();
                    message.what = WEIXIUDAN_SAVE_EXCEPTION;
                    handler.sendMessage(message);
                }

                Log.d("toJSon", weixiudan.toQuery());
                RequestParams params = new RequestParams();
                try {

                     params.put("image1", new File("/sdcard/" + NewForm.this.getPackageName() + "/" + weixiudan.image1));
                     params.put("image2", new File("/sdcard/" + NewForm.this.getPackageName() + "/" + weixiudan.image2));
                     params.put("image3", new File("/sdcard/" + NewForm.this.getPackageName() + "/" + weixiudan.image3));
                } catch(FileNotFoundException e) {}
                if(!Util.instance().isNetworkConnected(NewForm.this)){
                    UIHelper.showLongToast(NewForm.this, getString(R.string.network_error));

                    return;
                }

                progressDialog.setTitle(R.string.wait_please);
                progressDialog.setMessage(getString(R.string.save_and_upload_inprogress));
                progressDialog.show();
                new HttpHelper(NewForm.this, Util.instance().current_user.login, Util.instance().current_user.password).post("weixiudans?" + weixiudan.toQuery(), params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int i, JSONObject jsonObject) {
                        Log.d("onSuccess", "onSuccess");
                        weixiudan.mRemoteSaved = 1;
                        weixiudan.save();

                        super.onSuccess(i, jsonObject);
                        Message message = new Message();
                        message.what = WEIXIUDAN_SAVE_OK;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(Throwable throwable, JSONObject jsonObject) {
                        Log.d("onFailure", "throwable");
                        super.onFailure(throwable, jsonObject);
                        Message message = new Message();
                        message.what = WEIXIUDAN_SAVE_FAILE;
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        Log.d("onFInish", "finish");

                    }
                });
            }else{
                Message message = new Message();
                message.what = WEIXIUDAN_SAVE_NO_RESULT;
                handler.sendMessage(message);
            }

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
            final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            tempFile =  getTempFile(NewForm.this);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile( tempFile));
            startActivityForResult(intent, IMAGE_CAPTURE);
    }
    }

    private File getTempFile(Context context){
//        final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName() );
        final File path = new File( "/sdcard"   ,context.getPackageName() );

        if(!path.exists()){
            path.mkdir();
        }
        return new File(path, StringUtils.randomString() + ".jpg");
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_CAPTURE) {
                if (resultCode == RESULT_OK) {
                    Log.d("sddd", tempFile.getName());
                  images.add(tempFile.getName());

                }
        }

            if (requestCode == CHOOSE_ADDRESS){
                if (data != null) {
                    result  = (AddressChooserResult)data.getSerializableExtra("result");
                    address_choose_text.setText(result.getmLougeName() + "/" + result.getmLoucengName() + "/" + result.getmDanyuanName());
                    zhuhu_phone_text.setText(result.getmYezhuDianhua());
                    zhuhu_name_text.setText(result.getmYezhuName());
                }


        }
    }
}