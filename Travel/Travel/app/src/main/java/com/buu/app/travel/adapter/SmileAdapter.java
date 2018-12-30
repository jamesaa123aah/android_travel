package com.buu.app.travel.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buu.app.travel.ChatActivity;
import com.buu.app.travel.R;
import com.buu.app.travel.role.Msg;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by king- on 2017/5/30.
 */

public class SmileAdapter extends RecyclerView.Adapter<SmileAdapter.ViewHolder> {
    private ArrayList<Integer> mSmiles;
    private Context mContext;
    private ChatActivity chatActivity;
    public SmileAdapter(ArrayList<Integer> Smiles,ChatActivity chatActivity){
        mSmiles = Smiles;
        this.chatActivity = chatActivity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.smile_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int x = mSmiles.get(position);
        InputStream in = null;
        try {
            in = mContext.getResources().getAssets().open("emoji/ ("+x+").png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.imageView.setImageBitmap(BitmapFactory.decodeStream(in));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatActivity.AddSmileMsg(position+1, Msg.TYPE_SEND);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSmiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.smile_img);
        }
    }
}
