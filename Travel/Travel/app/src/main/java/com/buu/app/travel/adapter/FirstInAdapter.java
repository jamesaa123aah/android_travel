package com.buu.app.travel.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import com.buu.app.travel.R;
import com.buu.app.travel.role.FirstInItem;

import java.util.ArrayList;

/**
 * Created by king- on 2017/8/3.
 */

public abstract class FirstInAdapter extends RecyclerView.Adapter<FirstInAdapter.ViewHolder>{
    public static final int NORMAL_PAGE = 0;
    public static final int ENTER_PAGE = 1;
    private ArrayList<FirstInItem> inItems;
    public FirstInAdapter(ArrayList<FirstInItem> inItems){
        this.inItems = inItems;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView img;
        Button enter;
        Button jump;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            img = (ImageView) itemView.findViewById(R.id.first_in_img);
            enter = (Button) itemView.findViewById(R.id.first_in_bt_enter);
            jump = (Button) itemView.findViewById(R.id.first_in_bt_jump);
        }
    }
    @Override
    public FirstInAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_in_item,parent,false);
       final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEnter();
            }
        });
        viewHolder.jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEnter();
            }
        });
        if(viewType==ENTER_PAGE){
            viewHolder.enter.setVisibility(View.VISIBLE);
        }else {
            viewHolder.jump.setVisibility(View.VISIBLE);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FirstInItem inItem = inItems.get(position);
        holder.img.setImageResource(inItem.imageId);
    }
    @Override
    public int getItemCount() {
        return inItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if(position==inItems.size()-1){
            type = ENTER_PAGE;
        }else {
            type = NORMAL_PAGE;
        }
        return type;
    }

    public abstract void onEnter();
}
