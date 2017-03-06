package com.example.sultan.getread.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.Address;
import com.example.sultan.getread.model.Company;
import com.example.sultan.getread.model.Geo;
import com.example.sultan.getread.model.User;

public class UserActivity extends MainActivity{

    private SwipeRefreshLayout swipeContainer;
    User user;
    Address userAddress;
    Company userCompany;
    Geo userGeo;
    private TextView name, username, email, idNu, address, phone, website, company;

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

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.activity_user);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                getDetails();
                swipeContainer.setRefreshing(false);

            }
        });
    }

    private void getDetails(){
        if (getIntent().getExtras() != null) {
            user = getIntent().getExtras().getParcelable("u");
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
        }
    }

}
