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

@Table(name="Weixiudan")
public class Weixiudan extends Model {
    @Column(name="mLougeName")
    public String mLougeName;
    @Column(name="mLoucengName")
    public String mLoucengName;
    @Column(name="mDanyuanName")
    public String mDanyuanName;
    @Column(name="mBaoXiuRiqi")
    public String mBaoXiuRiqi;
    @Column(name="mBaoxiuren")
    public String mBaoxiuren;
    @Column(name="mYezhuName")
    public String mYezhuName;
    @Column(name="mYezhuPhone")
    public String mYezhuPhone;
    @Column(name="mBaoxiuLeibie")
    public String mBaoxiuLeibie;
    @Column(name="mBaoxiuNeirong")
    public String mBaoxiuNeirong;
    @Column(name="mSaved")
    public boolean mSaved;

    public Weixiudan() {
    }

    public Weixiudan(String mLougeName, String mLoucengName, String mDanyuanName, String mBaoXiuRiqi, String mBaoxiuren, String mYezhuName, String mYezhuPhone, String mBaoxiuLeibie, String mBaoxiuNeirong) {
        this.mLougeName = mLougeName;
        this.mLoucengName = mLoucengName;
        this.mDanyuanName = mDanyuanName;
        this.mBaoXiuRiqi = mBaoXiuRiqi;
        this.mBaoxiuren = mBaoxiuren;
        this.mYezhuName = mYezhuName;
        this.mYezhuPhone = mYezhuPhone;
        this.mBaoxiuLeibie = mBaoxiuLeibie;
        this.mBaoxiuNeirong = mBaoxiuNeirong;
    }

    public static String[] categories = new String[]{"客户区域", "公共区域",  "设备"};


    public static ArrayList<Weixiudan> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Weixiudan> weixiudans = new ArrayList<Weixiudan>();

        for (int i = 0; i < s.length(); i++){
            Weixiudan weixiudan = new Weixiudan();
            temp = s.getJSONObject(i);

            weixiudans.add(weixiudan);

        }
        return weixiudans;
    }

    public static void deleteAll(){
        new Delete().from(Weixiudan.class).where("1=1").execute();
    }


}
