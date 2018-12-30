package com.buu.app.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buu.app.travel.R;
import com.buu.app.travel.role.Msg;

import java.util.ArrayList;

/**
 * Created by king- on 2017/4/30.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{
    private ArrayList<Msg> msgList;
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        if(msg.getType()==Msg.TYPE_RECEIVED){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            if(msg.isImg()){
                holder.rightImg.setImageBitmap(msg.getBitmap());
            }else {
                holder.leftMsg.setText(msg.getContent());
            }
        }else if(msg.getType()==msg.TYPE_SEND){
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            if(msg.isImg()){
                holder.rightImg.setImageBitmap(msg.getBitmap());
            }{
                holder.rightMsg.setText(msg.getContent());
            }
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;

        TextView leftMsg;
        TextView rightMsg;

        ImageView leftImg;
        ImageView rightImg;

        public ViewHolder(View itemView) {
            super(itemView);
            leftLayout = (LinearLayout) itemView.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) itemView.findViewById(R.id.right_layout);
            leftMsg = (TextView) itemView.findViewById(R.id.left_msg);
            rightMsg = (TextView) itemView.findViewById(R.id.right_msg);
            leftImg = (ImageView) itemView.findViewById(R.id.left_img);
            rightImg = (ImageView) itemView.findViewById(R.id.right_img);
        }
    }
    public MsgAdapter(ArrayList<Msg> msgList){
        this.msgList = msgList;
    }
}
