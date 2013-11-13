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

@Table(name="YFYsxm")
public class YFYsxm extends Model {
    @Column(name="mXmmc")
    public String mXmmc;
    @Column(name="mXmbh")
    public String mXmbh;
    @Column(name="mRemoteId")
    public String mRemoteId;

    public static ArrayList<YFYsxm> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<YFYsxm> yf_ysxms = new ArrayList<YFYsxm>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            YFYsxm yf_ysxm = new YFYsxm();
            yf_ysxm.mXmmc = temp.optString("XMMC", "");
            yf_ysxm.mXmbh = temp.optString("XMBH", "");
            yf_ysxm.mRemoteId = temp.optString("id", "");

            yf_ysxms.add(yf_ysxm);

        }
        return yf_ysxms;
    }

    public static void deleteAll(){
        new Delete().from(YFYsxm.class).where("1=1").execute();
    }

    @Override
    public String toString() {
        return  this.mXmmc + "/" + this.mXmbh;
    }
}
