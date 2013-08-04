package com.example.pad.models;

import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.pad.common.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/15/13
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "Xunjiandan")
public class Xunjiandan extends Model {
    @Column(name = "mDanjuBianHao")
    public String mDanjuBianHao;
    @Column(name = "mDanjuXuhao")
    public String mDanjuXuhao;
    @Column(name = "mZhidanRiqi")
    public String mZhidanRiqi;
    @Column(name = "mLururen")
    public String mLururen;
    @Column(name = "mLuruShijian")
    public String mLuruShijian;
    @Column(name = "mJihuaQishiShijian")
    public String mJihuaQishiShijian;
    @Column(name = "mJihuaZhongzhiShijian")
    public String mJihuaZhongzhiShijian;
    @Column(name = "mShifouWancheng")
    public boolean mShifouWancheng;
    @Column(name = "mXunjianQishiShijian")
    public String mXunjianQishiShijian;
    @Column(name = "mXunjianZhongzhiShijian")
    public String mXunjianZhongzhiShijian;
    @Column(name = "mShifouShenhe")
    public boolean mShifouShenhe;
    @Column(name = "mShenheRiqi")
    public String mShenheRiqi;
    @Column(name = "mShenheren")
    public String mShenheren;
    @Column(name = "mRemoteID")
    public int mRemoteID;
    @Column(name = "mXunjianren")
    public String mXunjianren;

    public static ArrayList<Xunjiandan> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Xunjiandan> xunjiandans = new ArrayList<Xunjiandan>();
        for (int i = 0; i < s.length(); i++) {
            temp = s.getJSONObject(i);
            Xunjiandan xunjiandan = new Xunjiandan();
            xunjiandan.mDanjuBianHao = temp.optString("单据编号", "");
            xunjiandan.mDanjuXuhao = temp.getString("单据序号");
            xunjiandan.mZhidanRiqi = temp.optString("制单日期");
            xunjiandan.mLururen = temp.getString("录入人");
            xunjiandan.mLuruShijian = temp.getString("录入时间");
            xunjiandan.mJihuaQishiShijian = temp.getString("计划起始时间");
            xunjiandan.mJihuaZhongzhiShijian = temp.getString("计划终止时间");
            xunjiandan.mShifouWancheng = temp.getBoolean("是否完成");
            xunjiandan.mXunjianQishiShijian = temp.optString("巡检起始时间");
            xunjiandan.mXunjianZhongzhiShijian = temp.optString("巡检终止时间");
            xunjiandan.mShifouShenhe = temp.getBoolean("是否审核");
            xunjiandan.mShenheRiqi = temp.optString("审核日期");
            xunjiandan.mShenheren = temp.optString("审核人");
            xunjiandan.mRemoteID = temp.optInt("id");
            xunjiandan.mXunjianren = temp.optString("巡检人");

            Log.d("xunjiandan", temp.toString());
            xunjiandans.add(xunjiandan);

        }
        return xunjiandans;
    }

    public static void deleteAll() {
        new Delete().from(Xunjiandan.class).where("1=1").execute();
    }

    public static Xunjiandan findByRemoteId(int id) {
        return new Select().from(Xunjiandan.class).where("mRemoteID=" + id + "").executeSingle();

    }

    public List<Xunjiandanmingxi> xunjiandanmingxis() {
        return new Select().from(Xunjiandanmingxi.class).where("mXunjiandanId=" + this.mRemoteID).execute();
    }

    public List<Xunjiandian> finishedXunjiandians() {
        return this.xunjiandians(true);
    }

    public List<Xunjiandian> notFinishedXunjiandians() {
        return this.xunjiandians(false);
    }

    public List<Xunjiandian> xunjiandians(boolean finished) {
        List<Xunjiandanmingxi> xunjiandanmingxis = new Select().from(Xunjiandanmingxi.class).where("mXunjiandanId=" + this.mRemoteID).execute();
        //find out xunjianxiangmuid and mark finished xiangmus
        Set finishedXunjianxiangmuIds = new HashSet();
        StringBuilder sb = new StringBuilder("");
        for (Xunjiandanmingxi mx : xunjiandanmingxis) {
            if (!sb.toString().equals(""))
                sb.append(",");
            sb.append(mx.mRemoteID);
            if(!StringUtils.isEmpty(mx.mXunjianShijian)){
                finishedXunjianxiangmuIds.add(mx.mXiangmuId);
            }
        }
        // find out xunjiandians
        List<Xunjianxiangmu> xunjianxiangmus = new Select().from(Xunjianxiangmu.class).where("mRemoteID in (" + sb.toString() + ")").execute();
        Set set = new HashSet();
        for (Xunjianxiangmu xm : xunjianxiangmus) {
            set.add(xm.mXunjiandianId);
        }
        StringBuilder sb1 = new StringBuilder("");
        for (Object id : set) {
            if (!sb1.toString().equals(""))
                sb1.append(",");
            sb1.append((String) id);
        }
        List<Xunjiandian> xunjiandians = new Select().from(Xunjiandian.class).where("mRemoteId in (" + sb1.toString() + ")").execute();
        ArrayList<Xunjiandian> results = new ArrayList<Xunjiandian>();

//        find out which xunjiandians are finished or not
        for (Xunjiandian xunjiandian : xunjiandians) {
            boolean xunjiangdian_finished = true;
            for (Xunjianxiangmu xunjianxiangmu : xunjiandian.xunjianxiangmusForXunjiandan(this.mRemoteID)) {
                if(!finishedXunjianxiangmuIds.contains(String.valueOf(xunjianxiangmu.mRemoteID))){
                    xunjiangdian_finished = false;
                    break;
                }
            }

            if(finished){
               if(xunjiangdian_finished){
                   results.add(xunjiandian);
               }
            }else {
                if(!xunjiangdian_finished){
                    results.add(xunjiandian);
                }
            }

        }
        return results;
    }

    public static List<Xunjiandan> findAll(){
       return  new Select().from(Xunjiandan.class).execute();
    }

    public boolean finished(){
        boolean finish = true;
        for (Xunjiandanmingxi xunjiandanmingxi : this.xunjiandanmingxis()) {
            if(!xunjiandanmingxi.isFinished()){
              finish = false;
            }
        }
        return finish;
    }

    public static boolean exists(int remoteId){
        return new Select().from(Xunjiandan.class).where("mRemoteID=" + remoteId).executeSingle() != null;
    }

    public String minTime(){
        ArrayList<Date> dates = new ArrayList<Date>();
        for (Xunjiandanmingxi xunjiandanmingxi : this.xunjiandanmingxis()) {
            dates.add(StringUtils.parseTime(xunjiandanmingxi.mXunjianShijian));
        }

        Collections.sort(dates);
        return StringUtils.formatTime(dates.get(0));
    }

    public String maxTime(){

        ArrayList<Date> dates = new ArrayList<Date>();
        for (Xunjiandanmingxi xunjiandanmingxi : this.xunjiandanmingxis()) {
            dates.add(StringUtils.parseTime(xunjiandanmingxi.mXunjianShijian));
        }

        Collections.sort(dates);
        Collections.reverse(dates);
        return StringUtils.formatTime(dates.get(0));
    }

    @Override
    public String toString() {
        return "";
    }
}
