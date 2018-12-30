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
import com.buu.app.travel.role.Theme_Item;

import java.util.List;

/**
 * Created by xiaoqiang on 2017-08-11.
 */

public class ThemeAdapter extends ArrayAdapter<Theme_Item> {
    private static final String TAG = "ThemeAdapter";
    private int resourceId;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Theme_Item theme_item = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView ==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.themeImg = (ImageView)view.findViewById(R.id.theme_list_img);
            viewHolder.themeTitle = (TextView)view.findViewById(R.id.theme_list_title);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        ImageView themeImg = (ImageView)view.findViewById(R.id.theme_list_img);
        TextView themeText = (TextView)view.findViewById(R.id.theme_list_title);
        themeImg.setImageResource(theme_item.getImageId());
        themeText.setText(theme_item.getTitle());
        return view;
    }

    public ThemeAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Theme_Item> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    class  ViewHolder{
        ImageView themeImg;
        TextView themeTitle;
    }
}
