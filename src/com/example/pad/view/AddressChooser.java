package com.example.pad.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.activeandroid.Model;
import com.example.pad.BaseActivity;
import com.example.pad.R;
import com.example.pad.models.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/6/13
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddressChooser extends BaseActivity {
    private Button back_btn;
    private ListView listView;

    private State state = new State();
    public ArrayAdapter<Model> adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_chooser);
        listView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<Model>(AddressChooser.this, android.R.layout.simple_list_item_1, Louge.all().toArray(new Louge[0]));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListItemOnClickListener());

        back_btn = (Button)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new BackBtnClickListener());
    }

    private void changeAdapter(){
        if (state.current_selecting.equals("louge")) {
            adapter =  new ArrayAdapter<Model>(AddressChooser.this, android.R.layout.simple_list_item_1, Louge.all().toArray(new Louge[0]));
        } else if (state.current_selecting.equals("louceng")) {
            adapter =  new ArrayAdapter<Model>(AddressChooser.this, android.R.layout.simple_list_item_1, state.current_louge.loucengs().toArray(new Louceng[0]));
        } else if (state.current_selecting.equals("danyuan")) {
            adapter =  new ArrayAdapter<Model>(AddressChooser.this, android.R.layout.simple_list_item_1, state.current_louceng.danyuans().toArray(new Danyuan[0]));
        } else {
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListItemOnClickListener());
        listView.invalidate();
    }

    protected class BackBtnClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View view) {
            if (state.current_selecting.equals("louge")) {
                Intent i = new Intent();
                i.setClass(AddressChooser.this, NewForm.class);
                startActivity(i);
            } else if (state.current_selecting.equals("louceng")) {
            } else if (state.current_selecting.equals("danyuan")) {
            } else {
            }
            state.pre();
            changeAdapter();

        }
    }

    private class ListItemOnClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            if (state.current_selecting.equals("louge")) {
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
                Zhuhu zhuhu = state.current_danyuan.zhuhu();
                result.setmYezhuName(zhuhu.mZhuhuMingcheng);
                result.setmYezhuDianhua(zhuhu.mShoujiHaoma);
                result.setmZhuhuBianhao(zhuhu.mZhuhuBianhao);
                Intent intent = new Intent();
                intent.putExtra("result", result);

                setResult(1, intent);
                AddressChooser.this.finish();
            } else {
            }
            state.next();
            changeAdapter();
        }
    }

    protected class State{
        public String current_selecting = "louge";
        public ArrayList<String> states = null;
        Louge current_louge;
        Louceng current_louceng;
        Danyuan current_danyuan;


        public State() {
            this.states = new ArrayList<String>();
            this.states.add("louge");
            this.states.add("louceng");
            this.states.add("danyuan");
        }

        public String selectedAddress(){
            return this.current_louge.toString() + "/" +
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