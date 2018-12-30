package com.buu.app.travel.role;

/**
 * Created by king- on 2017/8/21.
 */

public class Plan {
    private String date;//yyyy/M/D
    private int days;
    private String tourseName;

    public Plan(String date,int days,String tourseName){
        this.date = date;
        this.days = days;
        this.tourseName = tourseName;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTourseName() {
        return tourseName;
    }

    public void setTourseName(String tourseName) {
        this.tourseName = tourseName;
    }
}

