package com.buu.app.travel.role;

/**
 * Created by bei on 2017/11/22.
 */

public class Msg1 {

    public static final int TYPE_LEFT = 0;
    public static final int TYPE_RIGHT = 1;
    private String msg;
    private int type;

    public Msg1(String msg, int type) {
        this.msg = msg;
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public int getType() {
        return type;
    }
}
