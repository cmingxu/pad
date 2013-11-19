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

}

