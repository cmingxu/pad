package com.example.pad.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.actionbarsherlock.view.MenuItem;
import com.activeandroid.Model;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/6/13
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class DanyuanAddressChooser extends BaseActivity {
    private Button back_btn;
    private ListView listView;

    private State state = new State();
    public ArrayAdapter<Model> adapter;

    public void onCreate(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("title");
        if (title == null) {
            title = "维修地址";
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_chooser);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.icon_fanhui_on));
        getSupportActionBar().setTitle(title);

        listView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<Model>(DanyuanAddressChooser.this, android.R.layout.simple_list_item_1, Loupan.all().toArray(new Loupan[0]));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListItemOnClickListener());


    }

    private void changeAdapter(){
        if(state.current_selecting.equals("loupan")){
            adapter =  new ArrayAdapter<Model>(DanyuanAddressChooser.this, android.R.layout.simple_list_item_1, Loupan.all().toArray(new Loupan[0]));
        }
        else if (state.current_selecting.equals("louge")) {
            adapter =  new ArrayAdapter<Model>(DanyuanAddressChooser.this, android.R.layout.simple_list_item_1, state.current_loupan.louges().toArray(new Louge[0]));
        } else if (state.current_selecting.equals("louceng")) {
            adapter =  new ArrayAdapter<Model>(DanyuanAddressChooser.this, android.R.layout.simple_list_item_1, state.current_louge.loucengs().toArray(new Louceng[0]));
        } else if (state.current_selecting.equals("danyuan")) {
            adapter =  new ArrayAdapter<Model>(DanyuanAddressChooser.this, android.R.layout.simple_list_item_1, state.current_louceng.danyuans().toArray(new Danyuan[0]));
        } else {
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListItemOnClickListener());
        listView.invalidate();
    }

        public void back() {
            if (state.current_selecting.equals("loupan")) {
                finish();
            } else if (state.current_selecting.equals("louge")) {
            } else if (state.current_selecting.equals("louceng")) {
            } else if (state.current_selecting.equals("danyuan")) {
            } else {
            }
            state.pre();
            changeAdapter();

        }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                back();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private class ListItemOnClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            if(state.current_selecting.equals("loupan")){
                state.current_loupan = (Loupan) adapter.getItem(i);
            }
            else if (state.current_selecting.equals("louge")) {
                state.current_louge = (Louge) adapter.getItem(i);
            } else if (state.current_selecting.equals("louceng")) {
                state.current_louceng = (Louceng) adapter.getItem(i);
            } else if (state.current_selecting.equals("danyuan")) {

                state.current_danyuan = (Danyuan)adapter.getItem(i);

                AddressChooserResult result = new AddressChooserResult();
                result.setmDanyuanName(state.current_danyuan.mDanyuanmingcheng);
                result.setmDanyuanId(state.current_danyuan.getId());
                result.setmLoucengId(state.current_louceng.getId());
                result.setmLoucengName(state.current_louceng.mLoucengmingcheng);
                result.setmLougeId(state.current_louge.getId());
                result.setmLougeName(state.current_louge.mLougemingcheng);
                result.setmDanyuanbianhao(state.current_danyuan.mDanyuanbianhao);
                result.setmLougebianhao(state.current_louge.mLougebianhao);
                result.setmLoupanbianhao(state.current_loupan.mLoupanbianhao);
                result.setmLoupanName(state.current_loupan.mLoupanmingcheng);
                Zhuhu zhuhu = state.current_danyuan.zhuhu();
                if (zhuhu != null) {
                    result.setmYezhuName(zhuhu.mZhuhuMingcheng);
                    result.setmYezhuDianhua(zhuhu.mShoujiHaoma);
                    result.setmZhuhuBianhao(zhuhu.mZhuhuBianhao);
                }
                Intent intent = new Intent();
                intent.putExtra("result", result);

                setResult(1, intent);
                DanyuanAddressChooser.this.finish();
            } else {
            }
            state.next();
            changeAdapter();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected class State{
        public String current_selecting = "loupan";
        public ArrayList<String> states = null;
        Loupan current_loupan;
        Louge current_louge;
        Louceng current_louceng;
        Danyuan current_danyuan;


        public State() {
            this.states = new ArrayList<String>();
            this.states.add("loupan");
            this.states.add("louge");
            this.states.add("louceng");
            this.states.add("danyuan");
        }

        public String selectedAddress(){
            return this.current_loupan.toString() + "/" +
                    this.current_louge.toString() + "/" +
                    this.current_louceng.toString() + "/" +
                    this.current_danyuan.toString();
        }

        public void next(){
            int current_index =  this.states.indexOf(current_selecting) + 1;
            if (current_index >= this.states.size()) current_index = this.states.size() - 1;
            current_selecting = this.states.get(current_index);
        }
        public void pre(){
            int current_index =  this.states.indexOf(current_selecting) - 1;
            if (current_index < 0) current_index = 0;
            current_selecting = this.states.get(current_index);
        }
    }

}