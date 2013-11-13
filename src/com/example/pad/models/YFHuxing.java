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

@Table(name="YFHuxing")
public class YFHuxing extends Model {
    @Column(name="mHxmc")
    public String mHxmc;
    @Column(name="mHxbh")
    public String mHxbh;
    @Column(name="mHxt")
    public String mHxt;
    @Column(name="mRemoteId")
    public String mRemoteId;


    public static ArrayList<YFHuxing> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<YFHuxing> yf_huxings = new ArrayList<YFHuxing>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            YFHuxing yf_huxing = new YFHuxing();
            yf_huxing.mHxmc = temp.optString("HXMC", "");
            yf_huxing.mHxbh = temp.optString("HXHB", "");
            yf_huxing.mHxt  = temp.optString("HXT", "");
            yf_huxing.mRemoteId = temp.optString("id", "");

            yf_huxings.add(yf_huxing);

        }
        return yf_huxings;
    }

    public static void deleteAll(){
        new Delete().from(YFHuxing.class).where("1=1").execute();
    }

    @Override
    public String toString() {
         return  this.mHxmc + "/" + this.mHxbh;
    }
}
