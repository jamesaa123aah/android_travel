package com.buu.app.travel;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;

public class MainInfo extends AppCompatActivity {
    public void actionStart(Context context,int position){
        this.position = position;
        Intent start = new Intent(context,MainInfo.class);
        context.startActivity(start);

    }

    private static final String TAG = "MainInfo";
    ImageView img;
    TextView text;
    Resources resources;

    private static int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_info);
        Log.d(TAG, "onCreate: "+position);
        initView();
        initData();

    }

    private void initData() {
        resources = getResources();
        String[] date = resources.getStringArray(R.array.main_content);
        switch (position){
            case 0:
                img.setImageResource(R.drawable.mei1);
                text.setText(date[0]);
                break;
            case 1:
                img.setImageResource(R.drawable.mei2);
                text.setText(date[1]);
                break;
            case 2:
                img.setImageResource(R.drawable.mei3);
                text.setText(date[2]);
                break;
            case 3:
                img.setImageResource(R.drawable.mei4);
                break;
            default:
                break;
        }
    }

    private void initView() {
        img = (ImageView)findViewById(R.id.maininfo_img);
        text = (TextView)findViewById(R.id.maininfo_text);
    }
}
