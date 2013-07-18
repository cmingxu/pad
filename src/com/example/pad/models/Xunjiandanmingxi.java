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

@Table(name="Xunjiandanmingxi")
public class Xunjiandanmingxi extends Model {
    @Column(name="mXunjiandanId")
    public String mXunjiandanId;
    @Column(name="mXiangmuId")
    public String mXiangmuId;
    @Column(name="mZhiId")
    public String mZhiId;
    @Column(name="mXunjianShijian")
    public String mXunjianShijian;
    @Column(name="mBiaoshi")
    public String mBiaoshi;
    @Column(name="mZhi")
    public String mZhi;
    @Column(name="mShuoming")
    public String mShuoming;
    @Column(name="mRemoteId")
    public int mRemoteID;

    public static ArrayList<Xunjiandanmingxi> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Xunjiandanmingxi> xunjiandanmingxis = new ArrayList<Xunjiandanmingxi>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            Xunjiandanmingxi xunjiandanmingxi = new Xunjiandanmingxi();
            xunjiandanmingxi.mXunjiandanId = temp.optString("巡检单id", "");
            xunjiandanmingxi.mXiangmuId   = temp.optString("项目id");
            xunjiandanmingxi.mZhi   = temp.optString("值");
            xunjiandanmingxi.mZhiId   = temp.optString("值id");
            xunjiandanmingxi.mXunjianShijian   = temp.optString("巡检时间");
            xunjiandanmingxi.mBiaoshi   = temp.optString("标识");
            xunjiandanmingxi.mShuoming   = temp.optString("说明");
            xunjiandanmingxi.mRemoteID   = temp.optInt("id");

            xunjiandanmingxis.add(xunjiandanmingxi);

        }
        return xunjiandanmingxis;
    }

    public static void deleteAll(){
        new Delete().from(Xunjiandanmingxi.class).where("1=1").execute();
    }
    @Override
    public String toString() {
        return "";
       }
}
