package com.example.sultan.getread.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.Photo;

/**
 * Created by Sultan on 3/8/2017.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder{

    LinearLayout dataLayout;
    TextView album_id, photo_id, photo_title;
    ImageView thumbnailUrl;

    public PhotoViewHolder(View itemView) {
        super(itemView);

        dataLayout = (LinearLayout) itemView.findViewById(R.id.photo_view_row);
        album_id = (TextView) itemView.findViewById(R.id.albumId);
        photo_id = (TextView) itemView.findViewById(R.id.photo_id);
        photo_title = (TextView) itemView.findViewById(R.id.photo_title);
        thumbnailUrl = (ImageView) itemView.findViewById(R.id.thumbnailUrl);
    }

    public void bind(final Photo item, final PhotoViewAdapter.OnItemClickListener listener) {

        album_id.setText(item.getAlbumId());
        photo_id.setText(item.getId());
        photo_title.setText(item.getTitle());
        thumbnailUrl.setImageURI(Uri.parse(item.getThumbnailUrl()));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item, getAdapterPosition());
            }
        });
    }
}