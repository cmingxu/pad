package com.example.pad.view;

import com.actionbarsherlock.app.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.activeandroid.query.Select;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Menu;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xunjian_list);
        listView = (ListView)findViewById(R.id.xunjian_list);
        final List<com.example.pad.models.Xunjiandan> xunjians =  new Select().from(com.example.pad.models.Xunjiandan.class).execute();
        listView.setAdapter(new XunjianDanAdapter(xunjians));
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("xunjiandan_id", xunjians.get(position).mRemoteID);
                i.setClass(XunjianList.this, XunjiandanView.class);
                startActivity(i);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        getSupportMenuInflater().inflate(R.menu.xunjiandan_list_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if(item.getItemId() == R.id.action_download){

            Intent i = new Intent();
            i.setClass(XunjianList.this, XunjiandanListSelection.class);
            startActivity(i);
            overridePendingTransition(R.animator.push_down_in, R.animator.push_down_out);

        }
        return true;
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
            com.example.pad.models.Xunjiandan xunjiandan = (com.example.pad.models.Xunjiandan)xunjiandans.get(position);
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
