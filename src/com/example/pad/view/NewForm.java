package com.example.pad.view;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.pad.BaseActivity;
import android.provider.MediaStore;
import com.example.pad.R;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/6/13
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class NewForm extends BaseActivity {
    private Button back_btn;
    private Button take_pic_btn;
    private EditText address_choose_text;
    private EditText zhuhu_name_text;
    private EditText zhuhu_phone_text;
    private static final int IMAGE_CAPTURE = 0;
    public static final int CHOOSE_ADDRESS = 1;

    private Uri imageUri;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_form);
        back_btn = (Button)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new BackBtnClickListener());

        take_pic_btn = (Button)findViewById(R.id.take_pic_btn);
        take_pic_btn.setOnClickListener(new TakePicClickListener());

        address_choose_text = (EditText)findViewById(R.id.address_choose_text);
        zhuhu_name_text     = (EditText)findViewById(R.id.zhuhuName);
        zhuhu_phone_text    = (EditText)findViewById(R.id.zhuHuPhone);

        address_choose_text.setOnClickListener(new AddressChoseClickListener());

    }

    protected class BackBtnClickListener implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(NewForm.this, Maintain.class);
            startActivity(i);
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
             startCamera();
        }
    }

    public void startCamera() {
        Log.d("ANDRO_CAMERA", "Starting camera on the phone...");
        String fileName = "testphoto.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION,
                "Image capture by camera");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, IMAGE_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        address_choose_text.setText(data.getStringExtra("selectedAddress"));

        if (requestCode == IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK){
                Log.d("ANDRO_CAMERA","Picture taken!!!");
//                imageView.setImageURI(imageUri);
            }

            if (resultCode == CHOOSE_ADDRESS){
                address_choose_text.setText(data.getStringExtra("selectedAddress"));
                Log.d("asdsdsd",data.getStringExtra("selectedAddress"));
                Log.d("selectedZhuhuName",data.getStringExtra("selectedZhuhuName"));
                Log.d("selectedZhuhuShouji",data.getStringExtra("selectedZhuhuShouji"));

                zhuhu_phone_text.setText(data.getStringExtra("selectedZhuhuShouji"));
                zhuhu_name_text.setText(data.getStringExtra("selectedZhuhuName"));
            }
        }
    }
}