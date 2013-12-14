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

@Table(name="Xunjianxiangmu")
public class Xunjianxiangmu extends Model {
    @Column(name="mXunjiandianId")
    public String mXunjiandianId;
    @Column(name="mMingcheng")
    public String mMingcheng;
    @Column(name="mBiaozhun")
    public String mBiaozhun;
    @Column(name="mShuoming")
    public String mShuoming;
    @Column(name="mRemoteID")
    public int mRemoteID;





    public static ArrayList<Xunjianxiangmu> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Xunjianxiangmu> xunjianxiangmus = new ArrayList<Xunjianxiangmu>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            Xunjianxiangmu xunjianxiangmu = new Xunjianxiangmu();
            xunjianxiangmu.mXunjiandianId = temp.optString("巡检点id", "");
            xunjianxiangmu.mMingcheng   = temp.getString("名称");
            xunjianxiangmu.mBiaozhun   = temp.getString("标准");
            xunjianxiangmu.mShuoming   = temp.getString("说明");
            xunjianxiangmu.mRemoteID  = temp.optInt("id");

            xunjianxiangmus.add(xunjianxiangmu);

        }
        return xunjianxiangmus;
    }

    public static void deleteAll(){
        new Delete().from(Xunjianxiangmu.class).where("1=1").execute();
    }


    public static Xunjianxiangmu findByRemoteId(int remoteId){
        return new Select().from(Xunjianxiangmu.class).where("mRemoteId=" + remoteId).executeSingle();
    }

    public Xunjiandian xunjiandian(){
        return Xunjiandian.findByRemoteId(Integer.parseInt(this.mXunjiandianId));
    }

    public List<Xunjianzhi> xunjianzhis(){
        return new Select().from(Xunjianzhi.class).where("mXiangmuId=" + this.mRemoteID).orderBy("mShunxu").execute();
    }

    public boolean isFinish(Xunjiandan xunjiandan){
        Xunjiandanmingxi xunjiandanmingxi = Xunjiandanmingxi.findByRemoteXunjiandanidAndRemoteXunjianxiangmuid(xunjiandan.mRemoteID, this.mRemoteID);
        return xunjiandanmingxi.isFinished();
    }

    @Override
    public String toString() {
        return "";
    }
}
