package com.example.pad.models;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/15/13
 * Time: 8:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class User extends Base {

    public static ArrayList<User> usersFromJsonArray(JSONArray s) throws JSONException {
        String str = s.get(0).toString();
        return null;
    }
}
