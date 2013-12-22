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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/15/13
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "Xunjiandian")
public class Xunjiandian extends Model {
    @Column(name = "mMingcheng")
    public String mMingcheng;
    @Column(name = "mBianhao")
    public String mBianhao;
    @Column(name = "mLoupanBianhao")
    public String mLoupanBianhao;
    @Column(name = "mLougeBianhao")
    public String mLougeBianhao;
    @Column(name = "mLoucengMingcheng")
    public String mLoucengMingcheng;
    @Column(name = "mFangchanQuyu")
    public String mFangchanQuyu;
    @Column(name = "mLeibie")
    public String mLeibie;
    @Column(name = "mYuanLeixing")
    public String mYuanLeixing;
    @Column(name = "mRemoteId")
    public int mRemoteId;
    @Column(name = "mBianma")
    public String mBianma;

    public static ArrayList<Xunjiandian> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Xunjiandian> xunjiandians = new ArrayList<Xunjiandian>();

        for (int i = 0; i < s.length(); i++) {
            temp = s.getJSONObject(i);
            Xunjiandian xunjiandian = new Xunjiandian();
            xunjiandian.mMingcheng = temp.optString("名称");
            xunjiandian.mBianhao = temp.optString("编号");
            xunjiandian.mLoupanBianhao = temp.optString("楼盘编号");
            xunjiandian.mLougeBianhao = temp.optString("楼阁编号");
            xunjiandian.mLoucengMingcheng = temp.optString("楼层名称");
            xunjiandian.mFangchanQuyu = temp.optString("房产区域");
            xunjiandian.mLeibie = temp.optString("类别");
            xunjiandian.mYuanLeixing = temp.optString("源类型");
            xunjiandian.mRemoteId = temp.optInt("id");
            xunjiandian.mBianma = temp.optString("编码");

            xunjiandians.add(xunjiandian);

        }
        return xunjiandians;
    }

    public static void deleteAll() {
        new Delete().from(Xunjiandian.class).where("1=1").execute();
    }

    public static Xunjiandian findByRemoteId(int remoteId) {
        return new Select().from(Xunjiandian.class).where("mRemoteId=" + remoteId).executeSingle();
    }

    public static Xunjiandian findByBianma(String bianma) {
        return new Select().from(Xunjiandian.class).where("mBianma=" + bianma).executeSingle();
    }

    public List<Xunjianxiangmu> xunjianxiangmus() {
        return new Select().from(Xunjianxiangmu.class).where("mXunjiandianId=" + this.mRemoteId).execute();
    }

    public List<Xunjianxiangmu> xunjianxiangmusForXunjiandan(int remoteXunjiandanId) {
        List<Xunjiandanmingxi> xunjiandanmingxis = new Select().from(Xunjiandanmingxi.class).where("mXunjiandanId=" + remoteXunjiandanId).execute();
        Set xunjianxiangmuIds = new HashSet<String>();
        for (Xunjiandanmingxi xunjiandanmingxi : xunjiandanmingxis) {
            xunjianxiangmuIds.add(xunjiandanmingxi.mXiangmuId);
        }
        StringBuilder sb1 = new StringBuilder("");
        for (Xunjiandanmingxi id : xunjiandanmingxis) {
            if (!sb1.toString().equals(""))
                sb1.append(",");
            sb1.append((String) id.mXiangmuId);
        }

        Log.d("", new Select().from(Xunjianxiangmu.class).where("mXunjiandianId=" + this.mRemoteId + " AND mRemoteID IN (" + sb1.toString() + ")").toSql());
        return new Select().from(Xunjianxiangmu.class).where("mXunjiandianId=" + this.mRemoteId + " AND mRemoteID IN (" + sb1.toString() + ")").execute();
    }

    public boolean finish(Xunjiandan xunjiandan) {
        boolean finish = true;
        for (Xunjiandanmingxi xunjiandanmingxi : xunjiandan.xunjiandanmingxis()) {
             for (Xunjianxiangmu xunjianxiangmu : this.xunjianxiangmus()) {
                if (Integer.parseInt(xunjiandanmingxi.mXiangmuId) == xunjianxiangmu.mRemoteID) {
                    if (!xunjiandanmingxi.isFinished()) {
                        finish = false;
                    }
                }
            }
        }

        return finish;
    }

    @Override
    public String toString() {
        return this.mLeibie + "/" + this.mMingcheng;
    }
}
