package com.example.sultan.getread.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.User;

import static java.lang.String.valueOf;

/**
 * Created by Sultan on 3/5/2017.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder{

    LinearLayout dataLayout;
    TextView name, idNu, phone;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        dataLayout = (LinearLayout) itemView.findViewById(R.id.recycler_view_row);
        idNu = (TextView) itemView.findViewById(R.id.idNu);
        name = (TextView) itemView.findViewById(R.id.name);
        phone = (TextView) itemView.findViewById(R.id.phone);
    }

    public void bind(final User item, final RecyclerViewAdapter.OnItemClickListener listener) {

        idNu.setText(valueOf(item.getId()));
        name.setText(item.getName());
        phone.setText(item.getPhone());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(item, getAdapterPosition());
            }
        });
    }
}