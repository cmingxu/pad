package com.example.pad.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.pad.AppContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/17/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="Notice")
public class Notice extends Model{
    @Column(name="sendTime")
    public String sendTime;
    @Column(name="sendPerson")
    public String sendPerson;
    @Column(name="danjuLeixing")
    public String danjuLeixing;
    @Column(name="danjuId")
    public String danjuId;
    @Column(name="danjuBiaoti")
    public String danjuBiaoti;
    @Column(name="danjuNeirong")
    public String danjuNeirong;
    @Column(name="remoteId")
    public int remoteId;
    @Column(name="isAccept")
    public boolean isAccept;
    @Column(name="isComplete")
    public boolean isComplete;
    @Column(name="isDaixiu")
    public boolean isDaixiu;


    public static ArrayList<Notice> fromJsonArray(JSONArray s, AppContext context) throws JSONException {
        JSONObject temp = null;
        ArrayList<Notice> notices = new ArrayList<Notice>();

        for (int i = 0; i < s.length(); i++){
            Notice notice = new Notice();
            temp = s.getJSONObject(i);
            notice.danjuBiaoti = temp.getString("单据标题");
            notice.danjuId     = temp.getString("单据id");
            notice.danjuLeixing = temp.getString("单据类型");
            notice.danjuNeirong = temp.getString("单据内容");
            notice.remoteId     = temp.getInt("id");
            notice.isAccept = false;
            notice.isComplete = false;
            notice.isDaixiu = false;
            notices.add(notice);

        }
        return notices;
    }

    public static void deleteAll(){
        new Delete().from(Notice.class).where("1=1").execute();
    }

    public static int accept_count(String jsr){
        return new Select().from(Notice.class).where("isAccept=0 and jsr=?", jsr).execute().size();
    }

    public void accept(){
        this.isAccept = true;
        this.save();
    }

    public void complete(){
        this.isComplete = true;
        this.save();
    }
    public void daixiu(){
        this.isDaixiu = true;
        this.save();
    }

    public static List<Notice> allComplete(String jsr){
        return new Select().from(Notice.class).where("(isAccept = 1 and isComplete=0 and isDaixiu=0) and jsr=?", jsr).execute();
    }

    public static Notice findByRemoteId(int remoteId){
        return (Notice)(new Select().from(Notice.class).where("remoteId= ?", remoteId).executeSingle());
    }

    public static Notice findById(long id){
        return (Notice)(new Select().from(Notice.class).where("id= ?", id).executeSingle());
    }

    @Override
    public String toString() {
        return this.danjuNeirong;
    }

}

