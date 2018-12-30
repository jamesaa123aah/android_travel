package com.buu.app.travel;

import com.buu.app.travel.role.BaiduMark_Item;
import com.buu.app.travel.role.Plan;
import com.buu.app.travel.role.Thememaking_Item;
import com.buu.app.travel.role.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by king- on 2017/8/5.
 */

public class Cfg {
    public static final String BASE_URL = "http://47.93.184.45/Travel/";
    public static final String[] NATE_DEFAULT = {"自动","中文","日文", "英文","韩文","法文","俄文","葡萄牙文","西班牙文"};
    public static String IMEI = "00000000";
    public static final int CODE_TIME_OUT = 60;
    public static long ID = 1;
    public static boolean FIRST_IN = true;
    public static List<User> Users = new ArrayList<>();
    public static List<Plan> Plans = new ArrayList<>();
    public static boolean REMEMBER_ME = false;
    public static boolean AUTO_LOGIN = false;
    public static String LANGUAGE = "";//自动zh中文en英文
    public static Boolean firstFragment = false;

    public static List<Thememaking_Item> Theme_Item = new ArrayList<>();
    public static List<BaiduMark_Item> baidumark = new ArrayList<BaiduMark_Item>();
    public static List<BaiduMark_Item> baidumark2 = new ArrayList<BaiduMark_Item>();
    public static List<BaiduMark_Item> baidumark1 = new ArrayList<BaiduMark_Item>();
    public static List<BaiduMark_Item> baidumark4 = new ArrayList<BaiduMark_Item>();
    public static int TITLE = 1000;
}
