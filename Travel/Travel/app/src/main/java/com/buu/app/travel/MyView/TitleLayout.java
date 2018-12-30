package com.buu.app.travel.MyView;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.buu.app.travel.R;


/**
 * Created by xiaoqiang on 2017-08-14.
 */

public class TitleLayout extends LinearLayout {
    Button btn_back;
    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.myview_title,this);
        btn_back = (Button)findViewById(R.id.myview_title_back);
        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });
    }
}
