package com.buu.app.travel.role;

import java.io.Serializable;

/**
 * Created by xiaoqiang on 2017-08-28.
 */

public class Theme_Choice implements Serializable {
    private String theme;
    private String time;
    private String relax;
    private String date;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRelax() {
        return relax;
    }

    public void setRelax(String relax) {
        this.relax = relax;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Theme_Choice(String theme, String time, String relax, String date) {
        this.theme = theme;
        this.time = time;
        this.relax = relax;
        this.date = date;
    }



}
