package com.example.pad.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.pad.models.Syssend;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/27/13
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SyssendCompleteList extends BaseActivity
{
    ListView listView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weixiudan_list);
        listView = (ListView)findViewById(R.id.list_view);
        final List<Syssend> syssends = new Select().from(Syssend.class).where("ifComplete=0").execute();
        listView.setAdapter(new SysendCompleteListViewAdapter(syssends));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("syssend_id", syssends.get(position).getId());
                i.setClass(SyssendCompleteList.this, SyssendForm.class);
                startActivity(i);
            }
        });
    }





    public class SysendCompleteListViewAdapter extends BaseAdapter{
        List<Syssend> syssends;
        public SysendCompleteListViewAdapter(List<Syssend> syssends) {
            this.syssends = syssends;
        }

        @Override
        public int getCount() {
            return syssends.size();
        }

        @Override
        public Object getItem(int position) {
            return syssends.get(position);
        }

        @Override
        public long getItemId(int position) {
            return syssends.get(position).getId();
        }

        @Override
        public View getView(int position,  View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)SyssendCompleteList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View item = inflater.inflate(R.layout.weixiudan_item, null);

            TextView title = (TextView) item.findViewById(R.id.title);
            title.setText(syssends.get(position).style);


            TextView detail  = (TextView)item.findViewById(R.id.content);
            detail.setText(syssends.get(position).content);
            return item;
        }
    }


}
