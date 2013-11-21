package com.example.pad.models;

import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public Loupan() {
    }

    public Loupan(String mLoupanbianhao, String mLoupanmingcheng) {
        this.mLoupanbianhao = mLoupanbianhao;
        this.mLoupanmingcheng = mLoupanmingcheng;
    }

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


    public static List<Loupan> all(){
        return new Select().from(Loupan.class).execute();
    }

    public static Loupan first(){
        return new Select().from(Loupan.class).executeSingle();
    }

    public List<Louge> louges(){
        Log.d("sql", new Select().from(Louge.class).where("mLoupanbianhao='" + this.mLoupanbianhao + "'").toSql());
        return new Select().from(Louge.class).where("mLoupanbianhao='" + this.mLoupanbianhao + "'").execute();
    }

    public ArrayList<Louceng> loucengs(){
        List<Danyuan> danyuans = new Select().from(Danyuan.class).where("mLoupanbianhao='" + this.mLoupanbianhao + "'").groupBy("mLoucengMingcheng").execute();
        ArrayList<Danyuan> danyuans1 = (ArrayList<Danyuan>)danyuans;

        ArrayList<Louceng> loucengs = new ArrayList<Louceng>();
        for (Danyuan danyuan : danyuans1) {
            loucengs.add(new Louceng(danyuan.mLoucengMingcheng, danyuan.mLouceng));
        }

        return loucengs;
    }

    @Override
    public String toString() {
        return  this.mLoupanmingcheng;
    }
}
