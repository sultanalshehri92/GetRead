package com.example.sultan.getread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.Photo;
import com.squareup.picasso.Picasso;

import static java.lang.String.valueOf;

/**
 * Created by Sultan on 3/8/2017.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder{

    LinearLayout dataLayout;
    TextView album_id, photo_id, photo_title;
    ImageView thumbnail;

    public PhotoViewHolder(View itemView) {
        super(itemView);

        dataLayout = (LinearLayout) itemView.findViewById(R.id.photo_view_row);
        album_id = (TextView) itemView.findViewById(R.id.albumId);
        photo_id = (TextView) itemView.findViewById(R.id.photo_id);
        photo_title = (TextView) itemView.findViewById(R.id.photo_title);
        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnailUrl);
    }

    public void bind(final Photo item, final PhotoViewAdapter.OnItemClickListener listener, Context context) {

        album_id.setText(valueOf(item.getAlbumId()));
        photo_id.setText(valueOf(item.getId()));
        photo_title.setText(item.getTitle());

        String thumbnailUrl = item.getThumbnailUrl();
        Picasso.with(context).
            load(thumbnailUrl).
            placeholder(thumbnail.getDrawable()).
            error(R.mipmap.ic_launcher).
            resize(50,50).
            into(thumbnail);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item, getAdapterPosition());
            }
        });
    }
}