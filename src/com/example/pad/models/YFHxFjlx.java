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

@Table(name="YFHxFjlx")
public class YFHxFjlx extends Model {
    @Column(name="mHxId")
    public String mHxId;
    @Column(name="mFjlxId")
    public String mFjlxId;
    @Column(name="mRemoteId")
    public String mRemoteId;

    public static ArrayList<YFHxFjlx> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<YFHxFjlx> yf_hx_fjlxs = new ArrayList<YFHxFjlx>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            YFHxFjlx yf_hx_fjlx = new YFHxFjlx();
            yf_hx_fjlx.mHxId = temp.optString("HXID", "");
            yf_hx_fjlx.mFjlxId = temp.optString("FJLXID", "");
            yf_hx_fjlx.mRemoteId = temp.optString("id", "");

            yf_hx_fjlxs.add(yf_hx_fjlx);

        }
        return yf_hx_fjlxs;
    }

    public static void deleteAll(){
        new Delete().from(YFHxFjlx.class).where("1=1").execute();
    }

    @Override
    public String toString() {
         return  this.mHxId + "/" + this.mFjlxId;
    }
}
