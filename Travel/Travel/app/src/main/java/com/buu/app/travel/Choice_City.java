package com.buu.app.travel;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.buu.app.travel.adapter.CityAdapter;
import com.buu.app.travel.role.City;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Choice_City extends AppCompatActivity {

    private static final String TAG = "Choice_City";
    //界面
    private RecyclerView recyclerView;
    private TextView textView;
    private EditText editText;
    private Resources res;
    private Context context;
    private Button btn;
    //数据
    List<City> cityAdd = new ArrayList<City>();
    private String[] cityString = new String[]{};
    private City city;
    private InputStream inputStream = null;
    private List<Bitmap> bitmap = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_choice__city);
        context = this;
        res = this.getResources();
        initView();
        initData();

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(
                2,StaggeredGridLayoutManager.VERTICAL
        );
        recyclerView.setLayoutManager(manager);
        CityAdapter adapter = new CityAdapter(cityAdd);
        recyclerView.setAdapter(adapter);

        adapter.setItemClickListener(new CityAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick: :"+cityAdd.get(position).getText());
                startActivity(new Intent(Choice_City.this,Choice_Theme.class));
            }

            @Override
            public void onItemLongClick(View view, int postion) {

            }

            @Override
            public void onItemSubViewClick(View view, int postion) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTopoaition();
            }
        });
    }

    private void initView() {
        recyclerView = (RecyclerView)findViewById(R.id.theme_choice_recycle);
        textView = (TextView)findViewById(R.id.theme_choice_beijing);
        editText = (EditText)findViewById(R.id.theme_choice_edit);
        btn = (Button)findViewById(R.id.theme_choice_find);
        findViewById(R.id.theme_choice_beijing).setOnClickListener(listener);
        findViewById(R.id.theme_choice_aomen).setOnClickListener(listener);
        findViewById(R.id.theme_choice_yunnan).setOnClickListener(listener);
        findViewById(R.id.theme_choice_tianjin).setOnClickListener(listener);
        findViewById(R.id.theme_choice_xianggang).setOnClickListener(listener);
        findViewById(R.id.theme_choice_shanghai).setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Choice_City.this.startActivity(new Intent(Choice_City.this,Unique_Making_Choice.class));
        }
    };
    private void initData() {
        for (int i = 1; i < 35; i++) {
            try {
                inputStream = context.getAssets().open("imgCity/city_1("+i+").jpg");
//                    if(i<10) {
//                        inputStream = context.getAssets().open("imgCity/city_0" + i+".jpg");
//                    }
//                    else
//                    {
//                        inputStream = context.getAssets().open("imgCity/city_"+i+".jpg");
//                    }
            } catch (Exception o) {
                o.printStackTrace();
            }

            bitmap.add(BitmapFactory.decodeStream(inputStream));
        }

        cityString = res.getStringArray(R.array.city);
        for (int i = 0; i < cityString.length; i++) {
            city = new City(bitmap.get(i),cityString[i]);
            cityAdd.add(city);
        }
    }

    private void moveTopoaition(){
        for (int i = 0; i < cityAdd.size(); i++) {
            if(editText.getText().toString() == cityAdd.get(i).getText())
            {
                recyclerView.scrollToPosition(i);
            }
        }
    }


}
