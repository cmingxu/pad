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
    @Column(name="mTishu")
    public String mTishu;

    public static ArrayList<Louceng> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Louceng> loucengs = new ArrayList<Louceng>();

        for (int i = 0; i < s.length(); i++){
            Louceng louceng = new Louceng();
            temp = s.getJSONObject(i);
            louceng.mLoucengbianhao       = temp.optString("楼层编号");
            louceng.mLougebianhao         = temp.optString("楼阁编号");
            louceng.mLoucengmingcheng     = temp.optString("楼层名称");
            louceng.mTishu     =           temp.optString("梯数");
            louceng.remoteId              = temp.getInt("id");
            loucengs.add(louceng);
        }
        return loucengs;
    }

    public static void deleteAll(){
        new Delete().from(Louceng.class).where("1=1").execute();
    }

    public static Louceng first(){
        return new Select().from(Louceng.class).executeSingle();
    }

    public List<Danyuan> danyuans(){
        Log.d("Sql", new Select().from(Danyuan.class).where("mLougebianhao='" + this.mLougebianhao + "' AND mLoucengMingcheng='" + this.mLoucengmingcheng + "'").toSql());
        return new Select().from(Danyuan.class).where("mLougebianhao='" + this.mLougebianhao + "' AND mLoucengMingcheng='" + this.mLoucengmingcheng + "'").execute();
    }

    @Override
    public String toString() {
        return mLoucengmingcheng;
    }
}
