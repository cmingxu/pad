package com.example.pad.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.activeandroid.query.Select;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.*;
import com.example.pad.models.AddressChooserResult;
import com.example.pad.models.CachedRequest;
import com.example.pad.models.Weixiudan;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

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
    public  static final int WEIXIUDAN_SAVE_NO_RESULT = 4;
    public  static final   int WEIXIUDAN_SAVE_EMPTY = 5;

    public static final int IMAGE1 = 1;
    public static final int IMAGE2 = 2;
    public static final int IMAGE3 = 3;


    private Button saveBtn;
    private EditText address_choose_text;
    private EditText zhuhu_name_text;
    private EditText zhuhu_phone_text;
    private EditText baoxiuneirong;
    private EditText time_text;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    public int currentImageView = IMAGE1;
    private Spinner categories;
    private static final int IMAGE_CAPTURE = 0;
    public static final int CHOOSE_ADDRESS = 1;

    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private Handler handler;
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
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView3 = (ImageView)findViewById(R.id.imageView3);
        imageView1.setOnClickListener(new TakePicClickListener(IMAGE1));
        imageView2.setOnClickListener(new TakePicClickListener(IMAGE2));
        imageView3.setOnClickListener(new TakePicClickListener(IMAGE3));
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
        weixiudan.mLoupanName = result.mLoupanName;
        weixiudan.mLoupanBianhao = result.mLoupanbianhao;
        weixiudan.userlogin = appContext.getCurrentUser().login;

    }


    public boolean allFieldsFilled(){
        boolean result = true;

        if (StringUtils.isEmpty(address_choose_text.getText().toString())) {
            result = false;
        }

        if (StringUtils.isEmpty(zhuhu_name_text.getText().toString())) {
            result = false;
        }

        if (StringUtils.isEmpty(zhuhu_phone_text.getText().toString())) {
            result = false ;
        }

        if (StringUtils.isEmpty(baoxiuneirong.getText().toString())) {

            result = false;
        }


        return result;


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
                        case WEIXIUDAN_SAVE_EMPTY:
                            progressDialog.dismiss();
                            UIHelper.showLongToast(NewForm.this, "业主姓名,电话， 维修内容均不能空");
                            break;
                        default:
                    }

                }
            } ;

            if(!allFieldsFilled()){
                Message message = new Message();
                message.what = WEIXIUDAN_SAVE_EMPTY;
                handler.sendMessage(message);
                return;
            }

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

                RequestParams params = new RequestParams();
                try {

                    if( weixiudan.image1 != null)
                        params.put("image1", new File("/sdcard/" + NewForm.this.getPackageName() + "/" + weixiudan.image1));
                    if (weixiudan.image2 != null)
                        params.put("image2", new File("/sdcard/" + NewForm.this.getPackageName() + "/" + weixiudan.image2));
                    if (weixiudan.image3 != null)
                        params.put("image3", new File("/sdcard/" + NewForm.this.getPackageName() + "/" + weixiudan.image3));
                } catch(FileNotFoundException e) {}

//                if(!Util.instance().isNetworkConnected(NewForm.this)){
//                    UIHelper.showLongToast(NewForm.this, getString(R.string.network_error));
//
//                    return;
//                }


                final CachedRequest cachedRequest = new CachedRequest();
                cachedRequest.setHappenedAt(new Date());
                cachedRequest.setRequest("weixiudans?" + weixiudan.toQuery());
                cachedRequest.setType("维修单");
                progressDialog.setTitle(R.string.wait_please);
                progressDialog.setMessage(getString(R.string.save_and_upload_inprogress));
                progressDialog.show();
                new HttpHelper(appContext).post("weixiudans?" + weixiudan.toQuery(), params,
                        new PadJsonHttpResponseHandler(getApplicationContext(), progressDialog, cachedRequest) {

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
                        cachedRequest.save();
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
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(i, CHOOSE_ADDRESS);
        }
    }

    protected class TakePicClickListener implements Button.OnClickListener{
        public int currentImageView;
        public TakePicClickListener(int currentImageView) {
            this.currentImageView = currentImageView;
        }

        @Override
        public void onClick(View view) {
            NewForm.this.currentImageView = this.currentImageView;
            final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            tempFile =  getTempFile(NewForm.this);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile( tempFile));
            startActivityForResult(intent, IMAGE_CAPTURE);
        }
    }

    private File getTempFile(Context context){
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
                switch (NewForm.this.currentImageView)    {

                    case IMAGE1:
                        weixiudan.image1 = tempFile.getName();
                        imageView1.setImageDrawable(Drawable.createFromPath(tempFile.getAbsolutePath()));
                        break;
                    case IMAGE2:
                        weixiudan.image2 = tempFile.getName();
                        imageView2.setImageDrawable(Drawable.createFromPath(tempFile.getAbsolutePath()));
                        break;
                    case IMAGE3:
                        weixiudan.image3 = tempFile.getName();
                        imageView3.setImageDrawable(Drawable.createFromPath(tempFile.getAbsolutePath()));
                        break;
                    default:
                        break;
                }


            }
        }

        if (requestCode == CHOOSE_ADDRESS){
            if (data != null) {
                result  = (AddressChooserResult)data.getSerializableExtra("result");
                Log.d("result", result.toString());
                address_choose_text.setText(result.getmLougeName() + "/" + result.getmLoucengName() + "/" + result.getmDanyuanName());
                zhuhu_phone_text.setText(result.getmYezhuDianhua());
                zhuhu_name_text.setText(result.getmYezhuName());
            }


        }
    }
}