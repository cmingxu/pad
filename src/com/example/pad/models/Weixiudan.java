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

@Table(name="Weixiudan")
public class Weixiudan extends Model {
    @Column(name="mLougebianhao")
    public String mLougebianhao;


    public static ArrayList<Weixiudan> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Weixiudan> weixiudans = new ArrayList<Weixiudan>();

        for (int i = 0; i < s.length(); i++){
            Weixiudan weixiudan = new Weixiudan();
            temp = s.getJSONObject(i);

            weixiudans.add(weixiudan);

        }
        return weixiudans;
    }

    public static void deleteAll(){
        new Delete().from(Weixiudan.class).where("1=1").execute();
    }

    @Override
    public String toString() {
                    return  "";
    }
}
