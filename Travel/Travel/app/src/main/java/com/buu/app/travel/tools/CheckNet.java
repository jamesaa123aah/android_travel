package com.buu.app.travel.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by king- on 2017/8/2.
 */

public class CheckNet {
    private Context context;
    public boolean WIFI = false;
    public boolean GPRS = false;
    public CheckNet(Context context) {
        this.context = context;
    }
    public boolean isConnectingToInternet() {
        
        boolean connection;
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                connection = checkState_21_down();
            }else {
                connection = checkState_21_up();
            }
        return connection;
    }
    public boolean checkState_21_down(){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        WIFI = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        GPRS = networkInfo.isConnected();
       if(WIFI||GPRS){
           return true;
       }else {
           return false;
       }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean checkState_21_up(){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks = connMgr.getAllNetworks();
        boolean connection = false;
        for (int i=0; i < networks.length; i++){
            NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
            if(networkInfo.getTypeName().equals("WIFI")&&networkInfo.isConnected()) {
                WIFI = true;
            }
            if(networkInfo.getTypeName().equals("MOBILE")&&networkInfo.isConnected()){
                GPRS = true;
            }
            if(networkInfo.isConnected()){
                connection = true;
            }
        }
        return connection;
    }
}
