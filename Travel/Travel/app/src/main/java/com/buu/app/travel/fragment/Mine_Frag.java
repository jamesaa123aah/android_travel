package com.buu.app.travel.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buu.app.travel.Cfg;
import com.buu.app.travel.FriendsActivity;
import com.buu.app.travel.Insurance;
import com.buu.app.travel.MainActivity;
import com.buu.app.travel.R;
import com.buu.app.travel.adapter.MineAdapter;
import com.buu.app.travel.role.Mine_Item;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
/**
 * Created by xiaoqiang on 2017-09-03.
 */

public class Mine_Frag extends Fragment {
    private static final String TAG = "Mine_Frag";
    public static final  int UPDATE_TEXT = 1;
    List<Mine_Item> mineItems = new ArrayList<>();
    ListView mineListView;
    Context context;
    //菜单
    private PopupMenu pop;
    TextView topTextView;
    TextView bottomTextView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine,container,false);
        initView();
        initData();
        context = getActivity();
        MineAdapter mineadapter = new MineAdapter(getContext(), R.layout.mine_item,mineItems);
        mineListView = (ListView)view.findViewById(R.id.mine_list);
        mineListView.setAdapter(mineadapter);
        setHasOptionsMenu(true);
        mineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){

                    case 1:
                        pop = new PopupMenu(getActivity(),mineListView.getChildAt(i-mineListView.getFirstVisiblePosition()) , Gravity.RIGHT);
                        pop.inflate(R.menu.style);
                        pop.show();
                        pop.setOnMenuItemClickListener(menuItemClick);
                        break;
                    case 2:
                        Intent intent = new Intent(getContext(), FriendsActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Intent intent1 = new Intent(getContext(), FriendsActivity.class);
                        startActivity(intent1);
                        break;

                    case 4:
                        Intent intent2 = new Intent(getContext(), Insurance.class);
                        startActivity(intent2);
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }

    private void initView() {
        topTextView = (TextView) view.findViewById(R.id.main_text_top);
        bottomTextView = (TextView) view.findViewById(R.id.main_text_bottom);
    }

    PopupMenu.OnMenuItemClickListener menuItemClick = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch(item.getItemId()){
                case R.id.style_black:
                    Cfg.TITLE = 1;
                    Toast.makeText(context,"Setting Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context,MainActivity.class));
                    break;
                case R.id.style_light:
                    Cfg.TITLE = 4;
                    Toast.makeText(context,"Setting Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context,MainActivity.class));
                    break;

                case R.id.style_binguan:
                    Cfg.TITLE = 2;
                    Toast.makeText(context,"Setting Success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context,MainActivity.class));
                    break;
                default:
                    break;
            }
            return  true;
        }
    };
    private void initData()
    {
        Mine_Item map = new Mine_Item(R.drawable.mine_map,"Setting style",R.drawable.jiantou);
        mineItems.add(map);

        Mine_Item set = new Mine_Item(R.drawable.mine_setting,"Setting style",R.drawable.jiantou);
        mineItems.add(set);

        Mine_Item server = new Mine_Item(R.mipmap.mine_service,"Contact Custom Service",R.drawable.jiantou);
        mineItems.add(server);

        Mine_Item friend = new Mine_Item(R.mipmap.mine_friend,"Contact friend",R.drawable.jiantou);
        mineItems.add(friend);

        Mine_Item insurance = new Mine_Item(R.mipmap.mine_insurance,"Making Insurance",R.drawable.jiantou);
        mineItems.add(insurance);

    }
}
