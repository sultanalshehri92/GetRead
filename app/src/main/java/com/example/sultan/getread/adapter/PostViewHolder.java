package com.example.sultan.getread.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sultan.getread.R;
import com.example.sultan.getread.activity.PostsActivity;
import com.example.sultan.getread.activity.UserActivity;
import com.example.sultan.getread.model.Post;
import com.example.sultan.getread.model.User;

import static java.lang.String.valueOf;


/**
 * Created by Sultan on 3/5/2017.
 */

public class PostViewHolder extends RecyclerView.ViewHolder{

    LinearLayout dataLayout;
    TextView post_uId, post_id, title;

    public PostViewHolder(View itemView) {
        super(itemView);

        dataLayout = (LinearLayout) itemView.findViewById(R.id.post_view_row);
        post_uId = (TextView) itemView.findViewById(R.id.post_uId);
        post_id = (TextView) itemView.findViewById(R.id.post_id);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    public void bind(final Post item, final PostViewAdapter.OnItemClickListener listener,final Context context, final User user) {

        post_uId.setText(user.getName());
        post_uId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putParcelable("User", user);
                b.putInt("Id", item.getUserId());
                intent.putExtras(b);
                intent.setClass(context, UserActivity.class);
                context.startActivity(intent);
            }
        });
        post_id.setText(valueOf(item.getId()));
        title.setText(item.getTitle());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item, getAdapterPosition());
            }
        });
    }
}