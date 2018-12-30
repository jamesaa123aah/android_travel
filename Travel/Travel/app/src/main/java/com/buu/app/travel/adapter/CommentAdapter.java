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
import com.buu.app.travel.role.Comment_Item;

import java.util.List;

/**
 * Created by bei on 2017/10/30.
 */

public class CommentAdapter extends ArrayAdapter<Comment_Item> {
    private int myResource;
    public CommentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Comment_Item> objects) {
        super(context, resource, objects);
        myResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Comment_Item comment_item = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(myResource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView)view.findViewById(R.id.comment_img);
            viewHolder.date = (TextView)view.findViewById(R.id.comment_date);
            viewHolder.username = (TextView)view.findViewById(R.id.comment_username);
            viewHolder.comment = (TextView)view.findViewById(R.id.comment_comment);

            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.img.setImageResource(comment_item.getImg());
        viewHolder.username.setText(comment_item.getUserName());
        viewHolder.date.setText(comment_item.getDate());
        viewHolder.comment.setText(comment_item.getComment());
        return view;
    }

    class ViewHolder{
        ImageView img;
        TextView username;
        TextView date;
        TextView comment;
    }
}
