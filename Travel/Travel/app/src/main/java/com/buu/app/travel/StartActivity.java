package com.buu.app.travel;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import com.buu.app.travel.adapter.FirstInAdapter;
import com.buu.app.travel.role.FirstInItem;
import com.buu.app.travel.role.Plan;
import com.buu.app.travel.role.User;
import com.buu.app.travel.tools.CheckNet;
import com.youdao.sdk.app.YouDaoApplication;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.sms.SMSSDK;

public class StartActivity extends BaseActivity {
    private static final String TAG = "StartActivity";
    public static final int FAILURE = 0;
    public static final int SUCCESS = 1;
    public static final int OFFLINE = 3;
    private final int MIX_SHOW_TIME = 800;
    private boolean FIRST_IN = false;
    private Context _this;
    private FirstInAdapter adapter;
    private TextView version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SMSSDK.getInstance().initSdk(this);
        SMSSDK.getInstance().setIntervalTime(Cfg.CODE_TIME_OUT*1000);
        LitePal.initialize(this);
     //   YouDaoApplication.init(this, "2cd8d47022fa7fb3");
        _this = this;
        version = (TextView) findViewById(R.id.start_version);
        PackageManager pm = this.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(this.getPackageName(),PackageManager.GET_CONFIGURATIONS);
            version.setText("Version: "+pi.versionName+" "+pi.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        new AsyncTask<Void, Void, Integer>() {

            @Override
            protected Integer doInBackground(Void... params) {
                int result;
                long st = System.currentTimeMillis();
                result = doSomeThingConn();
                long tt = System.currentTimeMillis() - st;
                if (tt < MIX_SHOW_TIME) {
                    try {
                        Thread.sleep(MIX_SHOW_TIME - tt);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                doSomeThing();
                return result;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                if(!FIRST_IN) {
                    new LoginActivity().actionStart(_this);
                }else {
                    new FirstInActivity().actionStart(_this,adapter);
                }
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        }.execute(new Void[]{});
    }

    private int doSomeThingConn() {
        if (new CheckNet(_this).isConnectingToInternet()) {
           return OFFLINE;
        }
        //判断目前版本是否是否第一次进入
        //预加载
        //加载配置文件
        //
        //
        return SUCCESS;
    }
    private void doSomeThing(){
        FIRST_IN = true;
        if(FIRST_IN){
            ArrayList<FirstInItem> inItems = new ArrayList<>();
            inItems.add(new FirstInItem(R.drawable.a));
            inItems.add(new FirstInItem(R.drawable.b));
            inItems.add(new FirstInItem(R.drawable.c));
            inItems.add(new FirstInItem(R.drawable.d));

            adapter = new FirstInAdapter(inItems) {
                @Override
                public void onEnter() {
                    new LoginActivity().actionStart(_this);

                }
            };
        }
        Cfg.IMEI = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Cfg.Users = DataSupport.findAll(User.class);
        Cfg.Plans = new ArrayList<>();
    }
}
