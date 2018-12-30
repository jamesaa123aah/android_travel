package com.buu.app.travel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Unique_Making_Choice extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_unique__making__choice);
        findViewById(R.id.theme_unique_making_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cfg.firstFragment = true;
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
