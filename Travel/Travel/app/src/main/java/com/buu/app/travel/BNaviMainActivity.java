package com.buu.app.travel;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBEngineInitListener;
import com.baidu.mapapi.bikenavi.adapter.IBRoutePlanListener;
import com.baidu.mapapi.bikenavi.adapter.IBTTSPlayer;
import com.baidu.mapapi.bikenavi.model.BikeRoutePlanError;
import com.baidu.mapapi.bikenavi.params.BikeNaviLauchParam;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.SynthesizerTool;
import com.baidu.tts.client.TtsMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.buu.app.travel.Cfg.baidumark4;

public class BNaviMainActivity extends Activity {
    private TextView textView;

    public  void actionstart(Context context, double end_latlng, double end_longlng,double start_latlng,double start_longlng){
        Intent intent = new Intent(context,BNaviMainActivity.class);
//        intent.putExtra("end_lat",end_latlng);
//        intent.putExtra("end_long",end_longlng);
        this.end_latlng = end_latlng;
        this.end_longlng = end_longlng;
        this.start_latlng = start_latlng;
        this.start_longlng = start_longlng;
        context.startActivity(intent);
    }

    LocationClient client ;
    private static double start_latlng = 0.0;
    private static double start_longlng = 0.0;
    private static double end_latlng = 0.0;
    private  static  double end_longlng = 0.0;
    private BikeNavigateHelper mNaviHelper;
    BikeNaviLauchParam param;
    private static boolean isPermissionRequested = false;


    public MyLocationListenner myListener = new MyLocationListenner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_main);
        requestPermission();
        textView = (TextView) findViewById(R.id.avtivity_guide_main_text);

//        end_latlng = Double.parseDouble(getIntent().getStringExtra("end_lat"));
//        end_longlng = Double.parseDouble(getIntent().getStringExtra("end_long"));
        client = new LocationClient(getApplicationContext());
        client.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        client.setLocOption(option);
        client.start();


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBikeNavi();
            }
        });

        mNaviHelper = BikeNavigateHelper.getInstance();
        Toast.makeText(getApplicationContext(), ""+start_latlng +'\n'+ start_longlng,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),end_latlng+""+'\n'+end_longlng,Toast.LENGTH_SHORT).show();
//        LatLng startPt = new LatLng(40.047788, 116.313261);
//        LatLng endPt = new LatLng(40.056783, 116.308518);

        LatLng startPt = new LatLng(start_latlng,start_longlng);//40.0477887935, 116.3132617935
        LatLng endPt = new LatLng(end_latlng,end_longlng);

        param = new BikeNaviLauchParam().stPt(startPt).endPt(endPt);



    }

    public class MyLocationListenner implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {
            start_latlng = location.getLatitude();
            start_longlng = location.getLongitude();

            if(end_longlng != 0.0 && end_longlng != 0.0){

//                Toast.makeText(getApplicationContext(), ""+location.getLatitude()+'\n'+location.getLongitude(),Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),end_latlng+""+'\n'+end_longlng,Toast.LENGTH_SHORT).show();
                textView.setText(start_latlng+"\n"+start_longlng+"\n"+end_latlng+"\n"+end_longlng);
            }
            }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }


    }

    private void startBikeNavi() {


        Log.d("View", "startBikeNavi");
        mNaviHelper.initNaviEngine(this, new IBEngineInitListener() {
            @Override
            public void engineInitSuccess() {
                Log.d("View", "engineInitSuccess");
                routePlanWithParam();
            }

            @Override
            public void engineInitFail() {
                Log.d("View", "engineInitFail");
            }
        });

    }

    private void routePlanWithParam() {
        mNaviHelper.routePlanWithParams(param, new IBRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                Log.d("View", "onRoutePlanStart");
            }

            @Override
            public void onRoutePlanSuccess() {
                Log.d("View", "onRoutePlanSuccess");

                Intent intent = new Intent();
                intent.setClass(BNaviMainActivity.this, BNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(BikeRoutePlanError error) {
                Log.d("View", "onRoutePlanFail");
            }

        });
    }


    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {

            isPermissionRequested = true;

            ArrayList<String> permissions = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (permissions.size() == 0) {
                return;
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
            }
        }
    }

}
