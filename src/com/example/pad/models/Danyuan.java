package com.example.pad.models;

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
public class Danyuan extends Base {
    public String mLougebianhao;
    public String mLouceng;
    public String mLoucengMingcheng;
    public String mDanyuanbianhao;
    public String mDanyuanmingcheng;
    public String mYezhubianhao;
    public String mJiange;

    public static final String DB_CREATE_STATEMENT = "" +
            "drop table danyuans if exists danyuans;" +
            "create table danyuans(" +
            "mLougebianhao varchar(255)," +
            "mLouceng varchar(255)," +
            "mLoucengMingcheng varchar(255)," +
            "mDanyuanbianhao varchar(255)," +
            "mDanyuanmingcheng varchar(255)," +
            "mYezhubianhao varchar(255)," +
            "mJiange varchar(255)" +
            ");"
            ;





    public static ArrayList<Danyuan> danyuansFromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Danyuan> danyuans = new ArrayList<Danyuan>();

        for (int i = 0; i < s.length(); i++){
            Danyuan danyuan = new Danyuan();
            temp = s.getJSONObject(i);
            danyuan.mDanyuanbianhao = temp.getString("");
            danyuan.mLougebianhao   = temp.getString("");
            danyuan.mLouceng        = temp.getString("");
            danyuan.mLoucengMingcheng = temp.getString("");
            danyuan.mDanyuanmingcheng = temp.getString("");
            danyuan.mYezhubianhao   = temp.getString("");
            danyuan.mJiange         = temp.getString("");
            danyuans.add(danyuan);

        }
        return danyuans;
    }

}
