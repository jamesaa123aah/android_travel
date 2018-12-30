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
import com.buu.app.travel.role.Mine_Item;

import java.util.List;

/**
 * Created by xiaoqiang on 2017-08-16.
 */

public class MineAdapter extends ArrayAdapter<Mine_Item> {
    private int myRecsource;
    public MineAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Mine_Item> objects) {
        super(context, resource, objects);
        myRecsource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Mine_Item mine_item = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView ==null){
            view = LayoutInflater.from(getContext()).inflate(myRecsource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mine_img_Header = (ImageView)view.findViewById(R.id.mine_list_imgHeader);
            viewHolder.mine_img_Detail = (ImageView)view.findViewById(R.id.mine_list_imgDetail);
            viewHolder.mine_text = (TextView)view.findViewById(R.id.mine_list_text);
            view.setTag(viewHolder);
        }
        else {

            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.mine_img_Header.setImageResource(mine_item.getImgHeader());
        viewHolder.mine_img_Detail.setImageResource(mine_item.getImgDetail());
        viewHolder.mine_text.setText(mine_item.getText());

        return  view;
    }

    class ViewHolder{
        ImageView mine_img_Header;
        ImageView mine_img_Detail;
        TextView mine_text;
    }
}
