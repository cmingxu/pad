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
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="Danyuan")
public class Danyuan extends Model {
    @Column(name="mLougebianhao")
    public String mLougebianhao;
    @Column(name="mLouceng")
    public String mLouceng;
    @Column(name="mLoucengMingcheng")
    public String mLoucengMingcheng;
    @Column(name="mDanyuanbianhao")
    public String mDanyuanbianhao;
    @Column(name="mDanyuanmincheng")
    public String mDanyuanmingcheng;
    @Column(name="mYezhubianhao")
    public String mYezhubianhao;
    @Column(name="mJiange")
    public String mJiange;
    @Column(name="remoteId")
    public int remoteId;


    public static ArrayList<Danyuan> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Danyuan> danyuans = new ArrayList<Danyuan>();

        for (int i = 0; i < s.length(); i++){
            Danyuan danyuan = new Danyuan();
            temp = s.getJSONObject(i);
            danyuan.mDanyuanbianhao = temp.getString("单元编号");
            danyuan.mLougebianhao   = temp.getString("楼阁编号");
            danyuan.mLouceng        = temp.getString("楼层");
            danyuan.mLoucengMingcheng = temp.getString("楼层名称");
            danyuan.mDanyuanmingcheng = temp.getString("单元名称");
            danyuan.mYezhubianhao   = temp.optString("业主编号");
            danyuan.mJiange         = temp.optString("间隔", "");
            danyuan.remoteId        = temp.getInt("id");
            danyuans.add(danyuan);

        }
        return danyuans;
    }

    public static void deleteAll(){
        new Delete().from(Danyuan.class).where("1=1").execute();
    }

    public static Danyuan findByDanyuanbianhao(String danyuanbianhao){

        return new Select().from(Danyuan.class).where("mDanyuanbianhao = '" + danyuanbianhao + "'").executeSingle();

    }


    public Zhuhu zhuhu(){
        Log.d("sddd", new Select().from(Zhuhu.class).where("mZhuhuBianhao = '" + this.mYezhubianhao + "'").toSql());
        return new Select().from(Zhuhu.class).where("mZhuhuBianhao = '" + this.mYezhubianhao + "'").executeSingle();
    }

    public List<DanyuanbiaoChaobiao> danyuanbiaoChaobiaos(){
        return new Select().from(DanyuanbiaoChaobiao.class).where("mDanyuanBianhao = '" + this.mDanyuanbianhao + "'").execute();
    }

    @Override
    public String toString() {
        return this.mDanyuanmingcheng;

    }

    public YFHuxing huxing(){
        return new Select().from(YFHuxing.class).where("mHxmc='" + this.mJiange +"'").executeSingle();
    }
}
