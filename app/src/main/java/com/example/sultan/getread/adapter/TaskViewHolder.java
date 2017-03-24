package com.example.sultan.getread.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.Task;

import static java.lang.String.valueOf;


/**
 * Created by Sultan on 3/5/2017.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder{

    LinearLayout dataLayout;
    private TextView task_uId, task_id, task_title;
    private CheckBox task_completed;

    public TaskViewHolder(View itemView) {
        super(itemView);

        dataLayout = (LinearLayout) itemView.findViewById(R.id.task_view_row);
        task_uId = (TextView) itemView.findViewById(R.id.task_uId);
        task_id = (TextView) itemView.findViewById(R.id.task_id);
        task_title = (TextView) itemView.findViewById(R.id.task_title);
        task_completed = (CheckBox) itemView.findViewById(R.id.task_completed);
    }

    public void bind(final Task item, final TaskViewAdapter.OnItemClickListener listener, String u) {

        task_id.setText("#");
        task_id.append(valueOf(item.getId()));

        task_uId.setText(u);
        task_title.setText(item.getTitle());
        task_completed.setText(item.getCompletedStatus());
        task_completed.setChecked(item.getCompleted());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }
}