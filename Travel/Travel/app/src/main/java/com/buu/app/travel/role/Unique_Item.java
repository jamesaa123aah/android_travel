package com.buu.app.travel.role;

import com.buu.app.travel.Unique_Making;

/**
 * Created by xiaoqiang on 2017-09-15.
 */

public class Unique_Item {
    private String tags;
    private String title;
    private String img;
    private String introduce;
    private int look;
    private int comment;
    private int praise;
    private int star;
    private double money;
    public Unique_Item(String tags,String title, String img, String introduce, int look, int comment, int praise, int star, double money) {
        this.tags = tags;
        this.title = title;
        this.img = img;
        this.introduce = introduce;
        this.look = look;
        this.comment = comment;
        this.praise = praise;
        this.star = star;
        this.money = money;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getLook() {
        return look;
    }

    public void setLook(int look) {
        this.look = look;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}