package com.example.pad.view;

import android.app.Activity;
import android.app.DownloadManager;
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
import com.activeandroid.query.Select;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.Weixiudan;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/27/13
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class WeixiudanList extends BaseActivity
{
    ListView listView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weixiudan_list);
        listView = (ListView)findViewById(R.id.list_view);
        final List<Weixiudan> weixiudans = new Select().from(Weixiudan.class).where("mRemoteSaved = 0").execute();
        listView.setAdapter(new ListViewAdapter(weixiudans));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("weixiudan_id", weixiudans.get(position).getId());
                i.setClass(WeixiudanList.this, NewForm.class);
                startActivity(i);
            }
        });
    }





    public class ListViewAdapter extends BaseAdapter{
        List<Weixiudan> weixiudans;
        public ListViewAdapter(List<Weixiudan> weixiudans) {
            this.weixiudans = weixiudans;
        }

        @Override
        public int getCount() {
            return weixiudans.size();
        }

        @Override
        public Object getItem(int position) {
            return weixiudans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return weixiudans.get(position).getId();
        }

        @Override
        public View getView(int position,  View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)WeixiudanList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View item = inflater.inflate(R.layout.weixiudan_item, null);

            TextView title = (TextView) item.findViewById(R.id.title);
            title.setText(weixiudans.get(position).mYezhuName + "(" + weixiudans.get(position).address() + ")");


            TextView detail  = (TextView)item.findViewById(R.id.content);
            detail.setText(weixiudans.get(position).mBaoxiuNeirong);
            return item;
        }
    }


}
