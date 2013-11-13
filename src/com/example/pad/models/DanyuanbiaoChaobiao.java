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

@Table(name="DanyuanbiaoChaobiao")
public class DanyuanbiaoChaobiao extends Model {
    @Column(name="mRemoteId")
    public int mRemoteID;
    @Column(name="mDanyuanBianhao")
    public String mDanyuanBianhao;

    @Column(name="mBiaomingcheng")
    public String mBiaomingcheng;
    @Column(name="mShangciDushu")
    public String mShangciDushu;
    @Column(name="mBenciDushu")
    public String mBenciDushu;


    public static ArrayList<DanyuanbiaoChaobiao> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<DanyuanbiaoChaobiao> dianyuanbiaochaobiaos = new ArrayList<DanyuanbiaoChaobiao>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            DanyuanbiaoChaobiao dianyuanbiaochaobiao = new DanyuanbiaoChaobiao();
            dianyuanbiaochaobiao.mBenciDushu = temp.optString("本次读数", "");
            dianyuanbiaochaobiao.mBiaomingcheng   = temp.getString("表名称");
            dianyuanbiaochaobiao.mDanyuanBianhao   = temp.getString("单元编号");
            dianyuanbiaochaobiao.mShangciDushu   = temp.getString("上次读数");
            dianyuanbiaochaobiao.mRemoteID   = temp.getInt("ID");

            dianyuanbiaochaobiaos.add(dianyuanbiaochaobiao);

        }
        return dianyuanbiaochaobiaos;
    }

    public static void deleteAll(){
        new Delete().from(DanyuanbiaoChaobiao.class).where("1=1").execute();
    }

    public static List<DanyuanbiaoChaobiao> findByDanyuanbianhao(String danyuanbianhao){
        return new Select().from(DanyuanbiaoChaobiao.class).where("mDanyuanBianhao='" + danyuanbianhao + "'").execute();
    }

    public static DanyuanbiaoChaobiao findByRemoteId(int mRemoteID){
        return new Select().from(DanyuanbiaoChaobiao.class).where("mRemoteId='" + mRemoteID + "'").executeSingle();
    }


    @Override
    public String toString() {
         return  this.mDanyuanBianhao + "/" + this.mBiaomingcheng;
    }
}
