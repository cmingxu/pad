package com.example.pad.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.activeandroid.query.Select;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.common.HttpHelper;
import com.example.pad.common.PadJsonHttpResponseHandler;
import com.example.pad.common.StringUtils;
import com.example.pad.common.UIHelper;
import com.example.pad.models.CachedRequest;
import com.example.pad.models.Xunjiandan;
import com.example.pad.models.Xunjiandanmingxi;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/27/13
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class XunjianList extends BaseActivity
{
    ListView listView;
    XunjianDanAdapter adapter;
    ProgressDialog progressDialog;
    HttpHelper httpHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xunjian_list);
        listView = (ListView)findViewById(R.id.xunjian_list);

        httpHelper = new HttpHelper(appContext);
        progressDialog = new ProgressDialog(XunjianList.this);
        progressDialog.setTitle("巡检单上传中");
        progressDialog.setMessage("巡检单上传中");
        setupListView();

    }

    public void setupListView(){
        final List<Xunjiandan> xunjians =  new Select().from(Xunjiandan.class).orderBy("mJihuaQishiShijian").execute();
        Log.d("xunjian", xunjians.toString());

        adapter = new XunjianDanAdapter(xunjians);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("xunjiandan_id", xunjians.get(position).mRemoteID);
                i.setClass(XunjianList.this, XunjiandanView.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.xunjiandan_list_menu, menu);
        if(appConfig.getLoggedUser() != null){
            MenuItem menuItem = menu.findItem(R.id.action_logout) ;
            menuItem.setTitle("退出" + appConfig.getLoggedUser());
        }
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            super.onOptionsItemSelected(item);
        }
        if(item.getItemId() == R.id.action_download){

            redirect(XunjianList.this, XunjiandanListSelection.class);
            overridePendingTransition(R.animator.push_down_in, R.animator.push_down_out);
        }

        if(item.getItemId() == R.id.action_upload){
            if(Xunjiandan.findAllFinished().size() > 0) {
                progressDialog.show();
            }else{
                UIHelper.showLongToast(XunjianList.this, "尚无完成的巡检单， 请完成后上传");
                return true;
            }

            for (final Xunjiandan o : Xunjiandan.findAllFinished()) {
               if(o.finished()){

                   String req =  "xunjiandans?id=" + o.mRemoteID + "&minTime=" + o.minTime() + "&maxTime=" + o.maxTime();

                   CachedRequest cachedRequest = new CachedRequest();
                   cachedRequest.setHappenedAt(new Date());
                   cachedRequest.setRequest(req);
                   cachedRequest.setType("巡检单");

                   httpHelper.post(req, null,
                           new PadJsonHttpResponseHandler(XunjianList.this, progressDialog, cachedRequest) {

                       @Override
                       public void onSuccess(JSONObject jsonObject) {
                           super.onSuccess(jsonObject);
                           Xunjiandan.delete(Xunjiandan.class, o.getId());
                           setupListView();
                       }

                   });

                   for (final Xunjiandanmingxi xunjiandanmingxi : o.xunjiandanmingxis()) {


                       String request = new String("id=" + xunjiandanmingxi.mRemoteID + "&zhiid=" + xunjiandanmingxi.mZhiId + "&zhi=" + xunjiandanmingxi.mZhi +
                       "&xunjianshijian=" + StringUtils.toLongString(xunjiandanmingxi.mXunjianShijian) + "&biaoshi=" + xunjiandanmingxi.mBiaoshi +
                               "&shuoming=" + xunjiandanmingxi.mShuoming);

                       CachedRequest mingxicachedRequest = new CachedRequest();
                       mingxicachedRequest.setHappenedAt(new Date());
                       mingxicachedRequest.setRequest("xunjiandanmingxis?" + request);
                       mingxicachedRequest.setType("巡检明细");

                       httpHelper.post("xunjiandanmingxis?" + request, null,
                               new PadJsonHttpResponseHandler(XunjianList.this, progressDialog, mingxicachedRequest) {

                           @Override
                           public void onSuccess(JSONObject jsonObject) {
                               super.onSuccess(jsonObject);
                               Xunjiandanmingxi.delete(Xunjiandanmingxi.class, xunjiandanmingxi.getId());
                               progressDialog.hide();
                           }
                       });
                   }
               }
            }

        }
        return super.onMenuItemSelected( featureId, item);
    }


    public class XunjianDanAdapter extends BaseAdapter{
        List<com.example.pad.models.Xunjiandan> xunjiandans = null;

        public XunjianDanAdapter(List<com.example.pad.models.Xunjiandan> xunjiandans) {
            this.xunjiandans = xunjiandans;
        }

        @Override
        public int getCount() {
            return xunjiandans.size();
        }

        @Override
        public Object getItem(int position) {
            return xunjiandans.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Xunjiandan xunjiandan = (Xunjiandan)xunjiandans.get(position);
            LayoutInflater inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflator.inflate(R.layout.xunjiandan_item, null);

            TextView xunjiandan_bianhao = (TextView)view.findViewById(R.id.xunjiandan);
            TextView kaishi_shijian = (TextView)view.findViewById(R.id.kaishi_shijian);
            TextView jieshu_shijian = (TextView)view.findViewById(R.id.jieshu_shijian);
            kaishi_shijian.setText(xunjiandan.mJihuaQishiShijian.substring(0, 16));
            jieshu_shijian.setText(xunjiandan.mJihuaZhongzhiShijian.substring(0, 16));
            xunjiandan_bianhao.setText(xunjiandan.mDanjuBianHao);


            return view;
        }


    }

}
