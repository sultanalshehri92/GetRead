package com.example.sultan.getread.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sultan.getread.model.Post;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * Created by Sultan on 2/28/2017.
 */

public class PostViewAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private OnItemClickListener listener;

    private List<Post> postList, filteredList;
    private int row;
    private Context context;

    public PostViewAdapter(List<Post> item, int row, Context context, OnItemClickListener listener) {
        this.postList = item;
        this.row = row;
        this.context = context;
        this.listener = listener;

        this.filteredList = new ArrayList<>();
        this.filteredList.addAll(this.postList);
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(row, parent, false);

        return new PostViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.bind(filteredList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Post item, int position);
    }

    public void filterList(final String text){
        new Thread(new Runnable() {
            @Override
            public void run() {
                filteredList.clear();
                if (TextUtils.isEmpty(text)) {
                    filteredList.addAll(postList);
                } else {
                    for (Post item : postList) {
                        if (item.getTitle().toLowerCase().contains(text.toLowerCase()) ||
                            item.getBody().toLowerCase().contains(text.toLowerCase()) ||
                            valueOf(item.getUserId()).toLowerCase().contains(text.toLowerCase()) ||
                            valueOf(item.getId()).toLowerCase().contains(text.toLowerCase()))

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