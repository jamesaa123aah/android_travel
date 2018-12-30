package com.buu.app.travel.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.buu.app.travel.R;
import com.buu.app.travel.Unique_Making;
import com.buu.app.travel.role.Unique_Item;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoqiang on 2017-09-15.
 */

public class UniqueAdapter extends ArrayAdapter<Unique_Item> {
    private int myRescource;


    public UniqueAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Unique_Item> objects) {
        super(context, resource, objects);
        myRescource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Unique_Item unique_item = getItem(position);
        ViewHolder viewHolder;
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(myRescource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.img_center = (ImageView)view.findViewById(R.id.unique_img);
            viewHolder.img_comment = (TextView) view.findViewById(R.id.unique_comment_text);
            viewHolder.img_look = (TextView) view.findViewById(R.id.unique_look_text);
            viewHolder.img_praise = (TextView) view.findViewById(R.id.unique_praise_text);
            viewHolder.text_introduce = (TextView)view.findViewById(R.id.unique_introduce);
            viewHolder.text_title = (TextView)view.findViewById(R.id.unique_title);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        InputStream bm = null;
        try {
            bm = getContext().getAssets().open("img/"+unique_item.getImg());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(bm);
        viewHolder.img_center.setImageBitmap(bitmap);
        viewHolder.img_comment.setText(""+unique_item.getComment());
        viewHolder.img_praise.setText(""+unique_item.getPraise());
        viewHolder.img_look.setText(""+unique_item.getLook());
        viewHolder.text_introduce.setText(unique_item.getIntroduce());
        viewHolder.text_title.setText(unique_item.getTitle());

        return view;
    }

    class ViewHolder{
        ImageView img_center;
        TextView img_look;
        TextView img_praise;
        TextView img_comment;
        TextView  text_title;
        TextView text_introduce;
    }
}
