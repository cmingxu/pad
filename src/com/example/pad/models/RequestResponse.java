package com.example.pad.models;

import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 11/19/13
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestResponse {
    public String result;
    public String message;

    public RequestResponse(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public RequestResponse(JSONObject jsonObject) {
        this.result = jsonObject.optString("result");
        this.message = jsonObject.optString("message");
    }

    public boolean ok(){
        return this.result.equals("true");
    }

}
