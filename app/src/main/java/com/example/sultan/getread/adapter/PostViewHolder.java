package com.example.sultan.getread.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.Post;


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

    public void bind(final Post item, final PostViewAdapter.OnItemClickListener listener) {

        post_uId.setText(item.getUserId());
        post_id.setText(item.getId());
        title.setText(item.getTitle());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item, getAdapterPosition());
            }
        });
    }
}