package com.example.pad.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/17/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="CachedRequest")
public class CachedRequest extends Model{
    @Column(name="happenedAt")
    public Date happenedAt;
    @Column(name="request_path")
    public String request_path;
    @Column(name="images")
    public String images;
    @Column(name="resource_type")
    public String resource_type;
    @Column(name="resource_id")
    public long resource_id;
    @Column(name="httpMethod")
    public String httpMethod;


    public static List<CachedRequest> unsavedWeixiudanCachedRequests(){
        return new Select().from(CachedRequest.class).where("resource_type='维修单'").execute();
    }
    public static List<CachedRequest> unsavedJiedanCachedRequests(){
        return new Select().from(CachedRequest.class).where("resource_type='维修单接单'").execute();
    }
    public static List<CachedRequest> unsavedWanchengCachedRequests(){
        return new Select().from(CachedRequest.class).where("resource_type='维修单完成'").execute();
    }
    public static List<CachedRequest> unsavedXunjianCachedRequests(){
        return new Select().from(CachedRequest.class).where("resource_type='巡检单'").execute();
    }
    public static List<CachedRequest> unsavedChaobiaoCachedRequests(){
        return new Select().from(CachedRequest.class).where("resource_type='抄表'").execute();
    }
    public static List<CachedRequest> unsavedYanfangCachedRequests(){
        return new Select().from(CachedRequest.class).where("resource_type='验房'").execute();
    }

    public Weixiudan weixiudan(){
        return new Select().from(Weixiudan.class).where("id=" + this.resource_id).executeSingle();
    }

    public Notice jiedan_notice(){
        return new Select().from(Notice.class).where("id=" + this.resource_id).executeSingle();
    }

    public Notice wancheng_notice(){
        return new Select().from(Notice.class).where("id=" + this.resource_id).executeSingle();
    }

    public DanyuanbiaoChaobiao danyuanbiaoChaobiao(){
        return new Select().from(DanyuanbiaoChaobiao.class).where("id=" + this.resource_id).executeSingle();
    }

    public YFYfRecord yfRecord(){
        return new Select().from(YFYfRecord.class).where("id=" + this.resource_id).executeSingle();
    }

}

