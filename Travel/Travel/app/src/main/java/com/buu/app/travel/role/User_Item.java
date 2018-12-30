package com.buu.app.travel.role;

/**
 * Created by xiaoqiang on 2017-08-31.
 */

public class User_Item {
    public User_Item(String title, int img1, String text1, String content1, int img2, String text2, String content2) {
        this.title = title;
        this.img1 = img1;
        this.img2 = img2;
        this.text1 = text1;
        this.content1 = content1;
        this.text2 = text2;
        this.content2 = content2;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg1() {
        return img1;
    }

    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public int getImg2() {
        return img2;
    }

    public void setImg2(int img2) {
        this.img2 = img2;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    private String title;
    private int img1;
    private int img2;
    private String text1;
    private String content1;
    private String text2;
    private String content2;
}
