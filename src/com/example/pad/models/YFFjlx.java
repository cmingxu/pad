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

@Table(name="YFFjlx")
public class YFFjlx extends Model {
    @Column(name="mFjmc")
    public String mFjmc;
    @Column(name="mFjbh")
    public String mFjbh;
    @Column(name="mRemoteId")
    public String mRemoteId;

    public static ArrayList<YFFjlx> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<YFFjlx> yf_huxings = new ArrayList<YFFjlx>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            YFFjlx yf_huxing = new YFFjlx();
            yf_huxing.mFjmc = temp.optString("FJMC", "");
            yf_huxing.mFjbh = temp.optString("FJBH", "");
            yf_huxing.mRemoteId = temp.optString("id", "");

            yf_huxings.add(yf_huxing);

        }
        return yf_huxings;
    }

    public static void deleteAll(){
        new Delete().from(YFFjlx.class).where("1=1").execute();
    }

    @Override
    public String toString() {
         return  this.mFjmc + "/" + this.mFjbh;
    }
}
