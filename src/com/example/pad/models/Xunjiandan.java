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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Column(name = "mRemoteID")
    public int mRemoteID;
    @Column(name="mXunjianren")
    public String mXunjianren;






    public static ArrayList<Xunjiandan> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Xunjiandan> xunjiandans = new ArrayList<Xunjiandan>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            Xunjiandan xunjiandan = new Xunjiandan();
            xunjiandan.mDanjuBianHao = temp.optString("单据编号", "");
            xunjiandan.mDanjuXuhao   = temp.getString("单据序号");
            xunjiandan.mZhidanRiqi   = temp.optString("制单日期");
            xunjiandan.mLururen   = temp.getString("录入人");
            xunjiandan.mLuruShijian   = temp.getString("录入时间");
            xunjiandan.mJihuaQishiShijian   = temp.getString("计划起始时间");
            xunjiandan.mJihuaZhongzhiShijian   = temp.getString("计划终止时间");
            xunjiandan.mShifouWancheng  = temp.getBoolean("是否完成");
            xunjiandan.mXunjianQishiShijian   = temp.optString("巡检起始时间");
            xunjiandan.mXunjianZhongzhiShijian   = temp.optString("巡检终止时间");
            xunjiandan.mShifouShenhe   = temp.getBoolean("是否审核");
            xunjiandan.mShenheRiqi   = temp.optString("审核日期");
            xunjiandan.mShenheren = temp.optString("审核人");
            xunjiandan.mRemoteID = temp.optInt("id");
            xunjiandan.mXunjianren = temp.optString("巡检人");

            xunjiandans.add(xunjiandan);

        }
        return xunjiandans;
    }

    public static void deleteAll(){
        new Delete().from(Xunjiandan.class).where("1=1").execute();
    }



    public List<Xunjiandanmingxi> xunjiandanmingxis(){
        return new Select().from(Xunjiandanmingxi.class).where("mXunjiandanId=" + this.mRemoteID).execute();
    }

    public List<Xunjiandian> finishedXunjiandians(){
        return this.xunjiandians(true);
    }

    public List<Xunjiandian> notFinishedXunjiandians(){
        return this.xunjiandians(false);
    }


     public List<Xunjiandian> xunjiandians(boolean finished){
            List<Xunjiandanmingxi> xunjiandanmingxis;
            if(finished)  {
                xunjiandanmingxis= new Select().from(Xunjiandanmingxi.class).where("mXunjiandanId=" + this.mRemoteID + " and mXunjianShijian != ''").execute();
             }
            else{
                xunjiandanmingxis = new Select().from(Xunjiandanmingxi.class).where("mXunjiandanId=" + this.mRemoteID+ " and mXunjianShijian = ''").execute();
             }
        StringBuilder sb = new StringBuilder("");
        for(Xunjiandanmingxi mx : xunjiandanmingxis){
            if(!sb.toString().equals(""))
                sb.append(",");
            sb.append(mx.mRemoteID);
        }
        List<Xunjianxiangmu> xunjianxiangmus = new Select().from(Xunjianxiangmu.class).where("mRemoteID in (" + sb.toString() + ")").execute();
        Set set = new HashSet();
        for(Xunjianxiangmu xm : xunjianxiangmus){
            set.add(xm.mXunjiandianId);
        }
        StringBuilder sb1 = new StringBuilder("");
        for(Object id: set){
            if(!sb1.toString().equals(""))
                sb1.append(",");
            sb1.append((String)id);
        }
        return new Select().from(Xunjiandian.class).where("mRemoteId in (" + sb1.toString() + ")").execute();
    }

    public static List<Xunjiandian> xunjiandians(String username){
        return new Select().from(Xunjiandian.class).where("mXunjianren='" + username + "'").execute();
    }

    public static List<Xunjiandian> finishedXunjiandian(String username){
//        return new Select().from(Xunjiandan.class).where("mXunjianren='" + username + "' and mShifouWancheng=1").execute();
        return null;

    }

    public static List<Xunjiandian> notFinishedXunjiandian(String username){
        return new Select().from(Xunjiandan.class).where("mXunjianren='" + username + "' and ShifouWancheng=0").execute();
    }


    public static Xunjiandan findByRemoteId(int id){
        return new Select().from(Xunjiandan.class).where("mRemoteID=" + id + "").executeSingle();

    }

    @Override
    public String toString() {
         return  this.mDanjuBianHao;
    }
}
