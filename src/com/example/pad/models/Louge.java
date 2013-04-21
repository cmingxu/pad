package com.example.pad.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/15/13
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name="Louge")
public class Louge extends Model {
    @Column(name="mLoupanbianhao")
    public String mLoupanbianhao;

    @Column(name="mLougebianhao")
    public String mLougebianhao;

    @Column(name="mLougemingcheng")
    public String mLougemingcheng;

    public static ArrayList<Louge> fromJsonArray(JSONArray jsonArray) throws JSONException {
        JSONObject temp = null;
        ArrayList<Louge> louges = new ArrayList<Louge>();

        for (int i = 0; i < jsonArray.length(); i++){
            Louge louge = new Louge();
            temp = jsonArray.getJSONObject(i);
            louge.mLoupanbianhao   = temp.getString("楼盘编号");
            louge.mLougebianhao    = temp.getString("楼阁编号");
            louge.mLougemingcheng  = temp.getString("楼阁名称");
            louges.add(louge);

        }
        return louges;
    }

    public static void deleteAll(){
        new Delete().from(Louge.class).where("1=1").execute();
    }
}
