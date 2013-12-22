package com.example.pad.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
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

    public static final int CHOOSE_ADDRESS = 1;
    private static final int IMAGE_CAPTURE = 0;
    Weixiudan weixiudan;
    AddressChooserResult result;
    String weixiudanImagesDir;
    private Button saveBtn;
    private EditText address_choose_text;
    private EditText zhuhu_name_text;
    private EditText zhuhu_phone_text;
    private EditText baoxiuneirong;
    private EditText time_text;
    private ImageView camera;
    private Spinner categories;
    private LinearLayout containerLayout;
    private ArrayAdapter<String> adapter;
    private ProgressDialog progressDialog;
    private File tempFile = null;

    public void onCreate(Bundle savedInstanceState) {

        weixiudanImagesDir = Weixiudan.IMAGE_DIR;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_form);

        Bundle extra = getIntent().getExtras();
        weixiudan = new Weixiudan();

        saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new SaveOnClickListener());

        categories = (Spinner) findViewById(R.id.categories);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Weixiudan.categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(adapter);

        address_choose_text = (EditText) findViewById(R.id.address_choose_text);
        zhuhu_name_text = (EditText) findViewById(R.id.zhuhuName);
        zhuhu_phone_text = (EditText) findViewById(R.id.zhuHuPhone);
        baoxiuneirong = (EditText) findViewById(R.id.baoxiuneirong);
        time_text = (EditText) findViewById(R.id.time);
        containerLayout = (LinearLayout) findViewById(R.id.image_container);
        camera = (ImageView) findViewById(R.id.camera);
        camera.setOnClickListener(new TakePicClickListener());
        time_text.setText(Util.instance().formatTime("yyyy/MM/dd", new Date()));
        if (weixiudan.getId() != null) {
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


        File temp_path = new File(weixiudanImagesDir + "/temp");
        if (temp_path.exists() && temp_path.isDirectory()) {
            for (File imageFile : temp_path.listFiles()) {
                if (imageFile.getAbsolutePath().endsWith(".jpg")) {
                    addImageInContainer(imageFile.getAbsolutePath());
                }
            }
        }

    }

    public void gatherWeixiudan() {
        if (weixiudan == null) {
            weixiudan = new Weixiudan();
        }
        weixiudan.mBaoxiuLeibie = categories.getSelectedItem().toString();
        weixiudan.mBaoxiuNeirong = baoxiuneirong.getText().toString();
        weixiudan.mBaoxiuren = result.mYezhuName;
        weixiudan.mBaoXiuRiqi = Util.instance().formatTime("yyyy/MM/dd", new Date());
        weixiudan.mDanyuanName = result.mDanyuanName;
        weixiudan.mLoucengName = result.mLoucengName;
        weixiudan.mLougeName = result.mLougeName;
        weixiudan.mYezhuName = result.mYezhuName;
        weixiudan.mYezhuPhone = result.mYezhuDianhua;
        weixiudan.mLougeBianhao = result.mLougebianhao;
        weixiudan.mLoucengBianhao = result.mLoucengName;
        weixiudan.mDanyuanBianhao = result.mDanyuanbianhao;
        weixiudan.mLoupanName = result.mLoupanName;
        weixiudan.mLoupanBianhao = result.mLoupanbianhao;
    }

    public boolean allFieldsFilled() {
        boolean result = true;

        if (StringUtils.isEmpty(address_choose_text.getText().toString())) {
            result = false;
        }

        if (StringUtils.isEmpty(zhuhu_name_text.getText().toString())) {
            result = false;
        }

        if (StringUtils.isEmpty(zhuhu_phone_text.getText().toString())) {
            result = false;
        }

        if (StringUtils.isEmpty(baoxiuneirong.getText().toString())) {

            result = false;
        }
        return result;

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                addImageInContainer(tempFile.getAbsolutePath());
            }
        }

        if (requestCode == CHOOSE_ADDRESS) {
            if (data != null) {
                result = (AddressChooserResult) data.getSerializableExtra("result");
                Log.d("result", result.toString());
                address_choose_text.setText(result.getmLougeName() + "/" + result.getmLoucengName() + "/" + result.getmDanyuanName());
                zhuhu_phone_text.setText(result.getmYezhuDianhua());
                zhuhu_name_text.setText(result.getmYezhuName());
            }


        }
    }

    public void addImageInContainer(final String imagePath) {
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (10 * scale + 0.5f);
        int dpAsPixels60 = (int) (58 * scale + 0.5f);

        ImageView imageView = new ImageView(NewForm.this);

        imageView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imagePath), dpAsPixels60, dpAsPixels60, false));
        imageView.setPadding(0, 0, dpAsPixels, 0);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final View view = v;
                new AlertDialog.Builder(NewForm.this)
                        .setTitle("删除图片")
                        .setMessage("选择OK将删除此照片?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                File file = new File(imagePath);
                                file.delete();
                                containerLayout.removeView(view);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();

                return true;
            }

        });


        containerLayout.addView(imageView, 0);


    }

    private class SaveOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!allFieldsFilled()) {
                UIHelper.showLongToast(NewForm.this, R.string.value_should_not_empty);
                return;
            }

            if (null != result) {
                gatherWeixiudan();
                weixiudan.mImageDir = weixiudanImagesDir + "/" + Weixiudan.last_id();
                weixiudan.save();

                File file = new File(weixiudanImagesDir + "/temp/");
                if (file.exists() && file.isDirectory() && file.list().length > 0) {
                    file.renameTo(new File(weixiudan.mImageDir));
                }

                RequestParams params = new RequestParams();

                final CachedRequest cachedRequest = new CachedRequest();
                cachedRequest.happenedAt = new Date();
                cachedRequest.httpMethod = "post";
                cachedRequest.request_path = "weixiudans";
                cachedRequest.resource_type = "维修单";
                cachedRequest.resource_id = weixiudan.getId();
                cachedRequest.images = weixiudan.mImageDir;

                File imagesDir = new File(weixiudan.mImageDir);
                if (imagesDir.exists() && imagesDir.isDirectory()) {
                    int i = 0;
                    for (File image : imagesDir.listFiles()) {
                        Log.d("image", "images");
                        try {
                            params.put("image" + i, image);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        i = i + 1;
                    }
                }

                progressDialog.setTitle(R.string.wait_please);
                progressDialog.setMessage(getString(R.string.save_and_upload_inprogress));
                progressDialog.show();
                new HttpHelper(appContext).post("weixiudans?" + weixiudan.toQuery(), params,
                        new PadJsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int i, JSONObject jsonObject) {
                                progressDialog.hide();
                                NewForm.this.finish();
                                UIHelper.showLongToast(NewForm.this, R.string.weixiudan_saved);
                            }

                            @Override
                            public void failure(String message) {
                                NewForm.this.finish();
                                if (progressDialog != null) {
                                    progressDialog.hide();
                                }
                                UIHelper.showLongToast(NewForm.this, message);
                                if (cachedRequest != null) {
                                    Log.d("cachedrequest", cachedRequest.request_path);
                                    UIHelper.showLongToast(NewForm.this, R.string.weixiudan_saved_failed_will_retry_for_you);
                                    cachedRequest.save();
                                }
                                super.failure(message);
                            }


                        });
            } else {
                UIHelper.showLongToast(NewForm.this, R.string.weixiudan_saved_no_result);
            }

        }
    }

    protected class AddressChoseClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(NewForm.this, DanyuanAddressChooser.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(i, CHOOSE_ADDRESS);
        }
    }

    protected class TakePicClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            tempFile = getTempFile(NewForm.this);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            startActivityForResult(intent, IMAGE_CAPTURE);
        }

        private File getTempFile(Context context) {
            File path = new File(weixiudanImagesDir + "/temp/");
            if (!path.exists()) {
                path.mkdirs();
            }
            return new File(path,  StringUtils.randomString() + ".jpg");
        }
    }
}