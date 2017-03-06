package com.example.sultan.getread.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sultan.getread.R;
import com.example.sultan.getread.adapter.RecyclerViewAdapter;
import com.example.sultan.getread.model.User;
import com.example.sultan.getread.network.ApiClient;
import com.example.sultan.getread.service.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog pDialog;
    private TextView text;
    private RecyclerView recyclerView;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.activity_main);
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

        text = (TextView) findViewById(R.id.text);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();

    }

    protected void getData() {
        APIService service = ApiClient.getRetrofit().create(APIService.class);
        Call<List<User>> call = service.getUserDetails();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userList = response.body();
                recyclerView.setAdapter(new RecyclerViewAdapter
                    (userList, R.layout.recycler_view_row, getApplicationContext(),
                        new RecyclerViewAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(User item) {
                                Intent intent = new Intent();
                                Bundle b = new Bundle();
                                b.putParcelable("u", item);
                                intent.putExtras(b);
                                intent.setClass(MainActivity.this, UserActivity.class);
                                startActivity(intent);
                            }
                        }
                    )
                );
                hidepDialog();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Faild", Toast.LENGTH_LONG).show();
                text.setText(t.toString());
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
