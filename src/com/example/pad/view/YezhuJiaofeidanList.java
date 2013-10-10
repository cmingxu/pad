package com.example.pad.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.pad.BaseActivity;
import com.example.pad.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 10/10/13
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class YezhuJiaofeidanList extends BaseActivity {
    private ListView listView;

    private ArrayList<JiaofeidanEntry> jiaofeidanEntries;

    class JiaofeidanEntry{
        public String item;
        public String amount;
        public String dueto;

        JiaofeidanEntry(String item, String amount, String dueto) {
            this.item = item;
            this.amount = amount;
            this.dueto = dueto;
        }

        String getItem() {
            return item;
        }

        void setItem(String item) {
            this.item = item;
        }

        String getAmount() {
            return amount;
        }

        void setAmount(String amount) {
            this.amount = amount;
        }

        String getDueto() {
            return dueto;
        }

        void setDueto(String dueto) {
            this.dueto = dueto;
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yezhujiaofeidan_list);

        jiaofeidanEntries = new ArrayList<JiaofeidanEntry>();
        jiaofeidanEntries.add(new JiaofeidanEntry("物业费", "3000", "10月1日"));
        jiaofeidanEntries.add(new JiaofeidanEntry("水费", "20", "10月1日"));
        jiaofeidanEntries.add(new JiaofeidanEntry("电费", "300", "10月1日"));
        jiaofeidanEntries.add(new JiaofeidanEntry("取暖费", "5000", "10月1日"));
        jiaofeidanEntries.add(new JiaofeidanEntry("煤气", "2000", "10月1日"));

        listView = (ListView)findViewById(R.id.jiaofeidan_list);
        listView.setAdapter(new JiaofeidanAdapter(jiaofeidanEntries));

    }

    class JiaofeidanAdapter extends BaseAdapter{
        ArrayList<JiaofeidanEntry> jiaofeidanEntries;

        JiaofeidanAdapter(ArrayList<JiaofeidanEntry> jiaofeidanEntries) {
            this.jiaofeidanEntries = jiaofeidanEntries;
        }

        @Override
        public int getCount() {
            return jiaofeidanEntries.size();
        }

        @Override
        public Object getItem(int position) {
            return jiaofeidanEntries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            JiaofeidanEntry entry = jiaofeidanEntries.get(position);
            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.yezhu_jiaofeidan_item, null);
            TextView item = (TextView) view.findViewById(R.id.item);
            TextView amount = (TextView) view.findViewById(R.id.amount);
            TextView due = (TextView) view.findViewById(R.id.due);

            item.setText(entry.getItem());
            amount.setText(entry.getAmount());
            due.setText(entry.getDueto());

            return view;
        }
    }
}