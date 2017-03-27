package com.example.sultan.getread.activity;

import android.content.Intent;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.Post;
import com.example.sultan.getread.model.User;
import com.example.sultan.getread.network.ApiClient;
import com.example.sultan.getread.service.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sultan.getread.adapter.RecyclerViewAdapter.getUserList;
import static java.lang.String.valueOf;

public class PostDetailedActivity extends PostsActivity{

    private SwipeRefreshLayout swipeContainer;

    Post post;
    int index;
    private User user;
    View user_tab, po_tab, photo_tab ,task_tab;
    TextView post_uId, post_id, title, body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detailed);

        post_uId = (TextView)findViewById(R.id.post_uId);
        post_id = (TextView)findViewById(R.id.post_id);
        title = (TextView)findViewById(R.id.title);
        body = (TextView)findViewById(R.id.body);

        getDetails();


        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setTitle("Post: " + valueOf(post.getId()));
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        getTabs();

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.activity_post_detailed);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APIService service = ApiClient.getRetrofit().create(APIService.class);
                Call<Post> call = service.getPost(index);
                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        getDetails();
                        Toast.makeText(PostDetailedActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        Toast.makeText(PostDetailedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void getDetails(){
        if (getIntent().getExtras() != null) {
            post = getIntent().getExtras().getParcelable("Post");
            index = getIntent().getExtras().getInt("Id");
            user = getUserList().get(index);
            post_uId.setText(user.getName());
            post_uId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Bundle b = new Bundle();
                    b.putParcelable("User", user);
                    b.putInt("Id", user.getId());
                    intent.putExtras(b);
                    intent.setClass(PostDetailedActivity.this, UserActivity.class);
                    startActivity(intent);
                }
            });
            post_id.setText("#");
            post_id.append(valueOf(post.getId()));
            title.setText(post.getTitle());
            body.setText(post.getBody());
        }
    }

    private void getTabs(){
        user_tab = (View) findViewById(R.id.user_tab);
        user_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PostDetailedActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        po_tab = (View) findViewById(R.id.po_tab);
        po_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PostDetailedActivity.this, PostsActivity.class);
                startActivity(intent);
            }
        });

        photo_tab = (View) findViewById(R.id.photo_tab);
        photo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PostDetailedActivity.this, PhotosActivity.class);
                startActivity(intent);
            }
        });

        task_tab = (View) findViewById(R.id.task_tab);
        task_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PostDetailedActivity.this, TasksActivity.class);
                startActivity(intent);
            }
        });
    }

}
