package com.buu.app.travel.MyView;

import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;

import com.buu.app.travel.R;
import com.buu.app.travel.Unique_Making;

/**
 * Created by king- on 2017/9/21.
 */

public class MyTextView extends AppCompatTextView {

    public MyTextView(Context context) {
        super(context);
        init();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setBackgroundResource(R.drawable.mtv_unselected);
        if(this.getText()==getContext().getString(R.string.all)){
            this.setSelected(true);
            this.setBackgroundResource(R.drawable.mtv_selected);
            final AppCompatTextView _this = this;
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_this.getParent() instanceof GridLayout) {
                        int x = ((GridLayout) _this.getParent()).getChildCount();
                        if (!_this.isSelected()) {
                            _this.setSelected(true);
                            _this.setTextColor(Color.WHITE);
                            _this.setBackgroundResource(R.drawable.mtv_selected);
                            for (int i = 1; i < x; i++) {
                                MyTextView gl = (MyTextView) ((GridLayout) _this.getParent()).getChildAt(i);
                                if (gl.isSelected()) {
                                    gl.setSelected(false);
                                    gl.setTextColor(Color.BLACK);
                                    gl.setBackgroundResource(R.drawable.mtv_unselected);
                                }
                            }
                        }
                    }
                    sendResult();
                }
            });
        }else {
            final AppCompatTextView _this = this;
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_this.getParent() instanceof GridLayout) {
                        MyTextView gl = (MyTextView) ((GridLayout) _this.getParent()).getChildAt(0);
                        if (gl.isSelected()) {
                            gl.setSelected(false);
                            gl.setTextColor(Color.BLACK);
                            gl.setBackgroundResource(R.drawable.mtv_unselected);
                        }
                        if (!_this.isSelected()) {
                            _this.setSelected(true);
                            _this.setTextColor(Color.WHITE);
                            _this.setBackgroundResource(R.drawable.mtv_selected);
                        } else {
                            _this.setSelected(false);
                            _this.setTextColor(Color.BLACK);
                            _this.setBackgroundResource(R.drawable.mtv_unselected);
                        }
                    }
                    sendResult();
                }
            });
        }
    }
    private void sendResult(){
        if(((GridLayout) getParent()).getChildAt(0).isSelected()){
            Message meg = new Message();
            meg.what = Unique_Making.SIZER;
            meg.obj = new boolean[]{true};
            Unique_Making.mHandler.sendMessage(meg);
        }else{
            int x = ((GridLayout) getParent()).getChildCount();
            boolean[] re = new boolean[x-1];
            for (int i = 1; i < x; i++) {
                View gl = ((GridLayout) getParent()).getChildAt(i);
                if (gl.isSelected()) {
                    re[i-1] = true;
                } else {
                    re[i-1] = false;
                }
            }
            Message meg = new Message();
            meg.what = Unique_Making.SIZER;
            meg.obj = re;
            Unique_Making.mHandler.sendMessage(meg);
        }
    }
}
