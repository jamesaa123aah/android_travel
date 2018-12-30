package com.buu.app.travel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.buu.app.travel.adapter.FirstInAdapter;

public class FirstInActivity extends BaseActivity {
    public void actionStart(Context context, FirstInAdapter adapter){
        this.adapter = adapter;
        Intent start = new Intent(context,FirstInActivity.class);
        context.startActivity(start);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            back();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void back(){

    }
    /////////////////////
    private static FirstInAdapter adapter;
    private int DX = 0;
    ////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_in);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.first_in_rec_view);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.first_in_radio_group);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    check(recyclerView);
                }
                return false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                DX += dx;
                selectRadio(radioGroup,recyclerView);
            }
        });

    }
    private void check(RecyclerView recyclerView){
        final LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
        int x = DX % recyclerView.getWidth();
        if (x != 0) {
            int p =lm.findFirstVisibleItemPosition();
            if (x < recyclerView.getWidth() / 2) {
                recyclerView.smoothScrollToPosition(p);
            } else {
                recyclerView.smoothScrollToPosition(p + 1);
            }
        }
    }
    private void selectRadio(RadioGroup radioGroup,RecyclerView recyclerView){
        int s = (int) Math.floor(DX/recyclerView.getWidth());
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(s*2+1);
        if(!radioButton.isChecked()){
            radioButton.setChecked(true);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }
}
