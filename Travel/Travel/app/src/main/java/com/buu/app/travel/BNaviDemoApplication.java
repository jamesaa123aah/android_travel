/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.buu.app.travel;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;


public class BNaviDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        //SDKInitializer.setCoordType(CoordType.BD09LL);
        SDKInitializer.setCoordType(CoordType.GCJ02);
    }
}
