package com.example.sultan.getread.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sultan.getread.model.Task;
import com.example.sultan.getread.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.example.sultan.getread.adapter.RecyclerViewAdapter.getUserList;
import static java.lang.String.valueOf;

/**
 * Created by Sultan on 3/8/2017.
 */

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private OnItemClickListener listener;

    private List<Task> taskList, filteredList;
    private List<User> u = getUserList();
    private int row;
    private Context context;

    public TaskViewAdapter(List<Task> item, int row, Context context, OnItemClickListener listener) {
        this.taskList = item;
        this.row = row;
        this.context = context;
        this.listener = listener;

        this.filteredList = new ArrayList<>();
        this.filteredList.addAll(this.taskList);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(row, parent, false);

        return new TaskViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.bind(filteredList.get(position),
                listener,
                context,
                u.get(filteredList.get(position).getUserId() - 1));
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Task item);
    }

    public void filterList(final String text){
        new Thread(new Runnable() {
            @Override
            public void run() {
                filteredList.clear();
                if (TextUtils.isEmpty(text)) {
                    filteredList.addAll(taskList);
                } else {
                    for (Task item : taskList) {
                        if (item.getTitle().toLowerCase().contains(text.toLowerCase()) ||
                            item.getCompletedStatus().toLowerCase().contains(text.toLowerCase())||
                            u.get(item.getUserId()-1).getName().toLowerCase().contains(text.toLowerCase()) ||
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