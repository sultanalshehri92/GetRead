package com.example.sultan.getread.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sultan.getread.R;
import com.example.sultan.getread.adapter.PhotoViewAdapter;
import com.example.sultan.getread.model.Photo;
import com.example.sultan.getread.network.ApiClient;
import com.example.sultan.getread.service.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog pDialog;
    private TextView text;
    private RecyclerView recyclerView;
    private PhotoViewAdapter recyclerViewAdapter;
    private List<Photo> userList;
    private String searchQuery = "";
    SearchView searchView;
    View user_tab, po_tab, photo_tab ,task_tab;

    APIService service = ApiClient.getRetrofit().create(APIService.class);
    Call<List<Photo>> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.activity_photo);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setTitle("Photos");
        setSupportActionBar(toolbar);

        getTabs();

        pDialog = new ProgressDialog(this);
        showpDialog();

        text = (TextView) findViewById(R.id.text_photo);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_photo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search_bar);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                getData();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerViewAdapter.filterList(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_bar:
                return true;
            case R.id.action_settings:
                return true;
            case R.id.share:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getData() {
        if (searchQuery.isEmpty())
            call = service.getPhotoDetails();
        else {
            call = service.searchPhoto(searchQuery);
            searchQuery = "";
        }

        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                userList = response.body();
                recyclerViewAdapter = new PhotoViewAdapter
                    (userList, R.layout.photo_view_row, PhotosActivity.this,
                        new PhotoViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Photo item, int position) {
                                Intent intent = new Intent();
                                Bundle b = new Bundle();
                                b.putParcelable("p", item);
                                b.putInt("index", position);
                                intent.putExtras(b);
                                intent.setClass(PhotosActivity.this, PhotoDetailedActivity.class);
                                startActivity(intent);

                            }
                        }
                    );
                recyclerView.setAdapter(recyclerViewAdapter);
                hidepDialog();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Toast.makeText(PhotosActivity.this, "Faild", Toast.LENGTH_LONG).show();
                text.setText(t.toString());
            }
        });
    }

    private void getTabs(){
        user_tab = findViewById(R.id.user_tab);
        user_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PhotosActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        po_tab = findViewById(R.id.po_tab);
        po_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PhotosActivity.this, PostsActivity.class);
                startActivity(intent);
            }
        });

        photo_tab = findViewById(R.id.photo_tab);
        photo_tab.setSelected(true);

        task_tab = findViewById(R.id.task_tab);
        task_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PhotosActivity.this, TasksActivity.class);
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
