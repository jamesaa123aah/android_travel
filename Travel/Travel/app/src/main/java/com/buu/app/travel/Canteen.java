package com.buu.app.travel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoqiang on 2017-08-21.
 */

public class
Canteen extends Activity {

    private int[] imgID = {
        R.drawable.canteen_a,R.drawable.canteen_b,R.drawable.cateen_c,
            R.drawable.cateen_d,R.drawable.canteen_e,R.drawable.canteen_f
    };

    ListView listView ;
    List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_canteen);

        initView();
        initData();
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data,R.layout.main_canteen_item,
                new String[]{"imgID"},
                new int[]{R.id.canteen_img
        });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Canteen.this,CanteenInfo.class);
                intent.putExtra("index",""+i);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        for (int i = 0; i < imgID.length; i++) {

            Map<String,Object> list = new HashMap<String,Object>();
            list.put("imgID",imgID[i]);
            data.add(list);
        }

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.main_canteen_list);
    }
}
