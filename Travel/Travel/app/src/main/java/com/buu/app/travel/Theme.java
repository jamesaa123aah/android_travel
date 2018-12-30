package com.buu.app.travel;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.buu.app.travel.role.Theme_Choice;
import com.buu.app.travel.tools.SpecialCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Theme extends AppCompatActivity {
    //主题listView
//    private static final String TAG = "Theme";
//    ListView listview;
//    Theme_Item theme_item;
//    private List<Theme_Item> theme_items = new ArrayList<>();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_theme);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//
//        initView();
//        initListView();
//        ThemeAdapter adapter = new ThemeAdapter(Theme.this,R.layout.main_theme_item, theme_items);
//        listview.setAdapter(adapter);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                theme_item = theme_items.get(position);
//                Log.d(TAG, ""+l);
//                Toast.makeText(Theme.this, theme_item.getTitle()+position,Toast.LENGTH_SHORT).show();
//
//                switch((int) l){
//                    case 0:
//                        Cfg.TITLE = 0;
//                        break;
//                    case 1:
//                        Cfg.TITLE = 1;
//                        break;
//                    case 2:
//                        Cfg.TITLE = 2;
//                        break;
//                    case 3:
//                        Cfg.TITLE = 3;
//                        break;
//                    case 4:
//                        Cfg.TITLE = 4;
//                        break;
//                    case 5:
//                        Cfg.TITLE = 5;
//                        break;
//                    case 6:
//                        Cfg.TITLE = 6;
//                        break;
//                    default:
//                        break;
//
//                }
//
//
//            }
//        });
//
//
//    }
//
//    private void initListView() {
//            Theme_Item app0 = new Theme_Item("皇城",R.drawable.theme_palace);
//            theme_items.add(app0);
//            Theme_Item app1 = new Theme_Item("胡同",R.drawable.theme_hutong);
//            theme_items.add(app1);
//            Theme_Item app2 = new Theme_Item("园林",R.drawable.theme_yuanlin);
//            theme_items.add(app2);
//            Theme_Item app3 = new Theme_Item("美食",R.drawable.theme_delecious);
//            theme_items.add(app3);
//            Theme_Item app4 = new Theme_Item("民宿",R.drawable.theme_minsu);
//            theme_items.add(app4);
//            Theme_Item app5 = new Theme_Item("清凉",R.drawable.theme_water);
//            theme_items.add(app5);
//            Theme_Item app6 = new Theme_Item("游乐",R.drawable.theme_play);
//            theme_items.add(app6);
//    }
//
//    private void initView() {
//        listview = (ListView) findViewById(R.id.main_theme_list);
//    }zhuxidddd


    private static final String TAG = "Theme";
    Spinner spinner1, spinner2, spinner3,spinner4,spinner5,spinner6;
    List<String> theme = new ArrayList<>();
    List<String> time = new ArrayList<>();
    List<String> relax = new ArrayList<>();
    List<String> year = new ArrayList<>();
    List<String> month = new ArrayList<>();
    List<String> day = new ArrayList<>();
    String[] theme_string ;
    String[] time_string;
    String[] relax_string ;
    ArrayAdapter<String> adapterTheme;
    ArrayAdapter<String> adapterTime;
    ArrayAdapter<String> adapterRelax;
    ArrayAdapter<String> adapterYear;
    ArrayAdapter<String> adapterMonth;
    ArrayAdapter<String> adapterDay;
    public static Theme_Choice choice;
    public static String userCurrentDate="";
    Calendar calendar;
    public static  int tianshu;
    public static String choice_year="2017",choice_month="01",choice_day="01";
    private SpecialCalendar specialCanlendar = new SpecialCalendar();
    private Button btn;
    private ImageView img;

//    public Theme_Choice getChoice() {
//        return choice;
//    }
//
//    public void setChoice(Theme_Choice choice) {
//        this.choice = choice;
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_theme1);
        initView();
        initData();
        adapterTheme = bondAdapter(theme,spinner1);
        adapterTime = bondAdapter(time,spinner2);
        adapterRelax = bondAdapter(relax,spinner3);
        adapterYear = bondAdapter(year,spinner4);
        adapterMonth = bondAdapter(month,spinner5);
        adapterDay = bondAdapter(day,spinner6);
        initListener();


        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //动态绑定天数
                day.clear();

                Boolean isLeapYear = specialCanlendar.isLeapYear(Integer.parseInt(spinner4.getSelectedItem().toString()));
                final int dday =specialCanlendar.getDaysOfMonth(isLeapYear, Integer.parseInt(spinner5.getSelectedItem().toString()));
                for(int j=1;j<=dday;j++){
                    String s = j < 10 ? ""+j : ""+j;
                    day.add(s);
                }
                choice_month = String.valueOf(spinner5.getSelectedItem());
                userCurrentDate = userCurrentDate.concat(choice_year+"/"+choice_month+"/"+choice_day);
                Log.d(TAG, "onCreate: "+userCurrentDate);
                adapterDay.notifyDataSetChanged();
                spinner6.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ThemeMaking().actionStart(Theme.this,choice);
                finish();
            }
        });

    }

    //绑定适配器
    private ArrayAdapter<String> bondAdapter(List<String> list, Spinner spinner){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Theme.this,R.layout.support_simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        return adapter;
    }

    private void initListener() {
        spinner1.setOnItemSelectedListener(listener);
        spinner2.setOnItemSelectedListener(listener);
        spinner3.setOnItemSelectedListener(listener);
        spinner4.setOnItemSelectedListener(listener);
        spinner6.setOnItemSelectedListener(listener);
    }

    private void initData() {
        calendar = Calendar.getInstance();
        Resources res = getResources();
        theme_string = res.getStringArray(R.array.theme);
        relax_string = res.getStringArray(R.array.relax);
        time_string = res.getStringArray(R.array.time);
        for (int i = 0; i < theme_string.length; i++) {
            theme.add(theme_string[i]);
        }

        for (int i = 0; i < time_string.length; i++) {
            time.add(time_string[i]);
        }

        for (int i = 0; i < relax_string.length; i++) {
            relax.add(relax_string[i]);
        }

        //绑定年份
        for (int i = 0; i < 40; i++) {
            year.add("" + (calendar.get(Calendar.YEAR) + i));
        }
        //绑定月份
        for (int i = 1; i <= 12; i++) {
            month.add("" + (i < 10 ?   i : i));
        }
    }

    private void initView() {
        spinner1 = (Spinner) findViewById(R.id.main_theme1_spinner1);
        spinner2 = (Spinner) findViewById(R.id.main_theme1_spinner2);
        spinner3 = (Spinner) findViewById(R.id.main_theme1_spinner3);
        spinner4 = (Spinner) findViewById(R.id.main_theme1_spinner4);
        spinner5 = (Spinner) findViewById(R.id.main_theme1_spinner5);
        spinner6 = (Spinner) findViewById(R.id.main_theme1_spinner6);
        img = (ImageView)findViewById(R.id.main_theme1_img);
        btn = (Button)findViewById(R.id.main_theme1_btn);
    }

    ItemListener listener = new ItemListener();

    public class ItemListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String one = theme_string[(int) spinner1.getSelectedItemId()];
            String two = time_string[(int) spinner2.getSelectedItemId()];
            tianshu = spinner2.getSelectedItemPosition();
            String three = relax_string[(int)spinner3.getSelectedItemId()];
            choice_year = String.valueOf(spinner4.getSelectedItem());
            Log.d(TAG, "onItemSelected: "+spinner1.getSelectedItemPosition());
            choice_day = String.valueOf(spinner6.getSelectedItem());
            choice =new  Theme_Choice(one,two,three,userCurrentDate);
            Log.d(TAG, "onItemSelected: "+userCurrentDate);

            switch(spinner1.getSelectedItemPosition()){
                case 0:
                    img.setImageResource(R.drawable.main_theme_palace);
                    break;
                case 1:
                    img.setImageResource(R.drawable.theme_hutong);
                    break;
                case 2:
                    img.setImageResource(R.drawable.theme_yuanlin);
                    break;
                case 3:
                    img.setImageResource(R.drawable.theme_delecious);
                    break;
                case 4:
                    img.setImageResource(R.drawable.theme_minsu);
                    break;
                case 5:
                    img.setImageResource(R.drawable.theme_water);
                case 6:
                    img.setImageResource(R.drawable.theme_play);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }


}
