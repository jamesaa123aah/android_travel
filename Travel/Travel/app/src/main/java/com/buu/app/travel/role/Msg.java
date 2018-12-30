package com.buu.app.travel.role;

import android.graphics.Bitmap;

/**
 * Created by king- on 2017/4/30.
 */

public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND =1;
    private String content;
    private int type;
    private boolean isImg;
    private Bitmap bitmap;
    public Msg(String content,int type,boolean isImg){
        this.content = content;
        this.type = type;
        this.isImg = isImg;
    }
    public boolean isImg(){
        return isImg;
    }
    public String getContent(){
        return content;
    }
    public int getType(){
        return  type;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
