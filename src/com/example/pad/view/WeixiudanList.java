package com.example.pad.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.activeandroid.query.Select;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.Notice;
import com.example.pad.models.Weixiudan;

import java.util.ArrayList;
import java.util.List;

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
    enum ViewType{
        WEIXIUDAN_HEADER,
        WEIXIUDAN_VIEW,
        NOT_ACCEPT_NOTICE_HEADER,
        NOT_ACCEPT_NOTICE_VIEW,
        NOT_COMPLETE_NOTICE_HEADER,
        NOT_COMPLETE_NOTICE_VIEW

    }   ;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weixiudan_list);
        listView = (ListView)findViewById(R.id.list_view);
        final List<Weixiudan> weixiudans = new Select().from(Weixiudan.class).where("mRemoteSaved = 0").execute();
        final List<Notice> notUploadedAcceptList = new Select().from(Notice.class).where("acceptUploaded=0").execute();
        final List<Notice> notUploadCompleteList = new Select().from(Notice.class).where("completeUploaded=0").execute();

        listView.setAdapter(new ListViewAdapter(weixiudans, notUploadedAcceptList, notUploadCompleteList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


    public class ListViewAdapter extends BaseAdapter{
        List<Weixiudan> weixiudans;
        List<Notice> notAcceptNoticeList;
        List<Notice> notCompleteNoticetList;
        ArrayList<ViewType> viewTypes = new ArrayList<ViewType>();

        public ListViewAdapter(List<Weixiudan> weixiudans, List<Notice> notAcceptNoticeList, List<Notice> notCompleteNoticetList) {

            this.weixiudans = weixiudans;
            this.notAcceptNoticeList = notAcceptNoticeList;
            this.notCompleteNoticetList = notCompleteNoticetList;

            viewTypes.add(ViewType.WEIXIUDAN_HEADER);
            for (int i = 0; i < weixiudans.size(); i++){
               viewTypes.add(ViewType.WEIXIUDAN_VIEW);
            }
            viewTypes.add(ViewType.NOT_ACCEPT_NOTICE_HEADER);
            for (int i = 0; i < notAcceptNoticeList.size(); i++)      {
                viewTypes.add(ViewType.NOT_ACCEPT_NOTICE_VIEW);
            }

            viewTypes.add(ViewType.NOT_COMPLETE_NOTICE_HEADER);

            for(int i = 0; i < notCompleteNoticetList.size(); i ++){
                viewTypes.add(ViewType.NOT_COMPLETE_NOTICE_VIEW);
            }

        }

        @Override
        public int getCount() {
            return 3 + weixiudans.size() + notAcceptNoticeList.size() + notCompleteNoticetList.size();
        }

        @Override
        public Object getItem(int position) {
            return weixiudans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position,  View convertView, ViewGroup parent) {
            View result = null;

            LayoutInflater inflater = (LayoutInflater)WeixiudanList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            switch (viewTypes.get(position)){
                case WEIXIUDAN_HEADER:
                    View list_seperator = inflater.inflate(R.layout.weixiudan_list_seperator, null);
                    TextView groupName = (TextView)list_seperator.findViewById(R.id.display);
                    Button upload      = (Button)findViewById(R.id.upload);
                    groupName.setText("维修单");
                    result = list_seperator;
                    break;
                case WEIXIUDAN_VIEW:
                    Weixiudan weixiudan = weixiudans.get( position - 1);
                    View weixiudan_item = inflater.inflate(R.layout.weixiudan_item, null);
                    TextView title = (TextView) weixiudan_item.findViewById(R.id.title);
                    title.setText(weixiudan.mYezhuName + "(" + weixiudan.address() + ")");
                    TextView detail  = (TextView)weixiudan_item.findViewById(R.id.content);
                    detail.setText(weixiudan.mBaoxiuNeirong);
                    result = weixiudan_item;
                    break;
                case NOT_ACCEPT_NOTICE_HEADER:
                    View not_accept_notice_list_seperator = inflater.inflate(R.layout.weixiudan_list_seperator, null);
                    TextView not_accept_group_name = (TextView)not_accept_notice_list_seperator.findViewById(R.id.display);
                    Button not_accept_upload      = (Button)not_accept_notice_list_seperator.findViewById(R.id.upload);
                    not_accept_group_name.setText("接单");
                    result = not_accept_notice_list_seperator;
                    break;
                case NOT_ACCEPT_NOTICE_VIEW:
                    Notice notice =  notAcceptNoticeList.get(position - 2 - weixiudans.size());
                    View not_accept_notice_item = inflater.inflate(R.layout.not_upload_accept_notice_item, null);
                    TextView not_accept_notice_title = (TextView) not_accept_notice_item.findViewById(R.id.title);
                    not_accept_notice_title.setText(notice.danjuBiaoti + "(" + notice.danjuLeixing + ")");
                    TextView not_accept_detail  = (TextView)not_accept_notice_item.findViewById(R.id.content);
                    not_accept_detail.setText(notice.danjuNeirong);
                    result = not_accept_notice_item;
                    break;
                case NOT_COMPLETE_NOTICE_HEADER:
                    View not_complete_notice_list_seperator = inflater.inflate(R.layout.weixiudan_list_seperator, null);
                    TextView not_complete_group_name = (TextView)not_complete_notice_list_seperator.findViewById(R.id.display);
                    Button not_complete_upload      = (Button)not_complete_notice_list_seperator.findViewById(R.id.upload);
                    not_complete_group_name.setText("完成单据");
                    result = not_complete_notice_list_seperator;
                    break;
                case NOT_COMPLETE_NOTICE_VIEW:
                    Notice na_notice = notAcceptNoticeList.get(position - 3 - weixiudans.size() - notAcceptNoticeList.size());
                    View not_complete_notice_item = inflater.inflate(R.layout.not_upload_complete_notice_item, null);
                    TextView not_complete_notice_title = (TextView) not_complete_notice_item.findViewById(R.id.title);
                    not_complete_notice_title.setText(na_notice.danjuBiaoti + "(" + na_notice.danjuLeixing + ")");
                    TextView not_complete_detail  = (TextView)not_complete_notice_item.findViewById(R.id.content);
                    not_complete_detail.setText(na_notice.danjuNeirong);
                    result = not_complete_notice_item;
                    break;
                default:
                    break;

            }


            return result;
        }
    }


}
