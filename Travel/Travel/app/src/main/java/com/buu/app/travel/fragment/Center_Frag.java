package com.buu.app.travel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.buu.app.travel.Aid;
import com.buu.app.travel.Bicycle;
import com.buu.app.travel.Canteen;
import com.buu.app.travel.Choice_City;
import com.buu.app.travel.Choice_Theme;
import com.buu.app.travel.Forum;
import com.buu.app.travel.Hotel;
import com.buu.app.travel.Pay;
import com.buu.app.travel.Phone;
import com.buu.app.travel.MainInfo;
import com.buu.app.travel.R;
import com.buu.app.travel.Rate;

import com.buu.app.travel.Vr;
import com.buu.app.travel.Wether;
import com.buu.app.travel.adapter.MainAdapter;
import com.buu.app.travel.role.Main_Item;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiaoqiang on 2017-06-25.
 */

public class Center_Frag extends Fragment implements View.OnClickListener{
    private static final String TAG = "Center_Frag";
    LinearLayout linearLayout;
    View mView;
    RollPagerView rollPagerView;
    RecyclerView recyclerView;
    private ImageView btBg;

    private List<Main_Item> item = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.center,null);
        mView = view;
        rollPagerView = (RollPagerView) view.findViewById(R.id.rollviewpager);
        rollPagerViewsSet();
        init();//背景图初始化
        initRecycleDate();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        MainAdapter adapter = new MainAdapter(item);
        adapter.setItemClickListener(new MainAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.d(TAG, "onItemClick: "+postion);
                new MainInfo().actionStart(getActivity(),postion);
            }

            @Override
            public void onItemLongClick(View view, int postion) {

            }

            @Override
            public void onItemSubViewClick(View view, int postion) {

            }
        });
        recyclerView.setAdapter(adapter);
        initListener();
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"haha",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void initRecycleDate() {
        Main_Item main1= new Main_Item(R.drawable.mei1,"BU DA LA GONG",99,89,0);
        item.add(main1);

        Main_Item main2= new Main_Item(R.drawable.mei2,"XI AN CHENG QIANG",498,9,0);
        item.add(main2);

        Main_Item main3= new Main_Item(R.drawable.mei3,"WAI TAN",9,89,70);
        item.add(main3);

        Main_Item main4= new Main_Item(R.drawable.mei4,"HAUNG CHENG",99,689,50);
        item.add(main4);


    }

    @Override
    public void onStart() {
        super.onStart();

    }



    private void rollPagerViewsSet() {
        rollPagerView.setPlayDelay(2000);//*播放间隔
        rollPagerView.setAnimationDurtion(500);//透明度
        rollPagerView.setAdapter(new rollViewpagerAdapter());//配置适配器

    }
    private void init(){
        recyclerView = (RecyclerView)mView.findViewById(R.id.main_recyclerview);
    }
    private void initListener(){
        mView.findViewById(R.id.linear1);
        mView.findViewById(R.id.vr_map).setOnClickListener(this);
        mView.findViewById(R.id.main_page).setOnClickListener(this);
        mView.findViewById(R.id.traffic).setOnClickListener(this);
        mView.findViewById(R.id.exchange_rate).setOnClickListener(this);
        mView.findViewById(R.id.hotel).setOnClickListener(this);
//        mView.findViewById(R.id.contact).setOnClickListener(this);
        mView.findViewById(R.id.aid).setOnClickListener(this);
        mView.findViewById(R.id.pay).setOnClickListener(this);
        mView.findViewById(R.id.forum).setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.vr_map:
                startActivity(new Intent(getActivity(), Vr.class));
                break;

            case R.id.main_page:
                startActivity(new Intent(getActivity(),Choice_City.class));
                break;

//            case R.id.contact:
//                startActivity(new Intent(getActivity(),Contact.class));
            case R.id.traffic:
                startActivity(new Intent(getActivity(),Bicycle.class));
                break;

            case R.id.exchange_rate:
                startActivity(new Intent(getActivity(),Rate.class));
                break;

            case R.id.hotel:
                startActivity(new Intent(getActivity(),Hotel.class));
                break;


            case R.id.aid:
                Intent fun5= new Intent(getActivity(),Aid.class);
                startActivity(fun5);
                break;

            case R.id.forum:
                Intent fun6= new Intent(getActivity(),Forum.class);
                startActivity(fun6);
                break;

            case R.id.pay:
                Intent fun4 = new Intent(getContext(),Pay.class);
                startActivity(fun4);
                break;

            default:
                break;


        }
    }

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if(motionEvent.getAction()== MotionEvent.ACTION_DOWN){
//            switch(view.getId()){
//                case R.id.vr_map:
//                    btBg.setImageResource(R.drawable.lbt_1);
//                    break;
//
//                case R.id.main_page:
//                    btBg.setImageResource(R.drawable.lbt_2);
//                    break;
//
//                case R.id.traffic:
//                    btBg.setImageResource(R.drawable.lbt_3);
//                    break;
//                case R.id.exchange_rate:
//                    btBg.setImageResource(R.drawable.lbt_5);
//                    break;
//                case R.id.dining_room:
//                    btBg.setImageResource(R.drawable.lbt_4);
//                    break;
//            }
//        }
//        if(motionEvent.getAction()== MotionEvent.ACTION_UP){
//            btBg.setImageResource(R.drawable.lbt_0);
//        }
//        return false;
//    }

    private class rollViewpagerAdapter extends StaticPagerAdapter {

        private  int[] res = {
                R.drawable.main_mei1,
                R.drawable.main_mei2,
                R.drawable.main_mei3,
                R.drawable.main_mei4
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView imageView=new ImageView(container.getContext());
            imageView.setImageResource(res[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return imageView;
        }

        @Override
        public int getCount() {
            return res.length;
        }
    }


}
