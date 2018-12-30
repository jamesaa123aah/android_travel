package com.buu.app.travel.role;

/**
 * Created by xiaoqiang on 2017-09-09.
 */

public class Main_Item {
    private int imgId;
    private String title;
    private int user;
    private int  zan;
    private int imgOff;

    public Main_Item(int imgId, String title, int user, int zan, int imgOff) {
        this.imgId = imgId;
        this.title = title;
        this.user = user;
        this.zan = zan;
        this.imgOff = imgOff;
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

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public int getImgOff() {
        return imgOff;
    }

    public void setImgOff(int imgOff) {
        this.imgOff = imgOff;
    }
}
