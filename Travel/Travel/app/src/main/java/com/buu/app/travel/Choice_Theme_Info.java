package com.buu.app.travel;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;

public class Choice_Theme_Info extends BaseActivity {

    public void startaction(Context context, String name, String img) {
        this.name = name;
        this.img = img;
        Intent intent = new Intent(context, Choice_Theme_Info.class);
        context.startActivity(intent);
    }

    private static final String TAG = "Choice_Theme_Info";
    public static String name;
    public static String img;
    private TextView title,content,sure,cancle;
    private ImageView imgPhoto;
    private InputStream inputStream = null;
    private Context context;
    private Bitmap bt;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_choice__theme__info);
        res = getResources();
        context = this;
        initView();
        title.setText(name);
        imgPhoto.setImageBitmap(getPhoto());
        content = (TextView)findViewById(R.id.theme_choice_info_content);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private  void initView(){
        title = (TextView) findViewById(R.id.theme_choice_info_text);
        imgPhoto = (ImageView) findViewById(R.id.theme_choice_info_img);
        sure = (TextView)findViewById(R.id.theme_choice_info_sure);
        cancle = (TextView)findViewById(R.id.theme_choice_info_cancle);
    }

    private Bitmap getPhoto() {
        try {
            inputStream = context.getAssets().open("img/"+img);
        } catch (Exception o) {
            o.printStackTrace();
        }
        bt = BitmapFactory.decodeStream(inputStream);

        return bt;
    }
}