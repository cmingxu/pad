package com.example.pad.models;

import android.util.Log;
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
 * Time: 8:49 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "User")
public class User extends Model {
    @Column(name = "password")
    private String password;
    @Column(name = "login")
    private String login;
    @Column(name = "remoteId")
    private int remoteId;


    public User(String password, String login, int remoteId) {
        this.password = password;
        this.login = login;
        this.remoteId = remoteId;
    }

    public static ArrayList<User> usersFromJsonArray(JSONArray s) throws JSONException {
        JSONObject temp = null;
        ArrayList<User> users = new ArrayList<User>();

        for (int i = 0; i < s.length(); i++){
            temp = s.getJSONObject(i);
            String login;
            String password;
            int remoteId;
            try {
                Log.d("s", temp.toString());
                login = temp.getString("用户名");
                password = temp.getString("密码");
                remoteId = temp.getInt("id");
                users.add(new User(login, password, remoteId));
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return users;
    }

    public static int count(){
        return 1;
    }

    public static void deleteAll(){
        new Delete().from(User.class).where("1=1").execute();
    }
}
