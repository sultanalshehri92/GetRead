package com.example.sultan.getread.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sultan.getread.R;
import com.example.sultan.getread.activity.MainActivity;
import com.example.sultan.getread.activity.UserActivity;
import com.example.sultan.getread.model.Photo;
import com.example.sultan.getread.model.User;
import com.squareup.picasso.Picasso;

import static java.lang.String.valueOf;

/**
 * Created by Sultan on 3/8/2017.
 */

class PhotoViewHolder extends RecyclerView.ViewHolder{

    private TextView album_id, photo_id, photo_title;
    private ImageView thumbnail;

    PhotoViewHolder(View itemView) {
        super(itemView);

        album_id = (TextView) itemView.findViewById(R.id.albumId);
        photo_id = (TextView) itemView.findViewById(R.id.photo_id);
        photo_title = (TextView) itemView.findViewById(R.id.photo_title);
        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnailUrl);
    }

    void bind(final Photo item, final PhotoViewAdapter.OnItemClickListener listener, final Context context,final User user) {

        photo_id.setText("#");
        photo_id.append(valueOf(item.getId()));
        album_id.setText(valueOf(user.getName()));
        album_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putParcelable("User", user);
                b.putInt("Id", item.getAlbumId());
                intent.putExtras(b);
                intent.setClass(context, UserActivity.class);
                context.startActivity(intent);
            }
        });

        StringBuilder title = new StringBuilder();
        title.append("Title:\n");
        title.append(item.getTitle());
        photo_title.setText(title);

        String thumbnailUrl = item.getThumbnailUrl();
        if (!thumbnailUrl.startsWith("https://")){

            if (thumbnailUrl.startsWith("http://"))
                thumbnailUrl = new StringBuilder(thumbnailUrl).insert(4, "s").toString();
            else
                thumbnailUrl = new StringBuilder(thumbnailUrl).insert(0, "https://").toString();
        }

        Picasso.with(context)
            .load(thumbnailUrl)
            .placeholder(thumbnail.getDrawable())
            .error(R.mipmap.profile)
            .resize(200, 200)
            .into(thumbnail);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item, getAdapterPosition());
            }
        });
    }
}