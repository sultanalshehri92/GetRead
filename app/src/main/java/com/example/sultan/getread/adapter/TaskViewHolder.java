package com.example.sultan.getread.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sultan.getread.R;
import com.example.sultan.getread.activity.UserActivity;
import com.example.sultan.getread.model.Task;
import com.example.sultan.getread.model.User;

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

    public void bind(final Task item, final TaskViewAdapter.OnItemClickListener listener, final Context context, final User user) {

        task_id.setText("#");
        task_id.append(valueOf(item.getId()));
        task_uId.setText(user.getName());
        task_uId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putParcelable("User", user);
                b.putInt("Id", item.getUserId());
                intent.putExtras(b);
                intent.setClass(context, UserActivity.class);
                context.startActivity(intent);
            }
        });
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