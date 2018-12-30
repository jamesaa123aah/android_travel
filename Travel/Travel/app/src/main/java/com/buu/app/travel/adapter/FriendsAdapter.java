package com.buu.app.travel.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buu.app.travel.ChatActivity;
import com.buu.app.travel.R;
import com.buu.app.travel.role.Friend;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by king- on 2017/10/23.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Friend> friends;
    private Animation animation;
    private ArrayList<ViewHolder> viewHolders;

    public FriendsAdapter(ArrayList<Friend> friends) {
        this.friends = friends;
        viewHolders = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView portrait;
        TextView mesCount;
        TextView name;
        TextView lastM;
        LinearLayout edits;
        LinearLayout pMes;
        Button show;
        Button join;
        Button call;
        Button space;
        Button kTop;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            portrait = (ImageView) itemView.findViewById(R.id.portrait);
            mesCount = (TextView) itemView.findViewById(R.id.mes_count);
            name = (TextView) itemView.findViewById(R.id.name);
            lastM = (TextView) itemView.findViewById(R.id.last_message);
            edits = (LinearLayout) itemView.findViewById(R.id.edits);
            pMes = (LinearLayout) itemView.findViewById(R.id.personal_message);
            show = (Button) itemView.findViewById(R.id.show_edits);
            join = (Button) itemView.findViewById(R.id.join);
            call = (Button) itemView.findViewById(R.id.call);
            space = (Button) itemView.findViewById(R.id.space);
            kTop = (Button) itemView.findViewById(R.id.keep_top);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        final View finalView = view;
        //显示操作
        viewHolder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animation == null) {
                    animation = new ScaleAnimation(finalView.getWidth(), finalView.getWidth(), 0, finalView.getHeight());
                    animation.setDuration(200);
                }
                if (viewHolder.edits.getVisibility() == View.VISIBLE) {
                    viewHolder.edits.setVisibility(View.GONE);
                } else {
                    goneAllEdits();
                    viewHolder.edits.setVisibility(View.VISIBLE);
                    if (animation != null) {
                        viewHolder.edits.startAnimation(animation);
                    }
                }
            }
        });
        viewHolders.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Friend friend = friends.get(position);
        holder.name.setText(friend.getName());
        holder.lastM.setText(friend.getLastMes());
        if(friend.getMegCount()==0){
            holder.mesCount.setVisibility(View.GONE);
        }else {
            holder.mesCount.setVisibility(View.VISIBLE);
            holder.mesCount.setText("" + friend.getMegCount());
        }
        if(friend.isOnline()){
            holder.show.setSelected(true);
            holder.join.setVisibility(View.VISIBLE);
            if(friend.isTravel()){
                holder.join.setVisibility(View.VISIBLE);
            }else {
                holder.join.setVisibility(View.GONE);
            }
        }else {
            holder.show.setSelected(false);
            holder.call.setVisibility(View.GONE);
            holder.join.setVisibility(View.GONE);
        }
        if(friend.getPUrl().equals("service")){
            holder.show.setVisibility(View.GONE);
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(mContext.getAssets().open("png/service.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.portrait.setImageBitmap(bitmap);
        }else {
            holder.show.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(friend.getPUrl()).into(holder.portrait);
        }
        //交谈
        holder.pMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goneAllEdits();
                Intent intent = new Intent(mContext,ChatActivity.class);
                intent.putExtra("name",friend.getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    private ArrayList<ViewHolder> getViewHolders() {
        return viewHolders;
    }

    public void goneAllEdits() {
        for (ViewHolder holder : viewHolders) {
            if (holder.edits.getVisibility() == View.VISIBLE) {
                holder.edits.setVisibility(View.GONE);
            }
        }
    }
}
