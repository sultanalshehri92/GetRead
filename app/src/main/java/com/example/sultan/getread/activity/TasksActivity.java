package com.example.sultan.getread.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sultan.getread.R;
import com.example.sultan.getread.adapter.TaskViewAdapter;
import com.example.sultan.getread.model.Task;
import com.example.sultan.getread.network.ApiClient;
import com.example.sultan.getread.service.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog pDialog;
    private TextView text;
    private RecyclerView recyclerView;
    private List<Task> taskList;

    private View user_tab, po_tab, photo_tab, task_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setTitle("Tasks");
        setSupportActionBar(toolbar);

        getTabs();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.activity_task);
        swipeContainer.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getData();
                    }
                }
        );

        pDialog = new ProgressDialog(this);
        showpDialog();

        text = (TextView) findViewById(R.id.task_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_bar:
                Log.i("ActionBar", "Nuevo!");
                return true;
            case R.id.action_settings:
                Log.i("ActionBar", "Settings!");
                ;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void getData() {
        APIService service = ApiClient.getRetrofit().create(APIService.class);
        Call<List<Task>> call = service.getTaskDetails();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                taskList = response.body();
                recyclerView.setAdapter(new TaskViewAdapter
                        (taskList, R.layout.task_view_row, getApplicationContext(),
                                new TaskViewAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Task item) {

                                    }
                                }
                        )
                );

                hidepDialog();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Toast.makeText(TasksActivity.this, "Faild", Toast.LENGTH_LONG).show();
                text.setText(t.toString());
            }

        });
    }

    private void getTabs() {
        user_tab = (View) findViewById(R.id.user_tab);
        user_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TasksActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        po_tab = (View) findViewById(R.id.po_tab);
        po_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TasksActivity.this, PostsActivity.class);
                startActivity(intent);
            }
        });

        photo_tab = (View) findViewById(R.id.photo_tab);
        photo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(TasksActivity.this, PhotosActivity.class);
                startActivity(intent);
            }
        });

        task_tab = (View) findViewById(R.id.task_tab);
        task_tab.setSelected(true);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
        pDialog.setMessage("Loading...");
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

