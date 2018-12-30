package com.buu.app.travel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xys.libzxing.zxing.activity.CaptureActivity;

public class Pay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
    }

    public void scanner(View view){
        startActivityForResult(new Intent(Pay.this, CaptureActivity.class), 0);
    }
}
