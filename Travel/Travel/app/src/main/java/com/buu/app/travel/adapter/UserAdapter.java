package com.buu.app.travel.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.buu.app.travel.R;
import com.buu.app.travel.role.User_Item;

import java.util.List;

/**
 * Created by xiaoqiang on 2017-09-01.
 */

public class UserAdapter extends ArrayAdapter<User_Item> {

    private int myResource;
    public UserAdapter(@NonNull Context context, @LayoutRes int resourceId, @NonNull List<User_Item> objects) {
        super(context, resourceId, objects);
        myResource = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User_Item user_item = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(myResource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)view.findViewById(R.id.calendar_user_item_title);
            viewHolder.text1 = (TextView)view.findViewById(R.id.calendar_user_text1);
            viewHolder.text2 = (TextView)view.findViewById(R.id.calendar_user_text2);
            viewHolder.content1 = (TextView)view.findViewById(R.id.calendar_user_Content1);
            viewHolder.content2 = (TextView)view.findViewById(R.id.calendar_user_Content2);
            viewHolder.img1 = (ImageView)view.findViewById(R.id.calendar_user_img1);
            viewHolder.img2 = (ImageView)view.findViewById(R.id.calendar_user_img2);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.img1.setImageResource(user_item.getImg1());
        viewHolder.img2.setImageResource(user_item.getImg2());
        viewHolder.text1.setText(user_item.getText1());
        viewHolder.text2.setText(user_item.getText2());
        viewHolder.content1.setText(user_item.getContent1());
        viewHolder.content2.setText(user_item.getContent2());
        viewHolder.title.setText(user_item.getTitle());

        return  view;
    }

    class ViewHolder{

        ImageView img1,img2;
        TextView text1,text2,content1,content2,title;
    }
}
