package com.example.pad.view;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.activeandroid.query.Select;
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
public class WeixiudanList extends Activity
{
    ListView listView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weixiudan_list);
        listView = (ListView)findViewById(R.id.list_view);
        List<Weixiudan> weixiudans = new Select().from(Weixiudan.class).execute();
        listView.setAdapter(new ListViewAdapter(weixiudans));

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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)WeixiudanList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View item = inflater.inflate(R.layout.weixiudan_item,null);
            TextView title = (TextView) item.findViewById(R.id.title);
            title.setText(weixiudans.get(position).mYezhuName);

            TextView detail  = (TextView)item.findViewById(R.id.content);
            detail.setText(weixiudans.get(position).mBaoxiuNeirong);
            return item;
        }
    }


}
