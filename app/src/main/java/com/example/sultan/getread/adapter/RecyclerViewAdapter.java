package com.example.sultan.getread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.User;

import java.util.List;

/**
 * Created by Sultan on 2/28/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private OnItemClickListener listener;

    private List<User> item;
    private int row;
    private Context context;

    public RecyclerViewAdapter(List<User> item, int row, Context context, OnItemClickListener listener) {
        this.item = item;
        this.row = row;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(row, parent, false);

        return new RecyclerViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(item.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public interface OnItemClickListener {
        void onItemClick(User item);
    }

}