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
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="Zhuhu")
public class Zhuhu extends Model {
    @Column(name="mZhuhuMingcheng")
    public String mZhuhuMingcheng;
    @Column(name="mZhuhuBianhao")
    public String mZhuhuBianhao;
    @Column(name="mShoujiHaoma")
    public String mShoujiHaoma;
    @Column(name="mLianxiDianhua")
    public String mLianxiDianhua;
    @Column(name="mLianxiDiZhi")
    public String mLianxiDizhi;
    @Column(name="mRemoteID")
    public String mRemoteID;





    public static ArrayList<Zhuhu> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Zhuhu> zhuhus = new ArrayList<Zhuhu>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            Zhuhu zhuhu = new Zhuhu();
            zhuhu.mZhuhuMingcheng = temp.optString("住户名称", "");
            zhuhu.mZhuhuBianhao   = temp.getString("住户编号");
            zhuhu.mShoujiHaoma    = temp.optString("手机号码");
            zhuhu.mLianxiDianhua  = temp.optString("联系电话");
            zhuhu.mLianxiDizhi    = temp.optString("联系地址");

            zhuhus.add(zhuhu);

        }
        return zhuhus;
    }

    public static void deleteAll(){
        new Delete().from(Zhuhu.class).where("1=1").execute();
    }

    @Override
    public String toString() {
         return  this.mZhuhuMingcheng + "/" + this.mZhuhuBianhao;
    }
}
