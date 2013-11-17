package com.example.pad.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 4/15/13
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */

@Table(name="YFYfRecord")
public class YFYfRecord extends Model {
    @Column(name="mDanyuanbianhao")
    public String mDanyuanbianhao;
    @Column(name="mDxID")
    public int mDxID;
    @Column(name="mFjlxID")
    public int mFjlxID;
    @Column(name="mDesc")
    public String mDesc;
    @Column(name="mImageDir")
    public String mImageDir;


    public static void deleteAll(){
        new Delete().from(YFYfRecord.class).where("1=1").execute();
    }

    @Override
    public String toString() {
        return null;
    }

    public static YFYfRecord findById(long id){
        return new Select().from(YFYfRecord.class).where("id=" + id).executeSingle();
    }

    public static YFYfRecord findByDanyuanbianhaoAndDxIdAndFjlxId(String danyuanbianhao, int duixiangId, int fjlxId){
        return new Select().from(YFYfRecord.class).where("mDanyuanbianhao='" + danyuanbianhao + "' AND mDxID=" + duixiangId + " AND mFjlxID=" + fjlxId).executeSingle();
    }

    public static long last_id(){
        YFYfRecord last = new Select().from(YFYfRecord.class).orderBy("id DESC").executeSingle();
        if (last != null){
            return last.getId() + 1;
        }

        return 0l;

    }

    public String imagesInStr(){
        if (this.mImageDir == null) {
            return  "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        File dir = new File(this.mImageDir);
        if(dir.exists() && dir.isDirectory()){
            if (dir.list().length == 0){
                return "";
            }
            for (File file : dir.listFiles()) {
                stringBuilder.append(file.getAbsoluteFile());
                stringBuilder.append(":");
            }
        }else{
            return "";
        }
        String s = stringBuilder.toString();
        return s.substring(0, s.length() - 2);
    }

}
