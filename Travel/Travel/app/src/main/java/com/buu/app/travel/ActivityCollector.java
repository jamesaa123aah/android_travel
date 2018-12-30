package com.buu.app.travel;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by king- on 2017/5/17.
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    //添加活动
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    //移除
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    //结束所有活动
    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
    //结束自己外的活动
    public static void finishOthers(Activity _this){
        for(Activity activity:activities){
            if(!activity.isFinishing()&&activity!=_this){
                activity.finish();
            }
        }
    }
}
