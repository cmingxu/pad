package com.example.pad.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

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
    Date happenedAt;
    @Column(name="request")
    public String request;
    @Column(name="images")
    public String images;
    @Column(name="type")
    public String type;


    public Date getHappenedAt() {
        return happenedAt;
    }

    public void setHappenedAt(Date happenedAt) {
        this.happenedAt = happenedAt;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

