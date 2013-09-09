package com.example.pad.models;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: xcm
 * Date: 5/18/13
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddressChooserResult implements Serializable {
    public long mLoupanId;
    public String mLoupanName;
    public long mLougeId;
    public String mLougeName;
    public long mLoucengId;
    public String mLoucengName;
    public long mDanyuanId;
    public String mDanyuanName;
    public String mYezhuName;
    public String mYezhuDianhua;
    public String mDanyuanbianhao;
    public String mLougebianhao;
    public String mLoupanbianhao;

    public String getmLoupanbianhao() {
        return mLoupanbianhao;
    }

    public void setmLoupanbianhao(String mLoupanbianhao) {
        this.mLoupanbianhao = mLoupanbianhao;
    }

    public long getmLoupanId() {
        return mLoupanId;
    }

    public void setmLoupanId(long mLoupanId) {
        this.mLoupanId = mLoupanId;
    }

    public String getmLoupanName() {
        return mLoupanName;
    }

    public void setmLoupanName(String mLoupanName) {
        this.mLoupanName = mLoupanName;
    }

    public String mLoucengbianhao;
    public String mZhuhuBianhao;

    public String getmZhuhuBianhao() {
        return mZhuhuBianhao;
    }

    public void setmZhuhuBianhao(String mZhuhuBianhao) {
        this.mZhuhuBianhao = mZhuhuBianhao;
    }

    public long getmLougeId() {
        return mLougeId;
    }

    public void setmLougeId(long mLougeId) {
        this.mLougeId = mLougeId;
    }

    public String getmLougeName() {
        return mLougeName;
    }

    public void setmLougeName(String mLougeName) {
        this.mLougeName = mLougeName;
    }

    public long getmLoucengId() {
        return mLoucengId;
    }

    public void setmLoucengId(long mLoucengId) {
        this.mLoucengId = mLoucengId;
    }

    public String getmLoucengName() {
        return mLoucengName;
    }

    public void setmLoucengName(String mLoucengName) {
        this.mLoucengName = mLoucengName;
    }

    public long getmDanyuanId() {
        return mDanyuanId;
    }

    public void setmDanyuanId(long mDanyuanId) {
        this.mDanyuanId = mDanyuanId;
    }

    public String getmDanyuanName() {
        return mDanyuanName;
    }

    public void setmDanyuanName(String mDanyuanName) {
        this.mDanyuanName = mDanyuanName;
    }

    public String getmYezhuName() {
        return mYezhuName;
    }

    public void setmYezhuName(String mYezhuName) {
        this.mYezhuName = mYezhuName;
    }

    public String getmYezhuDianhua() {
        return mYezhuDianhua;
    }

    public void setmYezhuDianhua(String mYezhuDianhua) {
        this.mYezhuDianhua = mYezhuDianhua;
    }

    public String getmDanyuanbianhao() {
        return mDanyuanbianhao;
    }

    public void setmDanyuanbianhao(String mDanyuanbianhao) {
        this.mDanyuanbianhao = mDanyuanbianhao;
    }

    public String getmLougebianhao() {
        return mLougebianhao;
    }

    public void setmLougebianhao(String mLougebianhao) {
        this.mLougebianhao = mLougebianhao;
    }

    public String getmLoucengbianhao() {
        return mLoucengbianhao;
    }

    public void setmLoucengbianhao(String mLoucengbianhao) {
        this.mLoucengbianhao = mLoucengbianhao;
    }

    @Override
    public String toString() {
        return this.getmDanyuanbianhao() + this.getmDanyuanId() + this.getmDanyuanName() +
                this.getmLoucengbianhao() + this.getmLoucengName() + "  " + this.getmZhuhuBianhao();

    }
}
