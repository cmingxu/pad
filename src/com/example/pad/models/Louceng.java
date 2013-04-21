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
 * Date: 4/17/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="Louceng")
public class Louceng extends Model{

    @Column(name="remoteId")
    public int remoteId;
    @Column(name="mLougebianhao")
    public String mLougebianhao;
    @Column(name="mLoucengbianhao")
    public String mLoucengbianhao;
    @Column(name="mLoucengmingheng")
    public String mLoucengmingcheng;

    public static ArrayList<Louceng> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Louceng> loucengs = new ArrayList<Louceng>();

        for (int i = 0; i < s.length(); i++){
            Louceng louceng = new Louceng();
            temp = s.getJSONObject(i);
            louceng.mLoucengbianhao       = temp.getString("楼层编号");
            louceng.mLougebianhao         = temp.getString("楼阁编号");
            louceng.mLoucengmingcheng     = temp.getString("楼层名称");
            louceng.remoteId              = temp.getInt("id");
            loucengs.add(louceng);
        }
        return loucengs;
    }

    public static void deleteAll(){
        new Delete().from(Louceng.class).where("1=1").execute();
    }
}
