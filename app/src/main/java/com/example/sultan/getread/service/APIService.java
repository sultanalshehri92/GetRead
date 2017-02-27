package com.example.sultan.getread.service;

import com.example.sultan.getread.model.Data;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Sultan on 2/26/2017.
 */

public interface APIService {

    @GET("users")
    Call<List<Data>> getData();
}
