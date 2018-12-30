package com.buu.app.travel.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.baidu.mapapi.map.Text;
import com.buu.app.travel.R;
import com.buu.app.travel.role.Thememaking_Item;

import java.util.List;

/**
 * Created by xiaoqiang on 2017-08-31.
 */

public  class ThemeMakingAdapter extends  RecyclerView.Adapter<ThemeMakingAdapter.ViewHolder>{
    private List<Thememaking_Item> thememaking_items;

    static class  ViewHolder extends  RecyclerView.ViewHolder{
        ImageView img1,img2;
        TextView text1,text2,content1,content2,title1,title2;

        public ViewHolder(View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.main_thememaking_img1);
            img2 = (ImageView) itemView.findViewById(R.id.main_thememaking_img2);
            text1 = (TextView) itemView.findViewById(R.id.main_thememaking_text1);
            text2 = (TextView) itemView.findViewById(R.id.main_thememaking_text2);
            content1 = (TextView) itemView.findViewById(R.id.main_thememaking_Content1);
            content2 = (TextView) itemView.findViewById(R.id.main_thememaking_Content2);
            title1 = (TextView) itemView.findViewById(R.id.main_thememaking_item_title1);
            title2 = (TextView) itemView.findViewById(R.id.main_thememaking_item_title2);
        }
    }

    public ThemeMakingAdapter(List<Thememaking_Item> items){
        thememaking_items=items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_thememaking_item,parent,false);
        ViewHolder viewHolder  = new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Thememaking_Item thememakingItem = thememaking_items.get(position);
        holder.img1.setImageResource(thememakingItem.getImg1());
        holder.img2.setImageResource(thememakingItem.getImg2());
        holder.text1.setText(thememakingItem.getText1());
        holder.text2.setText(thememakingItem.getText2());
        holder.content1.setText(thememakingItem.getContent1());
        holder.content2.setText(thememakingItem.getContent2());
        holder.title1.setText(thememakingItem.getTitle1());
        holder.title2.setText(thememakingItem.getTitle2());
    }



    @Override
    public int getItemCount() {
        return thememaking_items.size();
    }

    public  void addItem(Thememaking_Item content, int position) {

        thememaking_items.add(position, content);
        notifyItemInserted(position); //Attention!
    }
}