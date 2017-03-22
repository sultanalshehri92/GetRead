package com.example.sultan.getread.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sultan.getread.model.User;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * Created by Sultan on 2/28/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private OnItemClickListener listener;

    private List<User> userList, filteredList;
    private int row;
    private Context context;

    public RecyclerViewAdapter(List<User> userList, int row, Context context, OnItemClickListener listener) {
        this.userList = userList;
        this.row = row;
        this.context = context;
        this.listener = listener;

        this.filteredList = new ArrayList<>();
        this.filteredList.addAll(this.userList);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(row, parent, false);

        return new RecyclerViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.bind(filteredList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(User item, int position);
    }

    public void filterList(final String text){
        new Thread(new Runnable() {
            @Override
            public void run() {
                filteredList.clear();
                if (TextUtils.isEmpty(text)) {
                    filteredList.addAll(userList);
                } else {
                    for (User item : userList) {
                        if (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                            item.getUsername().toLowerCase().contains(text.toLowerCase()) ||
                            item.getEmail().toLowerCase().contains(text.toLowerCase()) ||
                            item.getPhone().toLowerCase().contains(text.toLowerCase()) ||
                            item.getWebsite().toLowerCase().contains(text.toLowerCase()) ||
                            item.getCompany().getCompany().toLowerCase().contains(text.toLowerCase()) ||
                            item.getAddress().getAddress().toLowerCase().contains(text.toLowerCase()) ||
                            valueOf(item.getId()).toLowerCase().contains(text.toLowerCase())) {

                                    filteredList.add(item);
                        }
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