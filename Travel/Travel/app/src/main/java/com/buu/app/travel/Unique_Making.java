package com.buu.app.travel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.buu.app.travel.adapter.UniqueAdapter;
import com.buu.app.travel.role.Plan;
import com.buu.app.travel.role.Unique_Item;
import com.buu.app.travel.tools.LocalJson;
import com.buu.app.travel.tools.ScreenTools;
import com.buu.app.travel.tools.Sorts;
import com.buu.app.travel.tools.SpecialCalendar;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Unique_Making extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "Unique_Making";
    private final String[] TAGS = {"古迹", "名胜","购物","风景","宗教","博物馆","动物","欧式","文化","美食"};
    private boolean isShow =false;
    public static final int SortBack = 1;
    public static final int SIZER = 2;
    private Context mContext;
    private ArrayList<Unique_Item> ITEMS = new ArrayList<>();
    private ArrayList<Unique_Item> ITEMS2 = new ArrayList<>();
    private ArrayList<Unique_Item> U_items = new ArrayList<>();
    private ProgressDialog progressDialog;
    private ListView listview;
    private PopupWindow popupWindow;
    private View pv;
    private Spinner sSort;
    public static Handler mHandler;
    private TextView sort_status;
    private static boolean sort_asc = false;
    private EditText rangeLeft;
    private EditText rangeRight;
    private boolean[] bools = new boolean[]{false,false,false,false,false,false,false,false,false,false};
    private boolean sortDo = false;
    UniqueAdapter adapter;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_unique_making);
        mContext = getBaseContext();
        String jsonString = LocalJson.getJson("tourse.json",this);
        getJsonobject(jsonString);
        for(Unique_Item itm:U_items){
            ITEMS.add(itm);
        }
        listview = (ListView)findViewById(R.id.main_unique_listview);
        adapter = new UniqueAdapter(this,R.layout.main_unique_item,U_items);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(listViewListener);
        initView();
        initListener();
        initHandler();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Unique_Making.this,Unique_Making_Choice.class));
                finish();
            }
        });
    }

    private void initView() {
        btn = (Button)findViewById(R.id.unique_button);
        pv = LayoutInflater.from(this).inflate(R.layout.popwin,null,false);
        popupWindow = new PopupWindow(pv, ScreenTools.dip2px(mContext,320),ScreenTools.getScreenSize(mContext).y,true);
        popupWindow.setAnimationStyle(R.style.slide);
        sSort = (Spinner) findViewById(R.id.main_unique_sort_spinner);
        sort_status  = (TextView) findViewById(R.id.main_unique_sort_status);
        rangeLeft = (EditText) pv.findViewById(R.id.sizer_range_left);
        rangeRight = (EditText) pv.findViewById(R.id.sizer_range_right);
    }

    private void initListener(){
        findViewById(R.id.main_unique_sort).setOnClickListener(this);
        findViewById(R.id.main_unique_sort_status).setOnClickListener(this);
        pv.findViewById(R.id.sizer_back).setOnClickListener(this);
        rangeLeft.setOnFocusChangeListener(new mFocusListener());
        rangeRight.setOnFocusChangeListener(new mFocusListener());
        sSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(view.isEnabled() && view.isShown()){
                    sortDo = true;
                    Sort(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final int x = ((LinearLayout)pv.findViewById(R.id.sizer_star)).getChildCount();
        final LinearLayout ll = (LinearLayout)pv.findViewById(R.id.sizer_star);
        for(int i = 0;i <x;i++){
            ll.getChildAt(i).setId(-(i+10));
            ll.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int who = -(v.getId()%10);
                    for(int j = 0;j<x;j++){
                        if(j<=who){
                            ((ImageView)ll.getChildAt(j)).setImageResource(R.drawable.star_solid);
                        }else {
                            ((ImageView)ll.getChildAt(j)).setImageResource(R.drawable.star_hollow);
                        }
                    }
                    sizerStar(who+1);
                }
            });
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_unique_sort:
                transP();
                break;
            case R.id.main_unique_sort_status:
                sortStatus();
                break;
            case R.id.sizer_back:
                if(popupWindow!=null&&popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isShow){
            isShow = true;
        }
    }

    private void initHandler(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case SortBack:
                        ITEMS2 = (ArrayList<Unique_Item>) msg.obj;
                        sortBack();
                        break;
                    case SIZER:
                        bools = (boolean[]) msg.obj;
                        Sizer();
                        break;
                    default:
                        break;
                }
            }
        };
    }
    AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Log.d(TAG, "onItemClick: "+U_items.get(position).getTitle());
            new Choice_Theme_Info().startaction(Unique_Making.this,U_items.get(position).getTitle(),U_items.get(position).getImg());

        }
    };
    public ArrayList<Unique_Item> getUItems(){
        return U_items;
    }

    public void getJsonobject(String jsonData){
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String tags = jsonObject.getString("tag");
                String title = jsonObject.getString("title");
                String img = jsonObject.getString("img");
                String introduce = jsonObject.getString("introduce");
                int look = Integer.parseInt(jsonObject.getString("look"));
                int comment = Integer.parseInt(jsonObject.getString("comment"));
                int praise = Integer.parseInt(jsonObject.getString("praise"));
                int star = Integer.parseInt(jsonObject.getString("star"));
                double money = Double.parseDouble(jsonObject.getString("money"));
                Unique_Item iem = new Unique_Item(tags,title,img,introduce,look,comment,praise,star,money);
                U_items.add(iem);
                Log.d(TAG, "getJsonobject: "+title);
                Log.d(TAG, "getJsonobject: "+img);
                Log.d(TAG, "getJsonobject: "+introduce);
            }
        }
        catch (Exception o){
            o.printStackTrace();
        }
    }
    private void transP(){
        if(!popupWindow.isShowing()){
            popupWindow.showAtLocation(findViewById(R.id.layout), Gravity.RIGHT|Gravity.TOP,0,0);
        }else {
            popupWindow.dismiss();
        }
    }
    private void sortBack(){
        ArrayList<Unique_Item> items = ITEMS2;
        int size = U_items.size();
        U_items.addAll(items);
        int x = 0;
        for (int i = 0;i<size;i++){
            U_items.remove(0);
            x++;
            if(x ==5){
                adapter.notifyDataSetChanged();
            }
        }
        adapter.notifyDataSetChanged();
        if(sort_asc){
            Collections.reverse(U_items);
            adapter.notifyDataSetChanged();
        }
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        int p = listview.getFirstVisiblePosition();
        if(p>5){
            listview.setSelection(6);
        }
        listview.smoothScrollToPosition(0);
        if(sortDo){
            Sizer();
            sortDo = false;
        }
    }
    private void Sizer(){
        final boolean[] bs = bools;
        if(bs.length==1&&bs[0] == true){
            Sort(sSort.getSelectedItemPosition());
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<String> ls = new ArrayList<String>();
                    for(int i = 0;i<bs.length;i++){
                        if(bs[i]==true){
                            ls.add(TAGS[i]);
                        }
                    }
                    List<Unique_Item> items = new ArrayList<Unique_Item>();
                    for(Unique_Item itm:ITEMS2){
                        boolean bre = false;
                        for(String tg:ls){
                            if(itm.getTags().indexOf(tg)==-1){
                                bre = true;
                                break;
                            }
                        }
                        if(bre ==false){
                            items.add(itm);
                        }
                    }
                    final List<Unique_Item> finalItems = items;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int size = U_items.size();
                            for(Unique_Item itm:finalItems){
                                U_items.add(itm);
                            }
                            int x = 0;
                            for (int i = 0;i<size;i++){
                                U_items.remove(0);
                                x++;
                                if(x ==5){
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            adapter.notifyDataSetChanged();
                            if(sort_asc){
                                Collections.reverse(U_items);
                                adapter.notifyDataSetChanged();
                            }
                            int p = listview.getFirstVisiblePosition();
                            if(p>5){
                                listview.setSelection(6);
                            }
                            listview.smoothScrollToPosition(0);
                        }
                    });
                }
            }).start();
        }
    }
    private void sortStatus(){
        if(!sort_asc){
            sort_asc = true;
            sort_status.setText(R.string.sort_asc);
        }else {
            sort_asc = false;
            sort_status.setText(R.string.sort_desc);
        }
        Collections.reverse(U_items);
        adapter.notifyDataSetChanged();
        int p = listview.getFirstVisiblePosition();
        if(p>5){
            listview.setSelection(6);
        }
        listview.smoothScrollToPosition(0);
    }
    private void Sort(int type){
        if(progressDialog==null){
            progressDialog = new ProgressDialog(this);
        }
        if(isShow){
            progressDialog.show();
        }
        Sorts.Sort(mHandler,SortBack,type,ITEMS);
    }
    private void sizerStar(final int who){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Unique_Item> items = new ArrayList<Unique_Item>();
                for(Unique_Item itm:ITEMS){
                    if(itm.getStar()>=who){
                        items.add(itm);
                    }
                }
                final  ArrayList<Unique_Item> finalItems = items;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int size = U_items.size();
                        for(Unique_Item itm:finalItems){
                            U_items.add(itm);
                        }
                        int x = 0;
                        for (int i = 0;i<size;i++){
                            U_items.remove(0);
                            x++;
                            if(x ==5){
                                adapter.notifyDataSetChanged();
                            }
                        }
                        adapter.notifyDataSetChanged();
                        if(sort_asc){
                            Collections.reverse(U_items);
                            adapter.notifyDataSetChanged();
                        }
                        int p = listview.getFirstVisiblePosition();
                        if(p>5){
                            listview.setSelection(6);
                        }
                        listview.smoothScrollToPosition(0);
                        Sizer();
                    }
                });
            }
        }).start();
    }
    class mFocusListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus){
                if(!rangeLeft.getText().toString().isEmpty()&&!rangeRight.getText().toString().isEmpty()){
                    int l = -1;
                    int r = -1;
                    try {
                        l = Integer.parseInt(rangeLeft.getText().toString());
                    }catch (Exception e){
                        rangeLeft.setText("");
                    }
                    try {
                        r = Integer.parseInt(rangeRight.getText().toString());
                    }catch (Exception e){
                        rangeRight.setText("");
                    }
                    if(l!=-1&&r!=-1) {
                        if (!(l > -1 && r > -1)) {
                            if (l < -1) {
                                rangeLeft.setText("" + (-l));
                                l = -l;
                            }
                            if (r < -1) {
                                rangeRight.setText("" + (-r));
                                r = -r;
                            }
                        }
                        if(l>r){
                            int t = l;
                            l = r;
                            r = t;
                        }
                        final int finalL = l;
                        final int finalR = r;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<Unique_Item> items = new ArrayList<Unique_Item>();
                                for(Unique_Item itm:ITEMS){
                                    if(itm.getMoney()>finalL&&itm.getMoney()<finalR){
                                        items.add(itm);
                                    }
                                }
                                final  ArrayList<Unique_Item> finalItems = items;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int size = U_items.size();
                                        for(Unique_Item itm:finalItems){
                                            U_items.add(itm);
                                        }
                                        int x = 0;
                                        for (int i = 0;i<size;i++){
                                            U_items.remove(0);
                                            x++;
                                            if(x ==5){
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                        if(sort_asc){
                                            Collections.reverse(U_items);
                                            adapter.notifyDataSetChanged();
                                        }
                                        int p = listview.getFirstVisiblePosition();
                                        if(p>5){
                                            listview.setSelection(6);
                                        }
                                        listview.smoothScrollToPosition(0);
                                        Sizer();
                                    }
                                });
                            }
                        }).start();
                    }else if((l == -1&&r ==-1)){
                        Sort(sSort.getSelectedItemPosition());
                    }
                }else {
                    Sort(sSort.getSelectedItemPosition());
                }
            }
        }
    }
}
