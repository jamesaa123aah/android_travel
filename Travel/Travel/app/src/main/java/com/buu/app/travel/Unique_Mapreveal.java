package com.buu.app.travel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.buu.app.travel.role.BaiduMark_Item;
import com.buu.app.travel.tools.LocalJson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.buu.app.travel.Cfg.baidumark;

public class Unique_Mapreveal extends AppCompatActivity {
    private static final String TAG = "Unique_Mapreveal";
    private MapView myMap;
    private BaiduMap myBaiduMap;
    private  boolean isFirst = true;
    private LocationClient locationClient;
    private Button btn_add;
    private Marker marker;//当前点击的Marker
    int chushijibie,chushijibie1;
    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    //覆盖物
    BitmapDescriptor tiantan, tiantanf;
    BitmapDescriptor zrbwg, zrbwgf;
    BitmapDescriptor qinwangfu;
    BitmapDescriptor gugong;
    BitmapDescriptor mcqyz;
    BitmapDescriptor mine;
    Resources resources;
    String[] palace;
    String[] introduce;
    //画线
    List<Polyline> mColorfulPolyline1= new ArrayList<Polyline>();
    List<Marker> Marker1= new ArrayList<Marker>();
    Polyline mColorfulPolyline;
    private List<OverlayOptions> optionses = new ArrayList<OverlayOptions>();
    private List<LatLng> point;

    private Context context;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.unique__mapreveal);
        this.context = this;

        initView();
        chushijibie=myMap.getMapLevel();
        chushijibie1=myMap.getMapLevel();
        resources = this.getResources();
         palace = resources.getStringArray(R.array.palace);
        introduce = resources.getStringArray(R.array.content);
        initMarkDate(0.5f);
        initOptions();
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        locationClient.start();

        //获取json
        jsonObject(LocalJson.getJson("map.json", getApplicationContext()));

        /*
        覆盖物点击事件
        */

        myBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                BaiduMark_Item item = (BaiduMark_Item) bundle.getSerializable("baidumark");

                setMarker(marker);
                switch (item.getTitle()) {
                   case "天坛":
                        break;
                    case "自然博物馆":
                        break;
                    case "亲王府":
                        break;
                    case "故宫":
                        Toast.makeText(context,"hahhhh",Toast.LENGTH_SHORT).show();
                        LatLng tiantan = new LatLng(item.getLatitude(), item.getLongtitude());
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(tiantan).zoom(17.0f);
                        myBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                        break;
                    case "明城墙遗址":
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

//        myBaiduMap.setOnPolylineClickListener(new BaiduMap.OnPolylineClickListener() {
//            @Override
//            public boolean onPolylineClick(Polyline polyline) {
//                 if (polyline == mColorfulPolyline) {
//                    polyline.remove();
//                }
//
//                return false;
//            }
//        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu pop = new PopupMenu(context, btn_add, Gravity.CENTER);
                pop.inflate(R.menu.addmark);
                pop.show();
                pop.setOnMenuItemClickListener(popItemListener);
            }
        });

        myBaiduMap.setOnPolylineClickListener(new BaiduMap.OnPolylineClickListener() {
            @Override
            public boolean onPolylineClick(Polyline polyline) {

                return true;
            }
        });
        myBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }


            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
//                new  AlertDialog.Builder(context)
//
//                        .setTitle("标题" )
//
//                        .setMessage("简单消息框"+1.0f*chushijibie/myMap.getMapLevel())
//
//                        .setPositiveButton("确定" ,  null )
//
//                        .show();

        //        tiantanf = BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.icon_marka_f,5.0f));
//                Resources resources = getResources();
//                String[] palace = resources.getStringArray(R.array.palace);
//                String[] introduce = resources.getStringArray(R.array.content);
//                baidumark.add(new BaiduMark_Item(39.886376, 116.417115, R.drawable.user_tiantan, palace[0], introduce[0], 110, tiantanf));
                if(myMap.getMapLevel()!=chushijibie1) {
                    for (int i = 0; i < 5; i++) {
                        Marker1.get(0).remove();
                        Marker1.remove(0);
                        Cfg.baidumark.remove(0);
                        drawLine();

                    }

                    // initMarkDate(2.0f);
                    float c = (float) ((1.0f * chushijibie / myMap.getMapLevel()) / 2);
                    if (c > 0.5f)
                        c = 0.5f;
                    chushijibie1=myMap.getMapLevel();
               initMarkDate(c);
               addOverly(Cfg.baidumark);
                }
            }
        });
    }

    private void initOptions() {
        LatLng p11 = new LatLng(39.886376, 116.417115);
        LatLng p21 = new LatLng(39.914725, 116.388266);
        LatLng p31 = new LatLng(39.924091, 116.403414);
        LatLng p41 = new LatLng(39.889644, 116.406326);


        LatLng p12 = new LatLng(39.806376, 116.517115);
        LatLng p22 = new LatLng(39.904725, 116.588266);
        LatLng p32 = new LatLng(39.954091, 116.303414);
        LatLng p42 = new LatLng(39.849644, 116.496326);

        point = new ArrayList<LatLng>();
        point.add(p11);
        point.add(p21);
        point.add(p31);
        point.add(p41);

        List<LatLng> point1 = new ArrayList<LatLng>();
        point1.add(p12);
        point1.add(p22);
        point1.add(p32);
        point1.add(p42);

        optionses.add(new PolylineOptions().color(0xAAFF0000).width(15).points(point));
        optionses.add(new PolylineOptions().color(0xAA777777).width(30).points(point1));

    }

    private void drawLine() {
        mColorfulPolyline1.add((Polyline)myBaiduMap.addOverlay(optionses.get(0)));
        mColorfulPolyline1.add((Polyline)myBaiduMap.addOverlay(optionses.get(1)));
        mColorfulPolyline1.get(0).remove();
        mColorfulPolyline1.remove(0);
//        LatLng p11 = new LatLng(39.886376, 116.417115);
//        LatLng p21 = new LatLng(39.914725, 116.388266);
//        LatLng p31 = new LatLng(39.924091, 116.403414);
//        LatLng p41 = new LatLng(39.889644, 116.406326);
//        LatLng p51 = new LatLng(39.906996, 116.439712);
//        List<LatLng> points1 = new ArrayList<LatLng>();
//        points1.add(p11);
//        points1.add(p21);
//        points1.add(p31);
//
//        List<LatLng> point2 = new ArrayList<LatLng>();
//        point2.add(p41);
//        point2.add(p51);
//
//        OverlayOptions ooPolyline1 = new PolylineOptions().width(20)
//                .color(0xAAFF0000).points(points1);
//
//
//        mColorfulPolyline = (Polyline) myBaiduMap.addOverlay(ooPolyline1);

    }


    private void initView() {
        myMap = (MapView) findViewById(R.id.unique_mapreveal_map);
        btn_add = (Button) findViewById(R.id.unique_mapreveal_btn_add);
        myBaiduMap = myMap.getMap();
    }


    PopupMenu.OnMenuItemClickListener popItemListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.addmark_1:
                    addOption();
                    myBaiduMap.addOverlay(optionses.get(optionses.size()-1));
                    break;
            }

            return true;
        }
    };

    //放大缩小
    Bitmap scaleBitmap(int i,float j)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), i);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
//        int newWidth = width/2;
//        int newHeight = height/2;
        float scaleWidth = j;
        float scaleHeight = j;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true);
 return resizedBitmap;

    }
    /*
    覆盖物信息初始化
     */
    private void initMarkDate(float j) {


        tiantanf = BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.icon_marka_f,j));

        tiantan = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
      //  tiantanf.recycle();
       // tiantanf = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka_f);
        zrbwg = BitmapDescriptorFactory.fromResource(R.drawable.icon_markb);
        zrbwgf = BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.icon_markb_f,j));
        qinwangfu = BitmapDescriptorFactory.fromResource(R.drawable.icon_markc);
        gugong = BitmapDescriptorFactory.fromResource(R.drawable.icon_markd);
        mcqyz = BitmapDescriptorFactory.fromResource(R.drawable.icon_marke);
        mine = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);

        baidumark.add(new BaiduMark_Item(39.886376, 116.417115, R.drawable.user_tiantan, palace[0], introduce[0], 110, tiantanf));
        baidumark.add(new BaiduMark_Item(39.889644, 116.406326, R.drawable.user_zrbwg, palace[1], introduce[1], 1180, zrbwgf));
        baidumark.add(new BaiduMark_Item(39.914725, 116.388266, R.drawable.user_qinwnagfu, palace[2], introduce[2], 1210, qinwangfu));
        baidumark.add(new BaiduMark_Item(39.924091, 116.403414, R.drawable.user_gugong, palace[3], introduce[3], 1520, gugong));
        baidumark.add(new BaiduMark_Item(39.906996, 116.439712, R.drawable.user_mcqyz, palace[4], introduce[4], 190, mcqyz));

    }

    /*
    添加覆盖物到地图上
     */

    private void addOverly(List<BaiduMark_Item> baidumark) {

        myBaiduMap.clear();
        LatLng ll = null;
        Marker mark = null;
        MarkerOptions options;


        for (BaiduMark_Item item : baidumark) {
            ll = new LatLng(item.getLatitude(), item.getLongtitude());
            options = new MarkerOptions().position(ll).icon(item.getBd()).zIndex(5)
                    .animateType(MarkerOptions.MarkerAnimateType.grow);
            mark = (Marker) myBaiduMap.addOverlay(options);
            Marker1.add(mark);
            Bundle bundle = new Bundle();
            bundle.putSerializable("baidumark", item);
            mark.setExtraInfo(bundle);
        }

    }


    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            {
                if (bdLocation == null || myMap == null) {
                    return;
                }

                if (isFirst == true) {

                    if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation || bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                        Toast.makeText(getApplicationContext(), "定位成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "请检查网络！", Toast.LENGTH_SHORT).show();
                    }
//                LatLng latLng = new LatLng(50.915,116.404);
//                MapStatusUpdate  msu = MapStatusUpdateFactory.newLatLng(latLng);
//                myBaiduMap.animateMapStatus(msu);
//                msu = MapStatusUpdateFactory.zoomTo(16.0f);
//                myBaiduMap.animateMapStatus(msu);

                    LatLng ll = new LatLng(39.906996, 116.417115);
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(12.0f);
                    myBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    isFirst = false;
                    addOverly(Cfg.baidumark);
                    drawLine();
                }
            }
        }
        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    }


    public void jsonObject(String jsonData) {

        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                Log.d(TAG, "jsonObject: " + jsonObject.getString("name"));
                for (int j = 0; j < Cfg.Plans.size(); j++) {
                    if (Cfg.Plans.get(j).getTourseName().equals(name)) ;
                    Log.d(TAG, "jsonObject: :" + name);
                }
            }
        } catch (Exception o) {
            o.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
    }


    private void addOption() {
        LatLng a1 = new LatLng(39.889644, 116.406326);
        LatLng a2 = new LatLng(39.889644 + 0.05, 116.406326 + 0.05);

        List<LatLng> point = new ArrayList<LatLng>();
        point.add(a1);
        point.add(a2);

        optionses.add(new PolylineOptions().color(0xAABBFFFF).width(15).points(point));

    }



}