package com.buu.app.travel.role;

import android.graphics.Bitmap;

/**
 * Created by bei on 2017/9/21.
 */

public class City {
    private Bitmap img;
    private String text;

    public City(Bitmap img, String text) {
        this.img = img;
        this.text = text;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
