package com.buu.app.travel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buu.app.travel.R;
import com.buu.app.travel.role.Msg1;

import java.util.List;

/**
 * Created by bei on 2017/11/22.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<Msg1> msgList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout_left;
        LinearLayout linearLayout_right;
        TextView text_left;
        TextView text_right;
        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout_left = (LinearLayout)itemView.findViewById(R.id.recycler_item_left);
            linearLayout_right = (LinearLayout)itemView.findViewById(R.id.recycler_item_right);
            text_left = (TextView)itemView.findViewById(R.id.recycler_item_left_text);
            text_right = (TextView)itemView.findViewById(R.id.recycler_item_right_text);
        }
    }

    public ChatAdapter(List<Msg1> msg){
        this.msgList = msg;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg1 msg = msgList.get(position);
        if(msg.getType() == Msg1.TYPE_LEFT){
            holder.linearLayout_left.setVisibility(View.VISIBLE);
            holder.linearLayout_right.setVisibility(View.GONE);
            holder.text_left.setText(msg.getMsg());
        }

        if(msg.getType() == Msg1.TYPE_RIGHT){
            holder.linearLayout_left.setVisibility(View.GONE);
            holder.linearLayout_right.setVisibility(View.VISIBLE);
            holder.text_right.setText(msg.getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
