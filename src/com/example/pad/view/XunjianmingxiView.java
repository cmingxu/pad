package com.example.pad.view;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.StringUtils;
import com.example.pad.models.*;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 7/20/13
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class XunjianmingxiView extends BaseActivity {
    private Xunjiandanmingxi mXunjiandanmingxi;
    private Xunjiandan mXunjiandan;
    private Xunjianxiangmu mXunjianxiangmu;
    private Xunjiandian mXunjiandian;
    private Xunjianzhi mXunjianzhi;
    private TextView mXunjiandianTv;
    private TextView mXunjianxiangmuTv;
    private TextView mXunjianbiaozhun;
    private TextView mXunjianshijian;
    private TextView mXunjianshuoming;
    private RadioGroup radioGroup;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.xunjianmingxi);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowTitleEnabled(true);
        bar.setIcon(getResources().getDrawable(R.drawable.icon_zhuye));
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.top));

        this.mXunjianxiangmu = Xunjianxiangmu.findByRemoteId(getIntent().getIntExtra("xunjianxiangmu_id", 0));
        this.mXunjiandan = Xunjiandan.findByRemoteId(getIntent().getIntExtra("xunjiandan_id", 0));
        this.mXunjiandanmingxi = Xunjiandanmingxi.findByRemoteXunjiandanidAndRemoteXunjianxiangmuid(this.mXunjiandan.mRemoteID, this.mXunjianxiangmu.mRemoteID);
        this.mXunjiandian = this.mXunjianxiangmu.xunjiandian();
        bar.setTitle(this.mXunjianxiangmu.mMingcheng);

        mXunjiandianTv = (TextView)findViewById(R.id.xunjiandian);
        mXunjianxiangmuTv = (TextView)findViewById(R.id.xunjianxiangmu);
        mXunjianbiaozhun = (TextView)findViewById(R.id.xunjianbiaozhun);
        mXunjianshijian = (TextView)findViewById(R.id.xunjianshijian);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);
        mXunjianshuoming  = (TextView)findViewById(R.id.xunjianshuoming);

        mXunjiandianTv.setText(this.mXunjiandian.mMingcheng);
        mXunjianxiangmuTv.setText(this.mXunjianxiangmu.mMingcheng);
        mXunjianbiaozhun.setText(this.mXunjianxiangmu.mBiaozhun);
        if (!StringUtils.isEmpty(mXunjiandanmingxi.mXunjianShijian)) {
            mXunjianshijian.setText(mXunjiandanmingxi.mXunjianShijian);
        }else
            mXunjianshijian.setText(StringUtils.currentTime());

        if (!StringUtils.isEmpty(mXunjiandanmingxi.mShuoming))
            mXunjianshuoming.setText(mXunjiandanmingxi.mShuoming);

        for (Xunjianzhi xunjianzhi : mXunjianxiangmu.xunjianzhis()) {
            addRadioButton(xunjianzhi);
        }

    }

    public boolean isDefault(Xunjianzhi xunjianzhi){
        boolean moren = false;
        if(mXunjiandanmingxi.mZhiId != null){
            if(mXunjiandanmingxi.mZhiId.equals(String.valueOf(xunjianzhi.mRemoteID))) {
                moren = true;
            }
        }else
        {
            moren = xunjianzhi.mShifouMoren;
        }
        return moren;
    }

    public void addRadioButton(Xunjianzhi xunjianzhi){

        int WC = RadioGroup.LayoutParams.WRAP_CONTENT;
        RadioGroup.LayoutParams rParams;

        RadioButton radioButton = new RadioButton(this);
        radioButton.setChecked(isDefault(xunjianzhi));
        radioButton.setText(xunjianzhi.mBiaoshi);
        radioButton.setId(xunjianzhi.mRemoteID);
        rParams = new RadioGroup.LayoutParams(WC, WC);
        radioGroup.addView(radioButton, rParams);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.xunjianmingxi, menu);
        return true;
    }


    public void save(){
        mXunjianzhi = Xunjianzhi.findByRemoteId(radioGroup.getCheckedRadioButtonId());
        if (mXunjianzhi != null) {
            mXunjiandanmingxi.mZhi = mXunjianzhi.mZhi;
            mXunjiandanmingxi.mZhiId = String.valueOf(mXunjianzhi.mRemoteID);
        }
        mXunjiandanmingxi.mShuoming = mXunjianshuoming.getText().toString();
        mXunjiandanmingxi.mXunjianShijian = StringUtils.currentTime();
        mXunjiandanmingxi.save();


        XunjianmingxiView.this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
            default:
                super.onOptionsItemSelected(item);
        }

        return true;
    }
}