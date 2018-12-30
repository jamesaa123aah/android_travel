package com.buu.app.travel.adapter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.buu.app.travel.Cfg;
import com.buu.app.travel.R;
import com.buu.app.travel.role.Plan;
import com.buu.app.travel.tools.LunarCalendar;
import com.buu.app.travel.tools.SpecialCalendar;

public class CalendarAdapter extends BaseAdapter {
    private static final String TAG = "CalendarAdapter";
    private boolean isLeapyear = false;  //是否为闰年
    private int daysOfMonth = 0;      //某月的天数
    private int dayOfWeek = 0;        //具体某一天是星期几
    private int lastDaysOfMonth = 0;  //上一个月的总天数
    private Context context;
    private String[] dayNumber = new String[42];  //一个gridview中的日期存入此数组中
    private View[] convertViews = new View[42];
    private SpecialCalendar sc = null;
    private LunarCalendar lc = null;
    private String currentYear = "";
    private String currentMonth = "";
    private String currentDay = "";
    private List<Plan> plans;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    private int currentFlag = -1;     //用于标记当天
    private String showYear = "";   //用于在头部显示的年份
    private String showMonth = "";  //用于在头部显示的月份
    private String animalsYear = "";
    private String leapMonth = "";   //闰哪一个月
    private String cyclical = "";   //天干地支
    //系统当前时间
    private String sysDate = "";
    private String sys_year = "";
    private String sys_month = "";
    private String sys_day = "";

    public CalendarAdapter() {
        Date date = new Date();
        sysDate = sdf.format(date);  //当期日期
        sys_year = sysDate.split("-")[0];
        sys_month = sysDate.split("-")[1];
        sys_day = sysDate.split("-")[2];
        plans = Cfg.Plans;
    }

    public CalendarAdapter(Context context, int jumpMonth, int jumpYear, int year_c, int month_c, int day_c) {
        this();
        this.context = context;
        sc = new SpecialCalendar();
        lc = new LunarCalendar();

        int stepYear = year_c + jumpYear;
        int stepMonth = month_c + jumpMonth;
        if (stepMonth > 0) {
            //往下一个月滑动
            if (stepMonth % 12 == 0) {
                stepYear = year_c + stepMonth / 12 - 1;
                stepMonth = 12;
            } else {
                stepYear = year_c + stepMonth / 12;
                stepMonth = stepMonth % 12;
            }
        } else {
            //往上一个月滑动
            stepYear = year_c - 1 + stepMonth / 12;
            stepMonth = stepMonth % 12 + 12;
            if (stepMonth % 12 == 0) {

            }
        }

        currentYear = String.valueOf(stepYear);//得到当前的年份
        currentMonth = String.valueOf(stepMonth);  //得到本月 （jumpMonth为滑动的次数，每滑动一次就增加一月或减一月）
        currentDay = String.valueOf(day_c);  //得到当前日期是哪天
        getCalendar(Integer.parseInt(currentYear), Integer.parseInt(currentMonth));
    }

    public CalendarAdapter(Context context, int year, int month, int day) {
        this();
        this.context = context;
        sc = new SpecialCalendar();
        lc = new LunarCalendar();
        currentYear = String.valueOf(year);
        ;  //得到跳转到的年份
        currentMonth = String.valueOf(month);  //得到跳转到的月份
        currentDay = String.valueOf(day);  //得到跳转到的天
        getCalendar(Integer.parseInt(currentYear), Integer.parseInt(currentMonth));
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dayNumber.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calendar_item, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.calendar_item_text);
        String d = dayNumber[position].split("\\.")[0];
        String dv = dayNumber[position].split("\\.")[1];
        if (dv.length() > 5) {
            dv = dv.substring(0, 4) + "...";
        }
        SpannableString sp = new SpannableString(d + "\n" + dv);
        sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new RelativeSizeSpan(1.2f), 0, d.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (dv != null || dv != "") {
            sp.setSpan(new RelativeSizeSpan(0.75f), d.length() + 1, dayNumber[position].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(sp);
        textView.setTextColor(Color.GRAY);
        if (position < daysOfMonth + dayOfWeek && position >= dayOfWeek) {
            // 当前月信息显示
            textView.setTextColor(Color.BLACK);// 当月字体设黑
        }
        if (currentFlag == position) {
            //设置当天的背景
            textView.setBackgroundResource(R.drawable.current_day);
            textView.setTextColor(Color.WHITE);
        }
        final String da = currentYear + "/" + currentMonth + "/" + d;
        for (Plan p : plans) {
            if (p.getDate().equals(da)) {
                convertView.findViewById(R.id.calendar_item_have_things).setVisibility(View.VISIBLE);
            }
        }
        convertViews[position] = convertView;
        return convertView;
    }

    //得到某年的某月的天数且这月的第一天是星期几
    public void getCalendar(int year, int month) {
        isLeapyear = sc.isLeapYear(year);              //是否为闰年
        daysOfMonth = sc.getDaysOfMonth(isLeapyear, month);  //某月的总天数
        dayOfWeek = sc.getWeekdayOfMonth(year, month);      //某月第一天为几号位
        lastDaysOfMonth = sc.getDaysOfMonth(isLeapyear, month - 1);  //上一个月的总天数
        getWeek(year, month);
    }

    //将一个月中的每一天的值添加入数组dayNumber中
    private void getWeek(int year, int month) {
        int j = 1;
        String lunarDay = "";
        //得到当前月的所有日程日期
        for (int i = 0; i < dayNumber.length; i++) {
            if (i < dayOfWeek) {  //前一个月
                int temp = lastDaysOfMonth - dayOfWeek + 1;
                lunarDay = lc.getLunarDate(year, month - 1, temp + i, false);
                dayNumber[i] = (temp + i) + "." + lunarDay;
            } else if (i < daysOfMonth + dayOfWeek) {   //本月
                String day = String.valueOf(i - dayOfWeek + 1);   //得到的日期
                lunarDay = lc.getLunarDate(year, month, i - dayOfWeek + 1, false);
                dayNumber[i] = i - dayOfWeek + 1 + "." + lunarDay;
                //对于当前月才去标记当前日期
                if (sys_year.equals(String.valueOf(year)) && sys_month.equals(String.valueOf(month)) && sys_day.equals(day)) {
                    //标记当前日期
                    currentFlag = i;
                }
                setShowYear(String.valueOf(year));
                setShowMonth(String.valueOf(month));
                setAnimalsYear(lc.animalsYear(year));
                setLeapMonth(lc.leapMonth == 0 ? "" : String.valueOf(lc.leapMonth));
                setCyclical(lc.cyclical(year));
            } else {   //下一个月
                lunarDay = lc.getLunarDate(year, month + 1, j, false);
                dayNumber[i] = j + "." + lunarDay;
                j++;
            }
        }
        String abc = "";
        for (int i = 0; i < dayNumber.length; i++) {
            abc = abc + dayNumber[i] + ":";
        }
    }

    //某一位置的日期
    public String getDateByClickItem(int position) {
        int cuM = Integer.parseInt(currentMonth);
        if(position>getEndPosition()){
            cuM++;
        }
        if(position<getStartPosition()){
            cuM--;
        }
        return currentYear + "/" + cuM+ "/" + dayNumber[position].split("\\.")[0];
    }

    //当月中第一天的位置
    public int getStartPosition() {
        return dayOfWeek;
    }
    //当月中最后一天的位置
    public int getEndPosition() {
        return (dayOfWeek + daysOfMonth ) - 1;
    }

    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    public String getAnimalsYear() {
        return animalsYear;
    }

    public void setAnimalsYear(String animalsYear) {
        this.animalsYear = animalsYear;
    }

    public String getLeapMonth() {
        return leapMonth;
    }

    public void setLeapMonth(String leapMonth) {
        this.leapMonth = leapMonth;
    }

    public String getCyclical() {
        return cyclical;
    }

    public void setCyclical(String cyclical) {
        this.cyclical = cyclical;
    }

    public View[] getConvertViews() {
        return convertViews;
    }
}
