package com.buu.app.travel;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.Text;
import com.buu.app.travel.adapter.CommentAdapter;
import com.buu.app.travel.adapter.MainAdapter;
import com.buu.app.travel.role.Comment_Item;
import com.buu.app.travel.role.Main_Item;
import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;
import java.util.List;

public class Choice_Theme extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "Choice_Theme";
    RollPagerView rollPagerView;
    RecyclerView recyclerView;
    private List<Main_Item> item = new ArrayList<>();
    private TextView selfText;
    private TextView btn1;
    private TextView btn2;
    private TextView btn3;
    private View container1;
    private View container2;
    private Button theme_btn1;
    private Button theme_btn2;
    private ListView listview;
    private List<Comment_Item> data = new ArrayList<Comment_Item>();
    private String[] comment = new String[]{};
    private Resources resources;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_choice__theme);
        initView();

        initRecycleData();

        resources = this.getResources();
        comment = resources.getStringArray(R.array.comment);

        initData();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        MainAdapter adapter = new MainAdapter(item);

        adapter.setItemClickListener(new MainAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

                Log.d(TAG, "onItemClick: "+postion);
            }

            @Override
            public void onItemLongClick(View view, int postion) {

            }

            @Override
            public void onItemSubViewClick(View view, int postion) {

            }


        });
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View view, MotionEvent m) {
//                if(m.getAction() == MotionEvent.ACTION_DOWN){
//                    recyclerView.requestDisallowInterceptTouchEvent(false);
//                }
//                else{
//                    recyclerView.requestDisallowInterceptTouchEvent(true);
//                }
//
//                return true;
//            }
//        });
        recyclerView.setAdapter(adapter);

        selfText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Choice_Theme.this,Unique_Making.class));
            }
        });

        CommentAdapter adapter1 = new CommentAdapter(getApplication(),R.layout.comment_item,data);
        listview.setAdapter(adapter1);


//       listview.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        return true;
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });
    }

    private void initView() {
        scrollView = (ScrollView)findViewById(R.id.choice_theme_scrollview) ;
        listview = (ListView)findViewById(R.id.theme_choice_theme_listview);
        container1 =LayoutInflater.from(getApplication()).inflate(R.layout.linearlayout_item, null);
        container2 = LayoutInflater.from(getApplication()).inflate(R.layout.linearlayout_item,null);
        theme_btn1 = (Button)findViewById(R.id.theme_choic_theme_btn1);
        theme_btn2 = (Button) findViewById(R.id.theme_choice_theme_btn2);
        recyclerView = (RecyclerView)findViewById(R.id.theme_choice_recycler);
        selfText = (TextView)findViewById(R.id.theme_choice_theme_self);
        btn1 = (TextView)findViewById(R.id.choice_mingsheng);
        btn2 = (TextView)findViewById(R.id.choice_guji);
        btn3 = (TextView) findViewById(R.id.choice_gouwu);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

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

        Comment_Item comment_item2 = new Comment_Item(R.mipmap.t3,"Chen","2017-9-8",comment[2]);
        data.add(comment_item2);

        Comment_Item comment_item3 = new Comment_Item(R.mipmap.t4,"HaiKun","2017-6-8",comment[3]);
        data.add(comment_item3);

        Comment_Item comment_item4 = new Comment_Item(R.mipmap.t5,"HaiKun","2017-6-8",comment[0]);
        data.add(comment_item4);

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.choice_mingsheng:
                Log.d(TAG, "onClick: "+view.isClickable());
                break;
            case R.id.theme_choic_theme_btn1:
                recyclerView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in));
                listview.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out));
                recyclerView.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"hah",Toast.LENGTH_SHORT).show();
                break;
            case R.id.theme_choice_theme_btn2:
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
