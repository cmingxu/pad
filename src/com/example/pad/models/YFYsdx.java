package com.example.pad.models;

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

@Table(name="YFYsdx")
public class YFYsdx extends Model {
    @Column(name="mDxmc")
    public String mDxmc;
    @Column(name="mDxbh")
    public String mDxbh;
    @Column(name="mRemoteId")
    public String mRemoteId;

    public static ArrayList<YFYsdx> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<YFYsdx> yf_ysdxs = new ArrayList<YFYsdx>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            YFYsdx yf_ysdx = new YFYsdx();
            yf_ysdx.mDxmc = temp.optString("DXMC", "");
            yf_ysdx.mDxbh = temp.optString("DXBH", "");
            yf_ysdx.mRemoteId = temp.optString("id", "");

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
}
