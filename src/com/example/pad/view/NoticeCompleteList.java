package com.example.pad.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.Notice;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 6/20/13
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoticeCompleteList extends BaseActivity {
    private ListView notice_list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_list);
        final List<Notice> notices = Notice.allComplete(appContext.getCurrentUser().login);


        notice_list = (ListView)findViewById(R.id.list_view);
        notice_list.setAdapter(new NoticeListViewAdapter(notices));
        notice_list.setEmptyView((TextView)findViewById(R.id.empty));

        notice_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notice n = notices.get(position);
                Intent i = new Intent();
                i.setClass(NoticeCompleteList.this, NoticeCompleteForm.class);
                i.putExtra("notice_id", n.getId());
                startActivity(i);            }


        });

        notice_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Notice notice = notices.get(position);
                new AlertDialog.Builder(NoticeCompleteList.this).setMessage(R.string.delete_confirm).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notice.delete();
                        finish();
                        startActivity(getIntent());
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }

    class NoticeListViewAdapter extends BaseAdapter {

        private List<Notice> notices;

        NoticeListViewAdapter(List<Notice> notices) {
            this.notices = notices;
        }

        @Override
        public int getCount() {
            return notices.size();
        }

        @Override
        public Object getItem(int position) {
            return notices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Notice notice = (Notice)notices.get(position);

            LayoutInflater inflater = (LayoutInflater)NoticeCompleteList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.notice_item, null);
            TextView title = (TextView)view.findViewById(R.id.title);
            TextView content = (TextView)view.findViewById(R.id.content);

            title.setText(notice.danjuBiaoti);
            Log.d("weeefe", notice.danjuNeirong);
            content.setText(notice.danjuNeirong);

            return view;


        }
    }

}