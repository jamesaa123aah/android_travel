package com.buu.app.travel.role;

/**
 * Created by bei on 2017/10/30.
 */

public class Comment_Item {

    private int img;
    private String userName;
    private  String date;
    private String comment;


    public Comment_Item(int img, String userName, String date, String comment) {
        this.img = img;
        this.userName = userName;
        this.date = date;
        this.comment = comment;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
