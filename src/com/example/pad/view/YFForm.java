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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.PadJsonHttpResponseHandler;
import com.example.pad.common.StringUtils;
import com.example.pad.common.UIHelper;
import com.example.pad.models.*;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 11/17/13
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class YFForm extends BaseActivity {
    public static final int IMAGE_CAPTURE = 0;
    File tempFile;
    String yfImagesDir;
    private TextView textView;
    private Button savebBtn;
    private LinearLayout containerLayout;
    private ImageView newPic;
    private YFYsdx ysdx;
    private Danyuan danyuan;
    private YFFjlx fjlx;
    private YFYfRecord yfRecord;
    private ProgressDialog progressDialog;
    private HttpHelper httpHelper;

    public void onCreate(Bundle savedInstanceState) {
        yfImagesDir = "/sdcard/" + YFForm.this.getPackageName() + "/yf";
        Bundle bundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yf_form);

        Log.d("sql", "" + bundle.getInt("ysdx_id"));
        ysdx = YFYsdx.findById(bundle.getInt("ysdx_id"));
        danyuan = Danyuan.findByDanyuanbianhao(bundle.getString("danyuanbianhao"));
        fjlx = YFFjlx.findByRemoteId(bundle.getInt("fjlx_id"));

        getActionBar().setTitle(ysdx.mDxmc);
        progressDialog = new ProgressDialog(YFForm.this);
        progressDialog.setMessage("验房结果上传中， 请稍后");

        textView = (TextView) findViewById(R.id.desc);
        savebBtn = (Button) findViewById(R.id.save_yf_record);
        containerLayout = (LinearLayout) findViewById(R.id.image_container);

        newPic = (ImageView) findViewById(R.id.newPic);
        newPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                tempFile = getTempFile(YFForm.this);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(intent, IMAGE_CAPTURE);

            }

            private File getTempFile(Context context) {
                File path = new File("/sdcard", context.getPackageName() + "/yf");

                if (!path.exists()) {
                    path.mkdir();
                }
                return new File(path, "/temp/" + StringUtils.randomString() + ".jpg");
            }

        });

        File temp_path = new File("/sdcard", YFForm.this.getPackageName() + "/yf/temp");
        if (temp_path.exists() && temp_path.isDirectory()) {
            for (File imageFile : temp_path.listFiles()) {
                if (imageFile.getAbsolutePath().endsWith(".jpg")) {
                    addImageInContainer(imageFile.getAbsolutePath());
                }
            }
        }


        savebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(textView.getText().toString())) {
                    UIHelper.showLongToast(YFForm.this, "请说明问题明细");
                    return;
                }

                yfRecord = new YFYfRecord();
                yfRecord.mDesc = textView.getText().toString();
                yfRecord.mDanyuanbianhao = danyuan.mDanyuanbianhao;
                yfRecord.mDxID = ysdx.mRemoteId;
                yfRecord.mFjlxID = fjlx.mRemoteId;
                yfRecord.mImageDir = yfImagesDir + "/" + YFYfRecord.last_id();
                yfRecord.save();

                Log.d("sql", yfImagesDir + "/temp/");
                File file = new File(yfImagesDir + "/temp/");
                if(file.exists() && file.isDirectory() && file.list().length > 0){
                    file.renameTo(new File(yfRecord.mImageDir));
                }

                progressDialog.show();

                final CachedRequest cachedRequest = new CachedRequest();
                cachedRequest.setHappenedAt(new Date());
                cachedRequest.setType("yf_yz_yfd");
                cachedRequest.setRequest("yf_yz_yfd");
                cachedRequest.setImages(yfRecord.imagesInStr());
                cachedRequest.setRid(yfRecord.getId());

                RequestParams params = new RequestParams();
                params.put("mDesc", yfRecord.mDesc);
                params.put("mDxID", "" +yfRecord.mDxID);
                params.put("mFjlxID", "" + yfRecord.mFjlxID);
                params.put("mDanyuanbianhao", yfRecord.mDanyuanbianhao);
                File imagesDir = new File(yfRecord.mImageDir);
                if(imagesDir.exists() && imagesDir.isDirectory()){
                    int i = 0;
                    for (File image : imagesDir.listFiles()) {

                        try {
                            Log.d("sql", "image" + i);
                            params.put("image" + i, image);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        i = i + 1;
                    }
                }

                new HttpHelper(appContext).post("yf_yz_yfd", params, new PadJsonHttpResponseHandler(YFForm.this, progressDialog, cachedRequest) {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        UIHelper.showLongToast(YFForm.this, "验房单保存成功");
                        YFForm.this.finish();
                        progressDialog.hide();
                        super.onSuccess(jsonObject);
                    }

                    @Override
                    public void onFailure(Throwable throwable, JSONObject jsonObject) {
                        Log.d("onFailure", "throwable");
                        cachedRequest.save();
                        YFForm.this.finish();
                        super.onFailure(throwable, jsonObject);
                        progressDialog.hide();
                    }
                });
            }
        }

    );

}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                addImageInContainer(tempFile.getAbsolutePath());
            }
        }


    }

    public void addImageInContainer(final String imagePath) {
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (10 * scale + 0.5f);
        int dpAsPixels60 = (int) (58 * scale + 0.5f);

        ImageView imageView = new ImageView(YFForm.this);

        imageView.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imagePath), dpAsPixels60, dpAsPixels60, false));
        imageView.setPadding(0, 0, dpAsPixels, 0);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final View view = v;
                new AlertDialog.Builder(YFForm.this)
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


}