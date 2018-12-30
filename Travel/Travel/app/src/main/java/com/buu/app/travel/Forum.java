package com.buu.app.travel;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buu.app.travel.adapter.CommentAdapter;
import com.buu.app.travel.adapter.MainAdapter;
import com.buu.app.travel.role.Comment_Item;
import com.buu.app.travel.role.Main_Item;

import java.util.ArrayList;
import java.util.List;


public class Forum extends AppCompatActivity implements View.OnClickListener {

    private List<Main_Item> item = new ArrayList<>();
    private List<Comment_Item> data = new ArrayList<Comment_Item>();
    private String[] comment = new String[]{};
    private Resources resources;
    RecyclerView recyclerView;
    private ListView listview;
    private Button theme_btn1;
    private Button theme_btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        resources = this.getResources();
        comment = resources.getStringArray(R.array.comment);
        initData();
        initRecycleData();
        initView();


        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        MainAdapter adapter = new MainAdapter(item);
        recyclerView.setAdapter(adapter);

        CommentAdapter adapter1 = new CommentAdapter(getApplication(),R.layout.comment_item,data);
        listview.setAdapter(adapter1);

    }

    private void initView() {
        listview = (ListView)findViewById(R.id.forum_listview);
        recyclerView = (RecyclerView)findViewById(R.id.forum_recycler);
        theme_btn1 = (Button)findViewById(R.id.forum_btn1);
        theme_btn2 = (Button) findViewById(R.id.forum_btn2);
        theme_btn1.setOnClickListener(this);
        theme_btn2.setOnClickListener(this);

    }
    private void initRecycleData() {
        Main_Item main1= new Main_Item(R.drawable.main_mei3,"Beautiful",99,89,0);
        item.add(main1);

        Main_Item main2= new Main_Item(R.drawable.theme_delecious,"Eatting",498,9,0);
        item.add(main2);


        Main_Item main3= new Main_Item(R.drawable.main_mei5,"Look,Look",9,89,70);
        item.add(main3);

        Main_Item main4= new Main_Item(R.drawable.main_mei4,"Beauty of Palace",99,689,50);
        item.add(main4);
    }

    public void initData(){

        Comment_Item comment_item = new Comment_Item(R.mipmap.t1,"BeiBei","2017-11-8",comment[0]);
        data.add(comment_item);

        Comment_Item comment_item1 = new Comment_Item(R.mipmap.t2,"XiaoFu","2017-10-8",comment[1]);
        data.add(comment_item1);

        Comment_Item comment_item2 = new Comment_Item(R.mipmap.t3,"Chen","2017-9-8",comment[0]);
        data.add(comment_item2);

        Comment_Item comment_item3 = new Comment_Item(R.mipmap.t4,"HaiKun","2017-6-8",comment[0]);
        data.add(comment_item3);

        Comment_Item comment_item4 = new Comment_Item(R.mipmap.t5,"HaiKun","2017-6-8",comment[0]);
        data.add(comment_item4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forum_btn1:
                recyclerView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
                listview.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
                recyclerView.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"hah",Toast.LENGTH_SHORT).show();
                break;
            case R.id.forum_btn2:
                recyclerView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
                listview.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
                recyclerView.setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"qqqqq",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

}
