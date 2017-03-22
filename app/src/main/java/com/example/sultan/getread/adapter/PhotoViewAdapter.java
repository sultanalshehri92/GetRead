package com.example.sultan.getread.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sultan.getread.model.Photo;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * Created by Sultan on 3/8/2017.
 */

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private OnItemClickListener listener;

    private List<Photo> photoList, filteredList;
    private int row;
    Context context;

    public PhotoViewAdapter(List<Photo> item, int row, Context context, OnItemClickListener listener) {
        this.photoList = item;
        this.row = row;
        this.context = context;
        this.listener = listener;

        this.filteredList = new ArrayList<>();
        this.filteredList.addAll(this.photoList);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(row, parent, false);

        return new PhotoViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.bind(filteredList.get(position), listener, context);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Photo item, int position);
    }

    public void filterList(final String text){
        new Thread(new Runnable() {
            @Override
            public void run() {
                filteredList.clear();
                if (TextUtils.isEmpty(text)) {
                    filteredList.addAll(photoList);
                } else {
                    for (Photo item : photoList) {
                        if (valueOf(item.getAlbumId()).toLowerCase().contains(text.toLowerCase()) ||
                            valueOf(item.getId()).toLowerCase().contains(text.toLowerCase()) ||
                            item.getTitle().toLowerCase().contains(text.toLowerCase()))
                                filteredList.add(item);
                    }
                }
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}

