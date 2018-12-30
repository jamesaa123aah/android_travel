package com.buu.app.travel.role;

/**
 * Created by xiaoqiang on 2017-08-16.
 */

public class Mine_Item {
    private int imgHeader;
    private String text;
    private int imgDetail;

    public Mine_Item(int imgHeader, String text, int imgDetail) {
        this.imgHeader = imgHeader;
        this.text = text;
        this.imgDetail = imgDetail;
    }

    public int getImgHeader() {
        return imgHeader;
    }

    public void setImgHeader(int imgHeader) {
        this.imgHeader = imgHeader;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImgDetail() {
        return imgDetail;
    }

    public void setImgDetail(int imgDetail) {
        this.imgDetail = imgDetail;
    }
}
