package com.example.sultan.getread.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.Post;
import com.example.sultan.getread.model.Task;


/**
 * Created by Sultan on 3/5/2017.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder{

    LinearLayout dataLayout;
    TextView task_uId, task_id, task_title, task_completed;

    public TaskViewHolder(View itemView) {
        super(itemView);

        dataLayout = (LinearLayout) itemView.findViewById(R.id.task_view_row);
        task_uId = (TextView) itemView.findViewById(R.id.task_uId);
        task_id = (TextView) itemView.findViewById(R.id.task_id);
        task_title = (TextView) itemView.findViewById(R.id.task_title);
        task_completed = (TextView) itemView.findViewById(R.id.task_completed);
    }

    public void bind(final Task item, final TaskViewAdapter.OnItemClickListener listener) {

        task_uId.setText(item.getUserId());
        task_id.setText(item.getId());
        task_title.setText(item.getTitle());
        task_completed.setText(item.getCompleted());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }
}