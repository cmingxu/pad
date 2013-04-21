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
@Table(name = "Loupan")
public class Loupan extends Model {
    @Column(name="mLoupanbianhao")
    public String mLoupanbianhao;

    @Column(name="mLoupanmingcheng")
    public String mLoupanmingcheng;

    public static ArrayList<Loupan> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Loupan> loupans = new ArrayList<Loupan>();

        for (int i = 0; i < s.length(); i++){
            Loupan loupan = new Loupan();
            temp = s.getJSONObject(i);
            loupan.mLoupanbianhao    = temp.getString("楼盘编号");
            loupan.mLoupanmingcheng  = temp.getString("楼盘名称");
            loupans.add(loupan);

        }
        return loupans;
    }

    public static void deleteAll(){
        new Delete().from(Loupan.class).where("1=1").execute();
    }

}
