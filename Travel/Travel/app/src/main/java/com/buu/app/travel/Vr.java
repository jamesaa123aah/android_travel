package com.buu.app.travel;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.buu.app.travel.role.BaiduMark_Item;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.List;

import static com.buu.app.travel.Cfg.baidumark4;

public class Vr extends AppCompatActivity {

    private static final String TAG = "Vr";
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private double testLatitude = 39.9076376;
    private double testLongtitude = 116.406326;
    public Marker make;
    public int icon;

    // UI相关
    Button requestLocButton;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;

    //添加折线
    // 纹理折线，点击时获取折线上点数及width
    Polyline mTexturePolyline;

//    BitmapDescriptor mRedTexture = BitmapDescriptorFactory.fromResource(R.drawable.icon_road_red_arrow);
//    BitmapDescriptor mBlueTexture = BitmapDescriptorFactory.fromResource(R.drawable.icon_road_blue_arrow);
//    BitmapDescriptor mGreenTexture = BitmapDescriptorFactory.fromResource(R.drawable.icon_road_green_arrow);

    MapView mMapView;
    BaiduMap mBaiduMap;
    private RelativeLayout mMarkerLayout;
    BitmapDescriptor tiantan,tiantanf;
    BitmapDescriptor zrbwg,zrbwgf ;
    BitmapDescriptor qinwangfu;
    BitmapDescriptor gugong;
    BitmapDescriptor mcqyz;
    BitmapDescriptor mine;


    private TextView tv;
    private String result = "";

    public Marker getMake() {
        return make;
    }

    public void setMake(Marker make) {
        this.make = make;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.main_vr);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;


        // 地图初始化
        mMapView = (MapView) findViewById(R.id.main_vr_mapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        initView();
        initMarkDate();


        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                InfoWindow window;
                marker.getIcon();
                setMake(marker);
                Bundle bundle = marker.getExtraInfo();
                BaiduMark_Item item = (BaiduMark_Item) bundle.getSerializable("baidumark");

                ImageView img = (ImageView) findViewById(R.id.mark_img);
                TextView title = (TextView) findViewById(R.id.mark_name);
                TextView content = (TextView) findViewById(R.id.mark_content);
                TextView zan = (TextView) findViewById(R.id.mark_zan);

                img.setImageResource(item.getImgId());
                title.setText(item.getTitle());
                content.setText(item.getContent());
                zan.setText(item.getZan() + "");

                tv = new TextView(getApplicationContext());
                tv.setText("点亮"+item.getTitle());
                tv.setTextColor(Color.argb(50, 255, 0, 255));
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(30, 20, 30, 50);
                tv.setBackgroundResource(R.drawable.popup);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(Vr.this, CaptureActivity.class), 0);
                    }
                });

                final LatLng latLng = marker.getPosition();
                window = new InfoWindow(tv, latLng, -47);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(item.getLatitude(), item.getLongtitude()));

                mBaiduMap.setMapStatus(msu);
                mBaiduMap.showInfoWindow(window);
                mMarkerLayout.setVisibility(View.VISIBLE);

                return true;
            }
        });


        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiduMap.hideInfoWindow();
                mMarkerLayout.setVisibility(View.GONE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }


    private void initMarkDate() {

        tiantan = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        tiantanf = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka_f);
        zrbwg = BitmapDescriptorFactory.fromResource(R.drawable.icon_markb);
        zrbwgf = BitmapDescriptorFactory.fromResource(R.drawable.icon_markb_f);


        Resources resources = getResources();
        String[] palace = resources.getStringArray(R.array.palace);
        String[] introduce = resources.getStringArray(R.array.content);
        baidumark4.add(new BaiduMark_Item(39.886376, 116.417115, R.drawable.user_tiantan, palace[0], introduce[0], 110, tiantanf));
        baidumark4.add(new BaiduMark_Item(39.889644, 116.406326, R.drawable.user_zrbwg, palace[1], introduce[1], 1180, zrbwgf));
        baidumark4.add(new BaiduMark_Item(39.914725, 116.388266, R.drawable.user_qinwnagfu, palace[2], introduce[2], 1210, tiantanf));
        baidumark4.add(new BaiduMark_Item(39.924091, 116.403414, R.drawable.user_gugong, palace[3], introduce[3], 1520, zrbwgf));
        baidumark4.add(new BaiduMark_Item(39.906996, 116.439712, R.drawable.user_mcqyz, palace[4], introduce[4], 190, tiantanf));

    }

    private void initView() {
        mMarkerLayout = (RelativeLayout) findViewById(R.id.mark_layout);

    }
    public class MyLocationListenner implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {
//            Toast.makeText(getApplicationContext(), ""+location.getLatitude()+'\n'+location.getLongitude(),Toast.LENGTH_SHORT).show();
            location.setLatitude(testLatitude);
            location.setLongitude(testLongtitude);

            mCurrentLat = testLatitude;
            mCurrentLon = testLongtitude;
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                if (location.getLocType() == BDLocation.TypeGpsLocation ||
                        location.getLocType() == BDLocation.TypeNetWorkLocation) {
                } else {
                    Toast.makeText(getApplicationContext(), "请检查网络！", Toast.LENGTH_SHORT).show();
                }
                isFirstLoc = false;
                LatLng ll = new LatLng(testLatitude, testLongtitude);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(14.5f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                //覆盖物添加
//                initBitMap();

                addOverly(baidumark4);

            }


        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }


        private void addOverly(List<BaiduMark_Item> baidumark) {

            mBaiduMap.clear();
            LatLng ll = null;
            Marker mark = null;
            MarkerOptions options;


            for (BaiduMark_Item item : baidumark) {
                ll = new LatLng(item.getLatitude(), item.getLongtitude());
                options = new MarkerOptions().position(ll).icon(item.getBd()).zIndex(5)
                        .animateType(MarkerOptions.MarkerAnimateType.drop);
                mark = (Marker) mBaiduMap.addOverlay(options);
                Bundle bundle = new Bundle();
                bundle.putSerializable("baidumark", item);
                mark.setExtraInfo(bundle);
            }

        }


    }
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (result.equals("匹配成功")) {
            if(getMake().getIcon() == tiantanf){
                getMake().setIcon(tiantan);
            }
            else if(getMake().getIcon() == zrbwgf){
                getMake().setIcon(zrbwg);
            }
        }
        result = "";

    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
        ;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            result = bundle.getString("result");
            tv.setText(result);
        }
    }
}
