package com.example.sultan.getread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sultan.getread.model.Photo;

import java.util.List;

/**
 * Created by Sultan on 3/8/2017.
 */

public class PhotoViewAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private OnItemClickListener listener;

    private List<Photo> item;
    private int row;
    private Context context;

    public PhotoViewAdapter(List<Photo> item, int row, Context context, OnItemClickListener listener) {
        this.item = item;
        this.row = row;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(row, parent, false);

        return new PhotoViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.bind(item.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Photo item, int position);
    }
}

