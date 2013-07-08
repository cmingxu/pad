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

@Table(name="Xunjiandan")
public class Xunjiandan extends Model {
    @Column(name="mDanjuBianHao")
    public String mDanjuBianHao;
    @Column(name="mDanjuXuhao")
    public String mDanjuXuhao;
    @Column(name="mZhidanRiqi")
    public String mZhidanRiqi;
    @Column(name="mLururen")
    public String mLururen;
    @Column(name="mLuruShijian")
    public String mLuruShijian;
    @Column(name="mJihuaQishiShijian")
    public String mJihuaQishiShijian;
    @Column(name="mJihuaZhongzhiShijian")
    public String mJihuaZhongzhiShijian;
    @Column(name="mShifouWancheng")
    public boolean mShifouWancheng;
    @Column(name="mXunjianQishiShijian")
    public String mXunjianQishiShijian;
    @Column(name="mXunjianZhongzhiShijian")
    public String mXunjianZhongzhiShijian;
    @Column(name="mShifouShenhe")
    public boolean mShifouShenhe;
    @Column(name="mShenheRiqi")
    public String mShenheRiqi;
    @Column(name="mShenheren")
    public String mShenheren;






    public static ArrayList<Xunjiandan> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Xunjiandan> xunjiandans = new ArrayList<Xunjiandan>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            Xunjiandan xunjiandan = new Xunjiandan();
            xunjiandan.mDanjuBianHao = temp.optString("单据编号", "");
            xunjiandan.mDanjuXuhao   = temp.getString("单据序号");
            xunjiandan.mZhidanRiqi   = temp.getString("制单日期");
            xunjiandan.mLururen   = temp.getString("录入人");
            xunjiandan.mLuruShijian   = temp.getString("录入时间");
            xunjiandan.mJihuaQishiShijian   = temp.getString("计划起始时间");
            xunjiandan.mJihuaZhongzhiShijian   = temp.getString("计划终止时间");
            xunjiandan.mShifouWancheng  = temp.getBoolean("是否完成");
            xunjiandan.mXunjianQishiShijian   = temp.getString("巡检起始时间");
            xunjiandan.mXunjianZhongzhiShijian   = temp.getString("巡检终止时间");
            xunjiandan.mShifouShenhe   = temp.getBoolean("是否审核");
            xunjiandan.mShenheRiqi   = temp.getString("审核日期");
            xunjiandan.mShenheren = temp.getString("审核人");

            xunjiandans.add(xunjiandan);

        }
        return xunjiandans;
    }

    public static void deleteAll(){
        new Delete().from(Xunjiandan.class).where("1=1").execute();
    }




    @Override
    public String toString() {
         return  this.mDanjuBianHao;
    }
}
