package com.buu.app.travel.role;

/**
 * Created by king- on 2017/10/23.
 */

public class Friend {
    private String pUrl;
    private String name;
    private String lastMes;
    private long id;
    private boolean online;
    private boolean travel;
    private int megCount;
    public Friend(String pUrl,String name,String lastMes,long id,int megCount,boolean online,boolean travel){
        this.pUrl = pUrl;
        this.name = name;
        this.lastMes = lastMes;
        this.id = id;
        this.online = online;
        this.travel = travel;
        if(megCount>999){
            megCount = 999;
        }this.megCount = megCount;
    }

    public String getPUrl() {
        return pUrl;
    }

    public void setPUrl(String imagePath) {
        this.pUrl = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMes() {
        return lastMes;
    }

    public void setLastMes(String lastMes) {
        this.lastMes = lastMes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getMegCount() {
        return megCount;
    }

    public boolean isTravel() {
        return travel;
    }

    public void setTravel(boolean travel) {
        this.travel = travel;
    }

    public void setMegCount(int megCount) {
        if(megCount>999){
            megCount = 999;
        }
        this.megCount = megCount;
    }
}
