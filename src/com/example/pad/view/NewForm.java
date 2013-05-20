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
import com.example.pad.models.AddressChooserResult;
import com.example.pad.models.Weixiudan;

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
    private EditText address_choose_text;
    private EditText zhuhu_name_text;
    private EditText zhuhu_phone_text;
    private Spinner categories;
    private static final int IMAGE_CAPTURE = 0;
    public static final int CHOOSE_ADDRESS = 1;

    private Uri imageUri;
    private ArrayAdapter<String> adapter;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_form);


        take_pic_btn = (Button)findViewById(R.id.take_pic_btn);
        take_pic_btn.setOnClickListener(new TakePicClickListener());

        categories = (Spinner)findViewById(R.id.categories);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Weixiudan.categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);

        address_choose_text = (EditText)findViewById(R.id.address_choose_text);
        zhuhu_name_text     = (EditText)findViewById(R.id.zhuhuName);
        zhuhu_phone_text    = (EditText)findViewById(R.id.zhuHuPhone);

        address_choose_text.setOnClickListener(new AddressChoseClickListener());



    }



    protected class AddressChoseClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {

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
//                    img.setImageBitmap(bm);//想图像显示在ImageView视图上，private ImageView img;
                    File myCaptureFile = new File("sdcard/123456.jpg");
                    try {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
      /* 采用压缩转档方法 */
                        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);

           /* 调用flush()方法，更新BufferStream */
                        bos.flush();

           /* 结束OutputStream */
                        bos.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
        }

            if (requestCode == CHOOSE_ADDRESS){
                AddressChooserResult result = (AddressChooserResult)data.getSerializableExtra("result");
                address_choose_text.setText(result.getmLougeName() + "/" + result.getmLoucengName() + "/" + result.getmDanyuanName());
                zhuhu_phone_text.setText(result.getmYezhuDianhua());
                zhuhu_name_text.setText(result.getmYezhuName());

        }
    }
}