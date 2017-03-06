package com.example.sultan.getread.service;


import com.example.sultan.getread.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Sultan on 2/24/2017.
 */

public interface APIService {
    @GET("users")
    Call<List<User>> getUserDetails();

}
