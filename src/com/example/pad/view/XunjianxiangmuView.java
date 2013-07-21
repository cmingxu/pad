package com.example.pad.view;

import android.os.Bundle;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.Xunjiandian;
import com.example.pad.models.Xunjianxiangmu;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 7/20/13
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class XunjianxiangmuView extends BaseActivity {
    private Xunjianxiangmu mXunjianxiangmu;
    private Xunjiandian mXunjiandian;
    private TextView mXunjiandianTv;
    private TextView mXunjianxiangmuTv;
    private TextView mXunjianbiaozhun;
    private TextView mXunjianshijian;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.xunjianxiangmu);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowTitleEnabled(true);
        bar.setIcon(getResources().getDrawable(R.drawable.icon_zhuye));
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.top));

        this.mXunjianxiangmu = Xunjianxiangmu.findByRemoteId(getIntent().getIntExtra("xunjianxiangmu_id", 0));
        this.mXunjiandian = this.mXunjianxiangmu.xunjiandian();
        bar.setTitle(this.mXunjianxiangmu.mMingcheng);

        mXunjiandianTv = (TextView)findViewById(R.id.xunjiandian);
        mXunjianxiangmuTv = (TextView)findViewById(R.id.xunjianxiangmu);
        mXunjianbiaozhun = (TextView)findViewById(R.id.xunjianbiaozhun);
        mXunjianshijian = (TextView)findViewById(R.id.xunjianshijian);

        mXunjiandianTv.setText(this.mXunjiandian.mMingcheng);
        mXunjianxiangmuTv.setText(this.mXunjianxiangmu.mMingcheng);
        mXunjianbiaozhun.setText(this.mXunjianxiangmu.mBiaozhun);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                XunjianxiangmuView.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}