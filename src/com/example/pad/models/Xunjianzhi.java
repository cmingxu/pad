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

@Table(name="Xunjianzhi")
public class Xunjianzhi extends Model {
    @Column(name="mXiangmuId")
    public String mXiangmuId;
    @Column(name="mShunxu")
    public String mShunxu;
    @Column(name="mBiaoshi")
    public String mBiaoshi;
    @Column(name="mZhi")
    public String mZhi;
    @Column(name="mShifouMoren")
    public boolean mShifouMoren;
    @Column(name="mShifouZhenggai")
    public boolean mShifouZhenggai;
    @Column(name="mRemoteID")
    public int mRemoteID;
   





    public static ArrayList<Xunjianzhi> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Xunjianzhi> xunjianzhis = new ArrayList<Xunjianzhi>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            Xunjianzhi xunjianzhi = new Xunjianzhi();
            xunjianzhi.mXiangmuId = temp.optString("项目id", "");
            xunjianzhi.mShunxu   = temp.getString("顺序");
            xunjianzhi.mBiaoshi   = temp.getString("标识");
            xunjianzhi.mZhi   = temp.getString("值");
            xunjianzhi.mShifouMoren   = temp.getBoolean("是否默认");
            xunjianzhi.mShifouZhenggai   = temp.getBoolean("是否整改");
            xunjianzhi.mRemoteID = temp.optInt("id");

            xunjianzhis.add(xunjianzhi);

        }
        return xunjianzhis;
    }

    public static void deleteAll(){
        new Delete().from(Xunjianzhi.class).where("1=1").execute();
    }

    public static Xunjianzhi findByRemoteId(int remoteID){
        return new Select().from(Xunjianzhi.class).where("mRemoteID=" + remoteID).executeSingle();
    }


    @Override
    public String toString() {
return "";
    }
}
