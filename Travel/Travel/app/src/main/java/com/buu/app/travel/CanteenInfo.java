package com.buu.app.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class CanteenInfo extends AppCompatActivity {
    private static final String TAG = "CanteenInfo";
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_canteen_info);

    }


    @Override
    protected void onStart() {
        super.onStart();
        imageView = (ImageView) findViewById(R.id.main_canteen_img);
        Intent intent = getIntent();
        String data = intent.getStringExtra("index");
        Log.d(TAG,""+ data);

        if (data.equals("0")) {
            imageView.setImageResource(R.drawable.canteen_b_detail);
        }

        else if (data.equals("1")) {
            imageView.setImageResource(R.drawable.canteen_a_detail);
        }

        else if (data.equals("2")) {
            imageView.setImageResource(R.drawable.canteen_c_detail);
        }

        else if (data.equals("3")) {
            imageView.setImageResource(R.drawable.canteen_d_detail);
        }

        else if (data.equals("4")) {
            imageView.setImageResource(R.drawable.canteen_e_detail);
        }

        else{
            imageView.setImageResource(R.drawable.canteen_f_detail);
        }

    }
}