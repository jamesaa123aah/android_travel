/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.buu.app.travel;
/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import clusterutil.clustering.Cluster;
import clusterutil.clustering.ClusterItem;
import clusterutil.clustering.ClusterManager;


/**
 * 此Demo用来说明点聚合功能
 */
public class MarkerClusterDemo extends AppCompatActivity implements BaiduMap.OnMapLoadedCallback {

    MapView mMapView;
    BaiduMap mBaiduMap;
    MapStatus ms;
    Context con;
    private ClusterManager<MyItem> mClusterManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_marker_cluster_demo1);
       mMapView = (MapView) findViewById(R.id.bmapView);
        ms = new MapStatus.Builder().target(new LatLng(39.906996, 116.417115)).build();
       mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
//        // 定义点聚合管理类ClusterManager
       mClusterManager = new ClusterManager<MyItem>(getApplicationContext(), mBaiduMap);
//        // 添加Marker点
        addMarkers();

        // 设置地图监听，当地图状态发生改变时，进行点聚合运算

        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        // 设置maker点击时的响应
        mBaiduMap.setOnMarkerClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {

                Toast.makeText(MarkerClusterDemo.this,
                        "有" + cluster.getSize() + "个点", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
                Toast.makeText(MarkerClusterDemo.this,
                        "点击单个Item", Toast.LENGTH_SHORT).show();
                //mClusterManager.clearItems();
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    /**
     * 向地图添加Marker点
     */
    public void addMarkers() {
        // 添加Marker点
        LatLng llA = new LatLng(39.886376, 116.417115);
        LatLng llB = new LatLng(39.889644, 116.406326);
        LatLng llC = new LatLng(39.914725, 116.388266);
        LatLng llD = new LatLng(39.924091, 116.403414);
        LatLng llE = new LatLng(39.906996, 116.439712);
        LatLng llF = new LatLng(39.928091, 116.453414);
        LatLng llG = new LatLng(39.909996, 116.469712);

        List<MyItem> items = new ArrayList<MyItem>();
        items.add(new MyItem(llA));
        items.add(new MyItem(llB));
        items.add(new MyItem(llC));
        items.add(new MyItem(llD));
        items.add(new MyItem(llE));
        items.add(new MyItem(llF));
        items.add(new MyItem(llG));

        mClusterManager.addItems(items);

    }

    public void addMarkers1() {
        // 添加Marker点
        LatLng llA = new LatLng( 39.916798,116.379282);
        LatLng llB = new LatLng(39.940362,116.460556 );
        LatLng llC = new LatLng(39.999298,116.396853 );
        LatLng llD = new LatLng(39.998309,116.185926);
        LatLng llE = new LatLng(39.931826,116.395541 );
        LatLng llF = new LatLng(39.913469,116.44085);
        LatLng llG = new LatLng(39.872999,116.496648);

        List<MyItem> items = new ArrayList<MyItem>();
        items.add(new MyItem(llA));
        items.add(new MyItem(llB));
        items.add(new MyItem(llC));
        items.add(new MyItem(llD));
        items.add(new MyItem(llE));
        items.add(new MyItem(llF));
        items.add(new MyItem(llG));

        mClusterManager.addItems(items);

    }

    /**
     * 每个Marker点，包含Marker点坐标以及图标
     */
    public class MyItem implements ClusterItem {
        private final LatLng mPosition;

        public MyItem(LatLng latLng) {
            mPosition = latLng;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public BitmapDescriptor getBitmapDescriptor() {
            return BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_gcoding);
        }
    }

    @Override
    public void onMapLoaded() {
        // TODO Auto-generated method stub
        ms = new MapStatus.Builder().zoom(13.0f).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.theme_black:

                mClusterManager.clearItems();
                ms = new MapStatus.Builder().zoom(13.0f).build();
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
                addMarkers1();
                break;
            case R.id.theme_light:
                mClusterManager.clearItems();
                ms = new MapStatus.Builder().zoom(13.5f).build();
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
                addMarkers();
                break;
            case R.id.mark_jiudina:
                break;
            case R.id.mark_binguan:
                break;
            default:
                break;
        }
        return true;
    }
}
