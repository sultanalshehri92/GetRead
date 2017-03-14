package com.example.sultan.getread.activity;

import com.example.sultan.getread.R;
import com.example.sultan.getread.adapter.PostViewAdapter;
import com.example.sultan.getread.model.Post;
import com.example.sultan.getread.network.ApiClient;
import com.example.sultan.getread.service.APIService;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostsActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog pDialog;
    private TextView text;
    private RecyclerView recyclerView;
    private List<Post> postList;
    private View user_tab, po_tab, photo_tab ,task_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setTitle("Posts");
        setSupportActionBar(toolbar);

        getTabs();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.activity_post);
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

        text = (TextView) findViewById(R.id.ptext);
        recyclerView = (RecyclerView)findViewById(R.id.precycler);
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
                Log.i("ActionBar", "Settings!");;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getData() {
        APIService service = ApiClient.getRetrofit().create(APIService.class);
        Call<List<Post>> call = service.getPostDetails();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                postList = response.body();
                recyclerView.setAdapter(new PostViewAdapter
                    (postList, R.layout.post_view_row, getApplicationContext(),
                        new PostViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Post item, int position) {
                                Intent intent = new Intent();
                                Bundle b = new Bundle();
                                b.putParcelable("p", item);
                                b.putInt("index", position);
                                intent.putExtras(b);
                                intent.setClass(PostsActivity.this, PostDetailedActivity.class);
                                startActivity(intent);
                            }
                        }
                    )
                );
                hidepDialog();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(PostsActivity.this, "Faild", Toast.LENGTH_LONG).show();
                text.setText(t.toString());
            }

        });
    }

    private void getTabs(){
        user_tab = (View) findViewById(R.id.user_tab);
        user_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PostsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        po_tab = (View) findViewById(R.id.po_tab);
        po_tab.setSelected(true);

        photo_tab = (View) findViewById(R.id.photo_tab);
        photo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PostsActivity.this, PhotosActivity.class);
                startActivity(intent);
            }
        });

        task_tab = (View) findViewById(R.id.task_tab);
        task_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PostsActivity.this, TasksActivity.class);
                startActivity(intent);
            }
        });
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