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

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/15/13
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="Syssend")
public class Syssend extends Model {
    @Column(name="sendtime")
    public String sendtime;
    @Column(name="sendperson")
    public String sendperson;
    @Column(name="style")
    public String style;
    @Column(name="content")
    public String content;
    @Column(name="wyid")
    public String wyid;
    @Column(name="ifsend")
    public String ifsend;
    @Column(name="ifck")
    public String ifck;
    @Column(name="iftsfs")
    public String iftsfs;
    @Column(name="ifComplete")
    public String ifComplete;
    @Column(name="remoteId")
    public int remoteId;


    public static ArrayList<Syssend> fromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<Syssend> syssends = new ArrayList<Syssend>();

        for (int i = 0; i < s.length(); i++){
            Syssend syssend = new Syssend();
            temp = s.getJSONObject(i);
            syssend.content = temp.getString("content");
            syssend.ifck    = temp.getString("ifck");
            syssend.ifComplete = temp.getString("ifComplete");
            syssend.ifsend  = temp.getString("ifsend");
            syssend.iftsfs  = temp.getString("iftsfs");
            syssend.sendperson = temp.getString("sendperson");
            syssend.sendtime = temp.getString("sendtime");
            syssend.style = temp.getString("style");
            syssend.wyid  = temp.getString("wyid");
            syssend.remoteId        = temp.getInt("id");
            syssends.add(syssend);

        }
        return syssends;
    }

    public static void deleteAll(){
        new Delete().from(Syssend.class).where("1=1").execute();
    }

    public static int accept_count(){
        return new Select().from(Syssend.class).where("ifck=0").execute().size();
    }


    public static int complete_count(){
        return new Select().from(Syssend.class).where("ifComplete=0").execute().size();
    }

    public static Syssend findByRemoteId(int remoteId){
        return (Syssend)(new Select().from(Syssend.class).where("remoteId= ?", remoteId).executeSingle());
    }

    public static Syssend findById(long id){
        return (Syssend)(new Select().from(Syssend.class).where("id= ?", id).executeSingle());
    }

    @Override
    public String toString() {
        return this.content;

    }
}
