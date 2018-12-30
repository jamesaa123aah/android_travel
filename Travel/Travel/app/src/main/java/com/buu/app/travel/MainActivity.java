package com.buu.app.travel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.mapapi.SDKInitializer;
import com.buu.app.travel.fragment.Center_Frag;
import com.buu.app.travel.fragment.Mine_Frag;
import com.buu.app.travel.fragment.Schedule_Frag;
import com.buu.app.travel.fragment.Translate_Frag;
import com.buu.app.travel.role.Theme_Choice;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xiaoqiang-07-28.
 */

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    public void actionStart(Context context, Theme_Choice choice, int frag){
        Choice = choice;
        Frag = frag;
        Intent start = new Intent(context,MainActivity.class);
        context.startActivity(start);
    }
    public static Theme_Choice Choice;
    private static int Frag = 0;
    private static final String TAG = "MainActivity";
    //是否双击退出
    private  static Boolean isExit = false;
    RadioGroup radioGroup ;
    TextView texttop,textbottom;
    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    private Mine_Frag mine_frag;
    private Center_Frag center_frag;
    private Schedule_Frag schedule_frag;
    private Translate_Frag translate_frag;

    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.main);
        initView();
        if(Cfg.firstFragment != false) {
            fragmentManager = this.getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            schedule_frag = new Schedule_Frag();
            fragmentTransaction.add(R.id.frag_one, schedule_frag);
            fragmentTransaction.commit();
            Cfg.firstFragment = false;
            Log.d(TAG, "onCreate: "+radioGroup.getChildAt(1));

        }

        else if(savedInstanceState == null){
        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        center_frag = new Center_Frag();
        fragmentTransaction.add(R.id.frag_one,center_frag);
        fragmentTransaction.commit();}
        if(Choice!=null){
            Log.d(TAG, "onCreate: "+Choice.getTheme()+"\n"+Choice.getTime()+"\n"+Choice.getRelax());
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this,Wether.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Cfg.TITLE==0){
            texttop.setBackgroundColor(Color.rgb(238,154,0));
            textbottom.setBackgroundColor(Color.rgb(238,154,0));
            findViewById(R.id.linear1).setBackgroundResource(R.drawable.main_theme_palace);
        }

        if(Cfg.TITLE==1){
            texttop.setBackgroundColor(Color.rgb(139,62,47));
            textbottom.setBackgroundColor(Color.rgb(139,62,47));
            findViewById(R.id.linear1).setBackgroundResource(R.drawable.theme_hutong);
        }

        if(Cfg.TITLE==2){

            findViewById(R.id.linear1).setBackgroundColor(Color.rgb(255,255,255));
        }

        if(Cfg.TITLE==3){
            texttop.setBackgroundColor(Color.rgb(255,106,106));
            textbottom.setBackgroundColor(Color.rgb(255,106,106));
            findViewById(R.id.linear1).setBackgroundResource(R.drawable.theme_delecious);
        }

        if(Cfg.TITLE==4){
            texttop.setBackgroundColor(Color.rgb(255,239,213));
            textbottom.setBackgroundColor(Color.rgb(255,239,213));
            findViewById(R.id.linear1).setBackgroundResource(R.drawable.theme_minsu);
        }

    }

    private void initView() {
        img =(ImageView)findViewById(R.id.weather);
        radioGroup = (RadioGroup) findViewById(R.id.radio_one);
        radioGroup.setOnCheckedChangeListener(this);
        texttop = (TextView)findViewById(R.id.main_text_top);
        textbottom = (TextView)findViewById(R.id.main_text_bottom);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checked) {

        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        if(center_frag!=null){
            fragmentTransaction.hide(center_frag);
        }
        if(translate_frag !=null){
            fragmentTransaction.hide(translate_frag);
        }

        if(schedule_frag!=null){
            fragmentTransaction.hide(schedule_frag);
        }

        if(mine_frag!=null){
            fragmentTransaction.hide(mine_frag);
        }

        switch(checked){
            case R.id.center:
                Log.i("fun",""+checked);
                if(center_frag==null)
                {
                    center_frag = new Center_Frag();
                    fragmentTransaction.add(R.id.frag_one,center_frag);
                    texttop.setText("Main");
                    texttop.setBackgroundColor(Color.rgb(255,255,255));
                }
                else {
                    fragmentTransaction.show(center_frag);
                    texttop.setText("Main");
                }
                break;

            case R.id.schedule:
                Log.i("fun",""+checked);
                if(schedule_frag==null)
                {
                    schedule_frag = new Schedule_Frag();
                    fragmentTransaction.add(R.id.frag_one,schedule_frag);
                    texttop.setText("Hodometer");
                    texttop.setBackgroundColor(Color.rgb(255,255,255));
                }
                else {
                    fragmentTransaction.show(schedule_frag);
                    texttop.setText("Hodometer");
                }
                break;

            case R.id.mine:
                if(mine_frag==null)
                {
                    mine_frag = new Mine_Frag();
                    fragmentTransaction.add(R.id.frag_one,mine_frag);
                    texttop.setText("Mine");
                    texttop.setBackgroundColor(Color.rgb(255,255,255));
                }
                else{
                    fragmentTransaction.show(mine_frag);
                    texttop.setText("Mine");
                }
                break;

            case R.id.translate:
                if(translate_frag ==null)
                {
                    translate_frag = new Translate_Frag();
                    fragmentTransaction.add(R.id.frag_one, translate_frag);
                    texttop.setText("Translate");
                }
                else {
                    fragmentTransaction.show(translate_frag);
                    texttop.setText("Translate");
                }
                break;
        }
        fragmentTransaction.commit();
        
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exitTwice();
        }
        return  false;
    }

    //点击两次退出的方法
    private void exitTwice() {
        Timer timer = null;
            if(isExit ==false){
                isExit = true;
                Toast.makeText(getApplicationContext(),"Click twice and exit app！", Toast.LENGTH_SHORT).show();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false;//取消退出
                    }
                },2000);
            }else{
                finish();
                System.exit(0);
            }
    }



    @Override
    protected void onPause() {
        super.onPause();
//        alertDialog();
    }

    //退出提示框
    private void alertDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("提示：");
        alertDialog.setMessage("是否退出程序？");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.show();
    }

}
