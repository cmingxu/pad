package com.example.pad.models;

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
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="Cidian")
public class Cidian extends Model {
    @Column(name="mLeibie")
    public String mLeibie;
    @Column(name="mMingcheng")
    public String mMingcheng;





    public static ArrayList<Cidian> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Cidian> cidians = new ArrayList<Cidian>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            Cidian cidian = new Cidian();
            cidian.mLeibie = temp.optString("类别", "");
            cidian.mMingcheng   = temp.getString("名称");

            cidians.add(cidian);

        }
        return cidians;
    }

    public static void deleteAll(){
        new Delete().from(Cidian.class).where("1=1").execute();
    }

    public static List<Cidian> allWanCheng(){
        List<Cidian> u = new Select().from(Cidian.class).where("mLeibie='客服_维修_完成情况'").execute();
        return u;
    }

    public static List<Cidian> allJiedan(){
        List<Cidian> u = new Select().from(Cidian.class).where("mLeibie='客服_维修_未进行原因'").execute();
        return u;
    }


    @Override
    public String toString() {
         return  this.mLeibie + "/" + this.mMingcheng;
    }
}
