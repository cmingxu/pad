package com.example.pad.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.pad.R;
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

@Table(name="YFYsdx")
public class YFYsdx extends Model implements Comparable<YFYsdx> {
    @Column(name="mDxmc")
    public String mDxmc;
    @Column(name="mDxbh")
    public String mDxbh;
    @Column(name="mRemoteId")
    public int mRemoteId;

    public static ArrayList<YFYsdx> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<YFYsdx> yf_ysdxs = new ArrayList<YFYsdx>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            YFYsdx yf_ysdx = new YFYsdx();
            yf_ysdx.mDxmc = temp.optString("DXMC", "");
            yf_ysdx.mDxbh = temp.optString("DXBH", "");
            yf_ysdx.mRemoteId = temp.optInt("id", -1);

            yf_ysdxs.add(yf_ysdx);

        }
        return yf_ysdxs;
    }

    public static void deleteAll(){
        new Delete().from(YFYsdx.class).where("1=1").execute();
    }

    @Override
    public String toString() {
        return  this.mDxmc + "/" + this.mDxbh;
    }

    public List<YFYsxm> ysxms(){
            return  new Select().from(YFYsxm.class).where("mDxId = '"+ this.mRemoteId + "'").execute();
    }


    public static YFYsdx findById(int id){
        Log.d("sql",   new Select().from(YFYsdx.class).where("mRemoteId="+ id + "").toSql());
        return  new Select().from(YFYsdx.class).where("mRemoteId="+ id + "").executeSingle();

    }
    @Override
    public int compareTo(YFYsdx another) {
        return Integer.parseInt(this.mDxbh) - Integer.parseInt(another.mDxbh);
    }

    public Drawable indicationIcon(Context context, String danyuanbianhao, int fjlxid){
        YFYfRecord yfYfRecord = YFYfRecord.findByDanyuanbianhaoAndDxIdAndFjlxId(danyuanbianhao, this.mRemoteId, fjlxid);
        if (yfYfRecord == null) {
            return context.getResources().getDrawable(R.drawable.yes_icon);
        }   else{
            return context.getResources().getDrawable(R.drawable.no_icon);
        }

    }

    public String preDesc(Context context, String danyuanbianhao, int fjlxid){
        YFYfRecord yfYfRecord = YFYfRecord.findByDanyuanbianhaoAndDxIdAndFjlxId(danyuanbianhao, this.mRemoteId, fjlxid);
        if (yfYfRecord == null) {
            return "";
        }   else{
            return yfYfRecord.mDesc;
        }

    }
}

