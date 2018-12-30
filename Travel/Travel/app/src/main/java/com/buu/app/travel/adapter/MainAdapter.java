package com.buu.app.travel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.buu.app.travel.R;
import com.buu.app.travel.role.Main_Item;

import java.util.List;

/**
 * Created by xiaoqiang on 2017-09-09.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Main_Item> item;
    private ItemClickListener mItemClickListener;

    public void setItemClickListener(ItemClickListener mItemClickListener) {

        this.mItemClickListener = mItemClickListener;
    }
    class ViewHolder extends  RecyclerView.ViewHolder {
        ImageView imgBg,imgOff;
        TextView title,user,zan;


        public ViewHolder(final View itemView) {
            super(itemView);
            imgBg = (ImageView) itemView.findViewById(R.id.main_item_img);
            imgOff= (ImageView) itemView.findViewById(R.id.main_item_imgOff) ;
            title = (TextView)itemView.findViewById(R.id.main_item_title);
            user = (TextView)itemView.findViewById(R.id.main_item_user);
            zan = (TextView) itemView.findViewById(R.id.main_item_zan);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (null != mItemClickListener) {
                        mItemClickListener.onItemClick(itemView, getPosition());
                    }

                }
            });
        }

    }

    public  MainAdapter(List<Main_Item> main_items){
        item = main_items;
    }
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        Main_Item  main_item = item.get(position);
        holder.imgBg.setImageResource(main_item.getImgId());
        holder.title.setText(main_item.getTitle());
        holder.user.setText(String.valueOf(main_item.getZan()));
        holder.zan.setText(String.valueOf(main_item.getZan()));
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public interface ItemClickListener {

        /**
         * Item 普通点击
         */

        public void onItemClick(View view, int postion);

        /**
         * Item 长按
         */

        public void onItemLongClick(View view, int postion);

        /**
         * Item 内部View点击
         */

        public void onItemSubViewClick(View view, int postion);
    }

}
