package com.buu.app.travel.role;

import org.litepal.crud.DataSupport;

/**
 * Created by king- on 2017/8/17.
 */

public class User extends DataSupport{
    private int id;
    private long acId;
    private long lastTime;
    private boolean autoLogin;
    private String Account;
    private String Password;

    public int getId() {
        return id;
    }

    public long getAcId() {
        return acId;
    }
    public long getLastTime() {
        return lastTime;
    }
    public boolean isAutoLogin() {
        return autoLogin;
    }

    public String getAccount() {
        return Account;
    }

    public String getPassword() {
        return Password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAcId(long acId) {
        this.acId =acId;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
