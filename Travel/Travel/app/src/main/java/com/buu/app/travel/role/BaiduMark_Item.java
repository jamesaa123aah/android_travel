package com.buu.app.travel.role;

import android.content.res.Resources;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.buu.app.travel.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoqiang on 2017-09-08.
 */

public class BaiduMark_Item implements Serializable{
    private double Latitude;
    private double Longtitude;
    private int imgId;
    private String title;
    private String content;
    private int zan;
    private BitmapDescriptor bd;

    public BaiduMark_Item(double latitude, double longtitude, int imgId, String title, String content, int zan,BitmapDescriptor bd) {
        Latitude = latitude;
        Longtitude = longtitude;
        this.imgId = imgId;
        this.title = title;
        this.content = content;
        this.zan = zan;
        this.bd = bd;

    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongtitude() {
        return Longtitude;
    }

    public void setLongtitude(double longtitude) {
        Longtitude = longtitude;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public BitmapDescriptor getBd() {
        return bd;
    }

    public void setBd(BitmapDescriptor bd) {
        this.bd = bd;
    }
}
