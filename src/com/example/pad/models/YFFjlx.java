package com.example.pad.models;

import android.util.Log;
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

@Table(name="YFFjlx")
public class YFFjlx extends Model implements Comparable<YFFjlx> {
    @Column(name="mFjmc")
    public String mFjmc;
    @Column(name="mFjbh")
    public String mFjbh;
    @Column(name="mRemoteId")
    public int mRemoteId;

    public static ArrayList<YFFjlx> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<YFFjlx> yf_huxings = new ArrayList<YFFjlx>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            YFFjlx yf_huxing = new YFFjlx();
            yf_huxing.mFjmc = temp.optString("FJMC", "");
            yf_huxing.mFjbh = temp.optString("FJBH", "");
            yf_huxing.mRemoteId = temp.optInt("id", -1);

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

    public List<YFYsdx> ysdxs(){
        Log.d("sql", new Select().from(YFYsdx.class).join(YFFjlxYsdx.class).on("YFFjlxYsdx.mDxId = YFYsdx.mRemoteId").where("YFFjlxYsdx.mFjlxId = '" + this.mRemoteId + "'").toSql());
        return new Select("YFYsdx.mRemoteId, YFYsdx.mDxmc, YFYsdx.mDxbh, YFYsdx.id ").from(YFYsdx.class).join(YFFjlxYsdx.class).on("YFFjlxYsdx.mDxId= YFYsdx.mRemoteId").where("YFFjlxYsdx.mFjlxId = '" + this.mRemoteId + "'").execute();
    }

    public static YFFjlx findByRemoteId(int id) {
        Log.d("sql", new Select().from(YFFjlx.class).where("mRemoteId=" + id).toSql()) ;

        return new Select().from(YFFjlx.class).where("mRemoteId=" + id).executeSingle() ;
    }

    @Override
    public int compareTo(YFFjlx another) {
        return Integer.parseInt(this.mFjbh) < Integer.parseInt(another.mFjbh) ? -1 : 1;
    }
}
