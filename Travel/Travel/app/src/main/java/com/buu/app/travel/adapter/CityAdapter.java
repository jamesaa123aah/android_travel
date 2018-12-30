package com.buu.app.travel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buu.app.travel.R;
import com.buu.app.travel.role.City;


import java.util.List;

/**
 * Created by bei on 2017/9/21.
 */

public class CityAdapter  extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private List<City> cities;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    class  ViewHolder extends  RecyclerView.ViewHolder{
        ImageView img;
        TextView text;

        public ViewHolder(final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.city_choice_img);
            text = (TextView) itemView.findViewById(R.id.city_choice_text);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemClickListener != null){
                        itemClickListener.onItemClick(itemView,getPosition());
                    }
                }
            });
        }



    }

    public CityAdapter(List<City> items){
        cities=items;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_choice_item,parent,false);
        CityAdapter.ViewHolder viewHolder  = new CityAdapter.ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder holder, int position) {
        City city = cities.get(position);
        holder.img.setImageBitmap(city.getImg());
        holder.text.setText(city.getText());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public interface ItemClickListener {

        /**
         * Item 普通点击
         */

        public void onItemClick(View view, int position);

        /**
         * Item 长按
         */

        public void onItemLongClick(View view, int position);

        /**
         * Item 内部View点击
         */

        public void onItemSubViewClick(View view, int position);
    }
}
