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

@Table(name="YFFjlxYsdx")
public class YFFjlxYsdx extends Model {
    @Column(name="mFjlxId")
    public String mFjlxId;
    @Column(name="mDxId")
    public String mDxId;
    @Column(name="mRemoteId")
    public String mRemoteId;

    public static ArrayList<YFFjlxYsdx> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<YFFjlxYsdx> yf_fjlx_ysdxs = new ArrayList<YFFjlxYsdx>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            YFFjlxYsdx yf_fjlx_ysdx = new YFFjlxYsdx();
            yf_fjlx_ysdx.mFjlxId = temp.optString("FJLXID", "");
            yf_fjlx_ysdx.mDxId = temp.optString("DXID", "");
            yf_fjlx_ysdx.mRemoteId = temp.optString("id", "");

            yf_fjlx_ysdxs.add(yf_fjlx_ysdx);

        }
        return yf_fjlx_ysdxs;
    }

    public static void deleteAll(){
        new Delete().from(YFFjlxYsdx.class).where("1=1").execute();
    }

    @Override
    public String toString() {
         return  this.mFjlxId + "/" + this.mDxId;
    }
}
