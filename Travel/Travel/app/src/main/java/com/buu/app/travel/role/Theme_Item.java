package com.buu.app.travel.role;

/**
 * Created by xiaoqiang on 2017-08-11.
 */

public class Theme_Item {
    private String title;
    private int imageId;

    public Theme_Item(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
