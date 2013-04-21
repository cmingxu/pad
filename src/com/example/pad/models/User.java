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
import java.util.List;

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
    public String password;
    @Column(name = "login")
    public String login;
    @Column(name = "remoteId")
    public int remoteId;


    public User(String password, String login, int remoteId) {
        this.password = password;
        this.login = login;
        this.remoteId = remoteId;
    }

    public static ArrayList<User> fromJsonArray(JSONArray s) throws JSONException {
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
                users.add(new User(password, login, remoteId));
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
        return users;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                '}';
    }


    public static void deleteAll(){
        new Delete().from(User.class).where("1=1").execute();
    }

    public static List<User> all(String wherence){
        if (wherence == null) {
            wherence = "1=1";
        }
        List<User> u = new Select().from(User.class).where(wherence).orderBy("id").execute();
        Log.d("ass", u.toString());
        return u;
    }

}
