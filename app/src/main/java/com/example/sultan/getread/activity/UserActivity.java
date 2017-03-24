package com.example.sultan.getread.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.Address;
import com.example.sultan.getread.model.Company;
import com.example.sultan.getread.model.Geo;
import com.example.sultan.getread.model.User;
import com.example.sultan.getread.network.ApiClient;
import com.example.sultan.getread.service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.valueOf;

public class UserActivity extends MainActivity{

    private SwipeRefreshLayout swipeContainer;
    User user;
    Address userAddress;
    Company userCompany;
    Geo userGeo;
    int index;
    View user_tab, po_tab, photo_tab ,task_tab;
    private TextView name, username, email, address, phone, website, company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        name = (TextView)findViewById(R.id.name);
        phone = (TextView)findViewById(R.id.phone);
        username = (TextView)findViewById(R.id.username);
        email = (TextView)findViewById(R.id.email);
        website = (TextView)findViewById(R.id.website);
        company = (TextView)findViewById(R.id.company);
        address = (TextView)findViewById(R.id.address);

        getDetails();

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(user.getWebsite());
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setTitle("User: " + (index));
        setSupportActionBar(toolbar);

        getTabs();

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.activity_user);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUser();
                swipeContainer.setRefreshing(false);
                Toast.makeText(UserActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetails(){
        index = getIntent().getExtras().getInt("Id");
        if (getIntent().hasExtra("User"))
            user = getIntent().getExtras().getParcelable("User");

            if (user != null){
                userCompany = user.getCompany();
                userAddress = user.getAddress();
                userGeo = userAddress.getGeo();

                name.setText(user.getName());
                phone.setText(user.getPhone());
                username.setText(user.getUsername());
                email.setText(user.getEmail());
                website.setText(user.getWebsite());
                company.setText(userCompany.getCompany());
                address.setText(userAddress.getAddress());
                address.append(userGeo.getGeo());
            } else
                getUser();


    }

    private void getUser(){
        APIService service = ApiClient.getRetrofit().create(APIService.class);
        Call<User> call = service.getUser(index);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response){
                getDetails();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserActivity.this, "Failed", Toast.LENGTH_LONG).show();
                username.setText(t.toString());
            }
        });
    }

    private void getTabs(){
        user_tab = findViewById(R.id.user_tab);
        user_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(UserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        po_tab = findViewById(R.id.po_tab);
        po_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(UserActivity.this, PostsActivity.class);
                startActivity(intent);
            }
        });

        photo_tab = findViewById(R.id.photo_tab);
        photo_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(UserActivity.this, PhotosActivity.class);
                startActivity(intent);
            }
        });

        task_tab = findViewById(R.id.task_tab);
        task_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(UserActivity.this, TasksActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openWebPage(String url) {
        String webpage = url;
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            webpage = "http://" + url;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webpage));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
