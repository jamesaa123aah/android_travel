package com.buu.app.travel.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.buu.app.travel.BNaviMainActivity;
import com.buu.app.travel.Cfg;
import com.buu.app.travel.R;
import com.buu.app.travel.role.BaiduMark_Item;
import com.buu.app.travel.role.mColorfulPolylineint;
import com.buu.app.travel.tools.DynamicChangeView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import clusterutil.clustering.ClusterItem;
import clusterutil.clustering.ClusterManager;

import static com.baidu.mapapi.map.MapView.setMapCustomEnable;
import static com.buu.app.travel.Cfg.baidumark;
import static com.buu.app.travel.Cfg.baidumark1;


/**
 * Created by xiaoqiang on 2017-06-25.
 */

public class Schedule_Frag extends Fragment  implements  View.OnClickListener{

    private static final String TAG = "Unique_Mapreveal";
    private MapView myMap;
    private BaiduMap myBaiduMap;
    private  boolean isFirst = true;
    private LocationClient locationClient;
    private Button btn_add;
    private Button btn_bettter;
    private  Button btn_delete;
    private Marker marker;//当前点击的Marker
    int chushijibie,chushijibie1;

    private double start_lat = 0.0;
    private double start_long = 0.0;
    private Button btn_back;
    private PopupWindow popupWindow;
    private View mainView;

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

    BitmapDescriptor mark_eat;
    BitmapDescriptor mark_hospital;
    Resources resources;
    String[] palace;
    String[] introduce;
    //画线
    List<mColorfulPolylineint> mColorfulPolyline2= new ArrayList<mColorfulPolylineint>();
    List<Polyline> mColorfulPolyline1= new ArrayList<Polyline>();
    List<Marker> Marker1= new ArrayList<Marker>();
    List<Marker> Markere2 = new ArrayList<Marker>();
    private List<OverlayOptions> optionses = new ArrayList<OverlayOptions>();
    private List<OverlayOptions> optionsesMark = new ArrayList<OverlayOptions>();
    private List<LatLng> point;
    private List<LatLng> point1;
    private List<LatLng> point2;

    private Context mContext;
    private View view;
    private RadioButton lineone;
    private RadioButton linetwo;
    private RadioButton linethree;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout_dingdan;
    private ScrollView scrollView;
    private GridLayout gridLayout;
    DynamicChangeView dynamicChangeView;
    View container1;
    View container2;
    View container3;
    View popWindow;
    LinearLayout linearLayout_item;
    private Boolean jindain1_add = false;
    private RelativeLayout mMarkerLayout;
    private  TextView tv;
    private Button cateen,inner,traffic,play,supermarker,toliet,delete,hospital;
    private TextView textView_cancle;

    MapStatus ms;
    private ClusterManager<MyItem> mClusterManager;
    InputStream inputStream =null;
//    private String clickTime;

    /**
     * @param inflater
     * @param container
     * stanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.making_line, null);
        popWindow = LayoutInflater.from(getActivity()).inflate(R.layout.pop_window, null);
        mContext = getActivity();
        dynamicChangeView = new DynamicChangeView(getActivity());

//        container1.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.arise));
        container1 = LayoutInflater.from(getActivity()).inflate(R.layout.linearlayout_item, null);
        container2 = LayoutInflater.from(getActivity()).inflate(R.layout.linearlayout_item1, null);
        container3 = LayoutInflater.from(getActivity()).inflate(R.layout.linearlayout_item2,null);
        popWindow.findViewById(R.id.pop_window_sure).setOnClickListener(this);
        initView();
        initOptions();

//        myMap.setCustomMapStylePath("/asserts/mapstyle.json");


        try{
            inputStream = mContext.getAssets().open("mapstyle.json");
        }
        catch (Exception o){
            o.printStackTrace();
        }
        setMapCustomEnable(true);
//        myBaiduMap.setTrafficEnabled(true);
        locationClient = new LocationClient(getContext());
        locationClient.registerLocationListener(new MyLocationListener());
        locationClient.start();


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu pop = new PopupMenu(mContext, btn_add, Gravity.CENTER);
                pop.inflate(R.menu.addmark);
                pop.show();
                pop.setOnMenuItemClickListener(popItemListener);
            }
        });

        btn_bettter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(point.size()>4){
                popupWindow = new PopupWindow(mContext);
                popupWindow.setContentView(popWindow);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                mainView = LayoutInflater.from(getActivity()).inflate(R.layout.making_line,null);
                popupWindow.showAtLocation(mainView,Gravity.BOTTOM,0,0);}
                else{
                    Toast.makeText(getActivity(),"线路已优化！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity()," ",Toast.LENGTH_SHORT).show();

            }
        });

        textView_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout_dingdan.setVisibility(View.GONE);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout_dingdan.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"订单支付成功！请在个人中心查看",Toast.LENGTH_SHORT).show();
            }
        });
        myBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent m) {
                if(m.getAction() == MotionEvent.ACTION_DOWN){
                    scrollView.requestDisallowInterceptTouchEvent(false);
                }
                else{
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
            }
        });

        myBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {

                marker.getIcon();
                Bundle bundle = marker.getExtraInfo();
                if(bundle != null) {
                    BaiduMark_Item item = (BaiduMark_Item) bundle.getSerializable("baidumark");

                ImageView img = (ImageView) view.findViewById(R.id.mark_img);
                TextView title = (TextView) view.findViewById(R.id.mark_name);
                TextView content = (TextView) view.findViewById(R.id.mark_content);
                TextView zan = (TextView) view.findViewById(R.id.mark_zan);

                img.setImageResource(item.getImgId());
                title.setText(item.getTitle());
                content.setText(item.getContent());
                zan.setText(item.getZan() + "");


                final LatLng latLng = marker.getPosition();
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(item.getLatitude(), item.getLongtitude()));

                myBaiduMap.setMapStatus(msu);
                mMarkerLayout.setVisibility(View.VISIBLE);
                gridLayout.setVisibility(View.VISIBLE);

                linearLayout.setVisibility(View.GONE);}

                else{
                    InfoWindow window;
                    tv = new TextView(mContext);
                    tv.setText("预订此酒店！长按导航！");
                    tv.setGravity(Gravity.CENTER);
                    tv.setPadding(30,20,30,50);
                    tv.setBackgroundResource(R.drawable.popup);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            linearLayout_dingdan.setVisibility(View.VISIBLE);
                        }
                    });

                    tv.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            new BNaviMainActivity().actionstart(mContext,marker.getPosition().latitude,marker.getPosition().longitude,start_lat,start_long);
                            return false;
                        }
                    });


                    final LatLng latLng = marker.getPosition();
                    window = new InfoWindow(tv, latLng, -47);

                    myBaiduMap.showInfoWindow(window);

                }
                return true;
            }
        });

        myBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                myBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });


        return view;

    }


    PopupMenu.OnMenuItemClickListener popItemListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            if (jindain1_add == true) {
                Toast.makeText(mContext, "已经添加该行程！", Toast.LENGTH_SHORT).show();
            } else {
                switch (item.getItemId()) {
                    case R.id.addmark_1:
                        LatLng ll = new LatLng(39.924091, 116.453414);
                        point.add(ll);
                        optionses.set(0, new PolylineOptions().color(0xAAFF0000).width(20).points(point));
//                    mColorfulPolyline1.set(0,(Polyline)myBaiduMap.addOverlay(optionses.get(0)));

                        mColorfulPolyline1.get(0).remove();
                        mColorfulPolyline1.remove(0);
                        mColorfulPolyline1.add(0, (Polyline) myBaiduMap.addOverlay(optionses.get(0)));

                        OverlayOptions options = new MarkerOptions().position(ll).icon(BitmapDescriptorFactory
                                .fromBitmap(scaleBitmap(R.drawable.icon_marke, 0.5f))).zIndex(5)
                                .animateType(MarkerOptions.MarkerAnimateType.grow);
                        Marker mark = (Marker) myBaiduMap.addOverlay(options);
                        Marker1.add(mark);
                        jindain1_add = true;

                        break;
                    case R.id.addmark_2:
                        break;
                    case R.id.addmark_3:
                        break;
                    default:
                        break;
                }
            }
                return false;
            }

    };

    private void initView() {

        btn_back = (Button)view.findViewById(R.id.making_line_btn);
        linearLayout = (LinearLayout)view.findViewById(R.id.making_line_linearlayout);
        linearLayout_item = (LinearLayout)view.findViewById(R.id.linearlayout_item);
        linearLayout_dingdan = (LinearLayout)view.findViewById(R.id.making_line_dingdan);
        textView_cancle  = (TextView)view.findViewById(R.id.making_line_cancle);
        gridLayout = (GridLayout)view.findViewById(R.id.making_line_linear_function);
        scrollView = (ScrollView)view.findViewById(R.id.making_line_scrollview);
        mMarkerLayout = (RelativeLayout)view.findViewById(R.id.mark_layout) ;
        myMap = (MapView)view. findViewById(R.id.making_line_map);
        btn_add = (Button) view.findViewById(R.id.making_line_add);
        btn_bettter = (Button)view.findViewById(R.id.making_line_better);
        btn_delete = (Button)view.findViewById(R.id.making_line_delete);
        lineone = (RadioButton)view.findViewById(R.id.making_line_lineone);
        linetwo = (RadioButton)view.findViewById(R.id.making_line_linetwo);
        linethree = (RadioButton) view.findViewById(R.id.making_line_linethree);
        lineone.setOnClickListener(this);
        linetwo.setOnClickListener(this);
        linethree.setOnClickListener(this);
        cateen = (Button)view.findViewById(R.id.making_line_canteen);
        inner = (Button)view.findViewById(R.id.making_line_inner);
        traffic = (Button)view.findViewById(R.id.making_line_traffic);
        play = (Button)view.findViewById(R.id.making_line_play);
        supermarker = (Button)view.findViewById(R.id.making_line_supermarket);
        toliet = (Button)view.findViewById(R.id.making_line_toliet);
        hospital = (Button)view.findViewById(R.id.making_line_hospital);
        delete = (Button)view.findViewById(R.id.making_line_delete);

        cateen.setOnClickListener(this);
        inner.setOnClickListener(this);
        traffic.setOnClickListener(this);
        play.setOnClickListener(this);
        supermarker.setOnClickListener(this);
        toliet.setOnClickListener(this);
        hospital.setOnClickListener(this);
        delete.setOnClickListener(this);


        myBaiduMap = myMap.getMap();
        chushijibie=myMap.getMapLevel();
        chushijibie1=myMap.getMapLevel();
        resources = this.getResources();
        palace = resources.getStringArray(R.array.palace);
        introduce = resources.getStringArray(R.array.content);
        initMarkDate(0.5f);
        dynamicChangeView.AddView(linearLayout,container1,lineone);

        myBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<>(mContext, myBaiduMap);

    }

    private void initOptions() {

        LatLng p11 = new LatLng(39.889644, 116.406326);
        LatLng p21 = new LatLng(39.914725, 116.388266);
        LatLng p31 = new LatLng(39.906996, 116.439712);
        LatLng p41 = new LatLng(39.886376, 116.453414);


        LatLng p12 = new LatLng(39.849644, 116.436326);
        LatLng p22 = new LatLng(39.904725, 116.496326);
        LatLng p32 = new LatLng(39.886376, 116.417115);
        LatLng p42 = new LatLng(39.924091, 116.403414);

        point2 = new ArrayList<LatLng>();
        LatLng p13 = new LatLng(39.886376, 116.358266);
        LatLng p23 = new LatLng(39.924091, 116.415115);

        point = new ArrayList<LatLng>();
        point.add(p11);
        point.add(p21);
        point.add(p31);
        point.add(p41);

        point1 = new ArrayList<LatLng>();
        point1.add(p12);
        point1.add(p22);
        point1.add(p32);
        point1.add(p42);

       point2.add(p13);
        point2.add(p23);

        optionses.add(new PolylineOptions().color(0xAAFF0000).width(20).points(point));
        optionses.add(new PolylineOptions().color(0xAA0111ed).width(10).points(point1));
        optionses.add(new PolylineOptions().color(0xffff8800).width(10).points(point2));

    }


    private void initMarkDate(float j) {
        tiantan = BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.icon_marka,j));
        zrbwg = BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.icon_markb,j));
        qinwangfu = BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.icon_markc,j));
        gugong = BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.icon_markd,j));
        mcqyz = BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.icon_marke,j));
        mine = BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.icon_gcoding,j));
        mark_eat = BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.eat,j));
        mark_hospital = BitmapDescriptorFactory.fromBitmap(scaleBitmap(R.drawable.hospital1,j));

        baidumark.add(new BaiduMark_Item(39.889644, 116.406326, R.drawable.user_tiantan, palace[0], introduce[0], 110, tiantan));
        baidumark.add(new BaiduMark_Item(39.914725, 116.388266, R.drawable.user_zrbwg, palace[1], introduce[1], 1180, zrbwg));
        baidumark.add(new BaiduMark_Item(39.906996, 116.439712, R.drawable.user_qinwnagfu, palace[2], introduce[2], 1210, tiantan));
        baidumark.add(new BaiduMark_Item(39.886376, 116.453414, R.drawable.user_gugong, palace[3], introduce[3], 1520, tiantan));


        baidumark.add(new BaiduMark_Item(39.886376, 116.358266, R.drawable.user_gugong, palace[5], introduce[3], 1520,tiantan));
        baidumark.add(new BaiduMark_Item(39.924091, 116.415115, R.drawable.user_mcqyz, palace[6], introduce[4], 190, tiantan));

        baidumark1.add(new BaiduMark_Item(39.849644, 116.436326, R.drawable.user_tiantan, palace[7], introduce[0], 110,tiantan));
        baidumark1.add(new BaiduMark_Item(39.904725, 116.496326, R.drawable.user_zrbwg, palace[8], introduce[1], 1180, zrbwg));
        baidumark1.add(new BaiduMark_Item(39.886376, 116.417115, R.drawable.user_qinwnagfu, palace[9], introduce[2], 1210, zrbwg));
        baidumark1.add(new BaiduMark_Item(39.924091, 116.403414, R.drawable.user_gugong, palace[10], introduce[3], 1520, zrbwg));


    }

    /**
     * @param i
     * @param j
     * @return
     */
    //放大缩小
    Bitmap scaleBitmap(int i, float j)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), i);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = j;
        float scaleHeight = j;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.making_line_lineone:
//                new  AlertDialog.Builder(getActivity())
//
//                        .setTitle("标题" )
//
//                        .setMessage("简单消息框"+mColorfulPolyline1.size())
//
//                        .setPositiveButton("确定" ,  null )
//
//                        .show();

                mColorfulPolyline1.get(0).setWidth(20);
                mColorfulPolyline1.get(1).setWidth(10);
                mColorfulPolyline1.get(2).setWidth(10);
//                redraw1();
                MapStatus.Builder msu = new MapStatus.Builder();
                LatLng latLng1 = new LatLng(calculateLAt(point),calculateLong(point));
//                Toast.makeText(getActivity(),+calculateLAt(point)+"\n"+calculateLong(point),Toast.LENGTH_SHORT).show();
                msu.target(latLng1);
                myBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(msu.build()));
//                private LinearLayout linearLayout;
//                private LinearLayout linearLayout_item;

                linearLayout.setVisibility(View.VISIBLE);
                if(dynamicChangeView.getTmpChild()!=container1) {
                    dynamicChangeView.RemoveView(linearLayout,dynamicChangeView.getTmpChild(),dynamicChangeView.getTmpClick());
                    dynamicChangeView.AddView(linearLayout, container1, lineone);
                }

                if(mMarkerLayout != null){
                    mMarkerLayout.setVisibility(View.GONE);
                }
                if(gridLayout !=null){
                    gridLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.making_line_linetwo:
                mColorfulPolyline1.get(0).setWidth(10);
                mColorfulPolyline1.get(1).setWidth(20);
                mColorfulPolyline1.get(2).setWidth(10);
                MapStatus.Builder msu1 = new MapStatus.Builder();
                LatLng latLng2 = new LatLng(calculateLAt(point1),calculateLong(point1));
                msu1.target(latLng2);
                myBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(msu1.build()));
                linearLayout.setVisibility(View.VISIBLE);
//                dynamicChangeView.RemoveView(linearLayout,container1,lineone);
//                dynamicChangeView.AddView(linearLayout,container2,linetwo);
                if(dynamicChangeView.getTmpChild()!=container2) {
                    dynamicChangeView.RemoveView(linearLayout,dynamicChangeView.getTmpChild(),dynamicChangeView.getTmpClick());
                    dynamicChangeView.AddView(linearLayout, container2, linetwo);
                }

                if(mMarkerLayout != null){
                    mMarkerLayout.setVisibility(View.GONE);
                }
                if(gridLayout !=null){
                    gridLayout.setVisibility(View.GONE);
                }

                break;
            case R.id.making_line_linethree:
                mColorfulPolyline1.get(0).setWidth(10);
                mColorfulPolyline1.get(1).setWidth(10);
                mColorfulPolyline1.get(2).setWidth(20);

                MapStatus.Builder msu2 = new MapStatus.Builder();
                LatLng latLng3 = new LatLng(calculateLAt(point2),calculateLong(point2));
                msu2.target(latLng3);
                myBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(msu2.build()));
                linearLayout.setVisibility(View.VISIBLE);
//                dynamicChangeView.RemoveView(linearLayout,container1,lineone);
//                dynamicChangeView.AddView(linearLayout,container2,linetwo);
                if(dynamicChangeView.getTmpChild()!=container3) {
                    dynamicChangeView.RemoveView(linearLayout,dynamicChangeView.getTmpChild(),dynamicChangeView.getTmpClick());
                    dynamicChangeView.AddView(linearLayout, container3, linetwo);
                }

                if(mMarkerLayout != null){
                    mMarkerLayout.setVisibility(View.GONE);
                }
                if(gridLayout !=null){
                    gridLayout.setVisibility(View.GONE);
                }
                break;

            case R.id.making_line_canteen:
                Toast.makeText(mContext,"jhkjk",Toast.LENGTH_SHORT).show();
                addmarks();
                ms = new MapStatus.Builder().zoom(15.0f).build();
                myBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
                break;
            case R.id.making_line_hospital:
                optionsesMark.removeAll(optionsesMark);
                addmark_hospital();
                ms = new MapStatus.Builder().zoom(13.5f).build();
                myBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
                break;


            /*
            进度条
             */
            case R.id.pop_window_sure:


                    point.remove(4);
                    optionses.set(0,new PolylineOptions().width(10).color(0xAAFF0000).points(point));
                    mColorfulPolyline1.get(0).remove();
                    mColorfulPolyline1.remove(0);
                    mColorfulPolyline1.add(0,(Polyline)myBaiduMap.addOverlay(optionses.get(0)));

                    point2.add(new LatLng(39.924091,116.453414));
                    optionses.set(2,new PolylineOptions().color(0xffff8800).width(20).points(point2));
                    mColorfulPolyline1.get(2).remove();
                    mColorfulPolyline1.remove(2);
                    mColorfulPolyline1.add(2,(Polyline)myBaiduMap.addOverlay(optionses.get(2)));

                    popupWindow.dismiss();

                    Toast.makeText(mContext,"行程已优化！",Toast.LENGTH_SHORT).show();



            default:
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            start_lat = bdLocation.getLatitude();
            start_long = bdLocation.getLongitude();
            if (bdLocation == null || myMap == null) {
                return;
            }

            if (isFirst == true) {

                if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation || bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                    Toast.makeText(getActivity(), "定位成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "请检查网络！", Toast.LENGTH_SHORT).show();
                }
//                LatLng latLng = new LatLng(50.915,116.404);
//                MapStatusUpdate  msu = MapStatusUpdateFactory.newLatLng(latLng);
//                myBaiduMap.animateMapStatus(msu);
//                msu = MapStatusUpdateFactory.zoomTo(16.0f);
//                myBaiduMap.animateMapStatus(msu);

                LatLng ll = new LatLng(39.906996, 116.417115);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(13.0f);
                myBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                isFirst = false;
                addOverly(Cfg.baidumark);
                addOverly1(Cfg.baidumark1);
                drawLine();
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
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

    private void addOverly1(List<BaiduMark_Item> baidumark) {


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

    private void drawLine() {
       mColorfulPolyline1.add((Polyline)myBaiduMap.addOverlay(optionses.get(0)));
   //     mColorfulPolyline2.add(new mColorfulPolylineint((Polyline)myBaiduMap.addOverlay(optionses.get(0)),1));
      //  mColorfulPolyline2.get(0).getmColorfulPolyline().isVisible();
        mColorfulPolyline1.add((Polyline)myBaiduMap.addOverlay(optionses.get(1)));

        mColorfulPolyline1.add((Polyline)myBaiduMap.addOverlay(optionses.get(2)));
    //    mColorfulPolyline2.add(new mColorfulPolylineint((Polyline)myBaiduMap.addOverlay(optionses.get(1)),2));
//        mColorfulPolyline1.get(0).remove();
//        mColorfulPolyline1.remove(0);

    }

    /**
     * @param point    、
     * @return
     */
    private double calculateLAt(List<LatLng> point){
        double latitude = 0 ;
        for (int i = 0; i < point.size(); i++) {
            latitude += point.get(i).latitude;
        }

        return  latitude/point.size();
    }

    private double calculateLong(List<LatLng> point){
        double longtitude = 0 ;
        for (int i = 0; i < point.size(); i++) {
            longtitude += point.get(i).longitude;
        }

        return  longtitude/point.size();



    }
    @Override
    public void onResume() {
        super.onResume();

    }


    public Schedule_Frag() {

    }


    private void init() {

    }


    @Override
    public void onStart() {
        super.onStart();

    }


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

    public void addmarks(){
        List<LatLng> canteen = new ArrayList<LatLng>();
        canteen.add(new LatLng(39.879644, 116.399326));
        canteen.add(new LatLng(39.889544, 116.396426));
        canteen.add(new LatLng(39.899444, 116.406526));
        canteen.add(new LatLng(39.879744, 116.406226));
        canteen.add(new LatLng(39.879144, 116.416126));
        canteen.add(new LatLng(39.889144, 116.416126));


        for (int i = 0; i < canteen.size(); i++) {
            optionsesMark.add(new MarkerOptions().position(canteen.get(i)).icon(mark_eat).zIndex(99));
            myBaiduMap.addOverlay(optionsesMark.get(i));
        }
    }

    public  void addmark_hospital(){

        List<LatLng> hospital = new ArrayList<LatLng>();
        hospital.add(new LatLng(39.869644, 116.412326));
        hospital.add(new LatLng(39.899544, 116.436426));
        hospital.add(new LatLng(39.889444, 116.356526));
        hospital.add(new LatLng(39.859744, 116.366226));
        hospital.add(new LatLng(39.889144, 116.356126));
        hospital.add(new LatLng(39.899144, 116.366126));


        for (int i = 0; i < hospital.size(); i++) {
            optionsesMark.add(new MarkerOptions().position(hospital.get(i)).icon(mark_hospital).zIndex(99));
            myBaiduMap.addOverlay(optionsesMark.get(i));
        }


    }

}
