package com.buu.app.travel;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.buu.app.travel.adapter.ThemeMakingAdapter;
import com.buu.app.travel.role.Theme_Choice;
import com.buu.app.travel.role.Thememaking_Item;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.buu.app.travel.Theme.tianshu;

public class ThemeMaking extends AppCompatActivity {
    public void actionStart(Context context, Theme_Choice choice){
        Choice = choice;
        Intent start = new Intent(context,ThemeMaking.class);
        context.startActivity(start);
    }

    private static final String TAG = "ThemeMaking";
    private static Theme_Choice Choice;
    TextView textDate;
    RecyclerView recyclerView;

    private  Context context;
    ThemeMakingAdapter adapter;
    //获取时间有关
    public Calendar calendar;
    public SimpleDateFormat sdf;
    public static String currentDate;
    private static String lastDate;
    private static String oneDate;
    private static String twoDate;
    private static String threeDate;
    //根据用户的选择读取string字符并匹配绑定数据
    public static String one="";
    public static String two="";
    public static String three="";
    public  String theme[] = new String []{};
    public String relax[] = new String[]{};
    public String palace[] = new String[]{};
    public String content[] = new String[]{};
    Resources res;
    LinearLayoutManager manager;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_theme_making);
        context = this;
        res = context.getResources();
        theme = res.getStringArray(R.array.theme);
        relax = res.getStringArray(R.array.relax);
        palace=res.getStringArray(R.array.palace);
        content = res.getStringArray(R.array.content);

        initView();
        //g根据用户选择显示数据

        Cfg.Theme_Item.clear();
        findLastDate();
        choiceDate();
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        adapter = new ThemeMakingAdapter(Cfg.Theme_Item);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollListener(listener);
        currentDate = Theme.choice_year+"/"+Theme.choice_month+"/"+Theme.choice_day+"-"+lastDate;
        textDate.setText(currentDate);
        textDate.setTextSize(20);
        Log.d(TAG, "onCreate: "+Choice.getTheme()+"\n"+Choice.getTime()+"\n"+Choice.getRelax());

    }

    RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            Log.d(TAG, "onScrollStateChanged: : :"+dx+"\n"+dy);
        }
    };

    private void choiceDate() {
        switch(Choice.getTime()){

            case "一天":
                if(Choice.getTheme().equals(theme[0])&&Choice.getRelax().equals(relax[0])){
                    initDate_palace_1_1();
                    linearLayout.setVisibility(View.GONE);
                }

                else if(Choice.getTheme().equals(theme[0])&&(Choice.getRelax().equals(relax[1]))){
                    initDate_palace_1_2();
                }
                break;
            case "两天":
                if(Choice.getTheme().equals(theme[0])&&Choice.getRelax().equals(relax[0])){
                    initDate_palace_2_1();
                }

                else if(Choice.getTheme().equals(theme[0])&&Choice.getRelax().equals(relax[1])) {
                    initDate_palace_2_2();
                }
                break;
            case "三天":
                if(Choice.getTheme().equals(theme[0])&&Choice.getRelax().equals(relax[0])){
                    initDate_palace_3_1();
                }
                else if(Choice.getTheme().equals(theme[0])&&Choice.getRelax().equals(relax[1])) {
                    initDate_palace_3_2();
                }
                break;
            default:
                break;
        }
    }

    private void initDate_palace_1_1() {
            Thememaking_Item make1 = new Thememaking_Item(oneDate,"Day1."+palace[0],R.drawable.user_tiantan,palace[0],content[0],
                    "Day1."+palace[1],R.drawable.user_zrbwg,palace[1],content[1]);
        Cfg.Theme_Item.add(make1);
    }

    private void initDate_palace_1_2(){
        Thememaking_Item make1 = new Thememaking_Item(oneDate,"Day1."+palace[0],R.drawable.user_tiantan,palace[0],content[0],
                "Day1."+palace[1],R.drawable.user_zrbwg,palace[1],content[1]);
        Cfg.Theme_Item.add(make1);
        Thememaking_Item make2 = new Thememaking_Item(oneDate,"Day2."+palace[2],R.drawable.user_qinwnagfu,palace[2],content[2],
                "Day2."+palace[3],R.drawable.user_gugong,palace[3],content[3]);
        Cfg.Theme_Item.add(make2);
    }

    private void initDate_palace_2_1() {
        Thememaking_Item make2 = new Thememaking_Item(oneDate,"Day1."+palace[2],R.drawable.user_qinwnagfu,palace[2],content[2],
                "Day1."+palace[3],R.drawable.user_gugong,palace[3],content[3]);
        Cfg.Theme_Item.add(make2);
        Thememaking_Item make1 = new Thememaking_Item(twoDate,"Day2."+palace[0],R.drawable.user_tiantan,palace[0],content[0],
                "Day2."+palace[1],R.drawable.user_zrbwg,palace[1],content[1]);
        Cfg.Theme_Item.add(make1);
        linearLayout.setVisibility(View.GONE);
    }

    private void initDate_palace_2_2() {
        Thememaking_Item make2 = new Thememaking_Item(oneDate,"Day1."+palace[2],R.drawable.user_qinwnagfu,palace[2],content[2],
                "Day1."+palace[3],R.drawable.user_gugong,palace[3],content[3]);
        Cfg.Theme_Item.add(make2);
        Thememaking_Item make1 = new Thememaking_Item(twoDate,"Day2."+palace[0],R.drawable.user_tiantan,palace[0],content[0],
                "Day2."+palace[1],R.drawable.user_zrbwg,palace[1],content[1]);
        Cfg.Theme_Item.add(make1);

    }

    private void initDate_palace_3_1() {
        Thememaking_Item make2 = new Thememaking_Item(oneDate,"Day1."+palace[2],R.drawable.user_qinwnagfu,palace[2],content[2],
                "Day1."+palace[3],R.drawable.user_gugong,palace[3],content[3]);
        Cfg.Theme_Item.add(make2);
        Thememaking_Item make1 = new Thememaking_Item(twoDate,"Day2."+palace[0],R.drawable.user_tiantan,palace[0],content[0],
                "Day2."+palace[1],R.drawable.user_zrbwg,palace[1],content[1]);
        Cfg.Theme_Item.add(make1);
        Thememaking_Item make3 = new Thememaking_Item(threeDate,"Day3."+palace[4],R.drawable.user_mcqyz,palace[4],content[4],
                "Day3."+palace[5],R.drawable.main_mei4,palace[5],content[5]);
        Cfg.Theme_Item.add(make3);
        linearLayout.setVisibility(View.GONE);
    }

    private void initDate_palace_3_2() {
        Thememaking_Item make2 = new Thememaking_Item(oneDate,"Day1."+palace[2],R.drawable.user_qinwnagfu,palace[2],content[2],
                "Day1."+palace[3],R.drawable.user_gugong,palace[3],content[3]);
        Cfg.Theme_Item.add(make2);
        Thememaking_Item make1 = new Thememaking_Item(twoDate,"Day2."+palace[0],R.drawable.user_tiantan,palace[0],content[0],
                "Day2."+palace[1],R.drawable.user_zrbwg,palace[1],content[1]);
        Cfg.Theme_Item.add(make1);
        Thememaking_Item make3 = new Thememaking_Item(threeDate,"Day3."+palace[4],R.drawable.user_mcqyz,palace[4],content[4],
                "Day3."+palace[5],R.drawable.main_mei4,palace[5],content[5]);
        Cfg.Theme_Item.add(make3);
    }
    private void initView() {
        linearLayout = (LinearLayout)findViewById(R.id.main_theme_making_linear);
        textDate = (TextView) findViewById(R.id.main_theme_making_date);
        recyclerView = (RecyclerView)findViewById(R.id.main_thememaking_recyclerview);
    }
    private void findLastDate() {
        calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(Theme.choice_year), Integer.parseInt(Theme.choice_month)-1, Integer.parseInt(Theme.choice_day));
        calendar.add(Calendar.DAY_OF_MONTH, tianshu);
        sdf = new SimpleDateFormat("yyyy/M/d");
        lastDate = sdf.format(calendar.getTime());

        calendar.set(Integer.parseInt(Theme.choice_year), Integer.parseInt(Theme.choice_month)-1, Integer.parseInt(Theme.choice_day));
        calendar.add(Calendar.DAY_OF_MONTH,0);
        oneDate = sdf.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH,1);
        twoDate = sdf.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH,1);
        threeDate = sdf.format(calendar.getTime());

        Log.d(TAG, "onCreate: "+lastDate);
        Log.d(TAG, "onCreate: "+Theme.choice_year+"/"+Theme.choice_month+"/"+Theme.choice_day);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
