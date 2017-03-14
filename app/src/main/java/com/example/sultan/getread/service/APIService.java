package com.example.sultan.getread.service;


import com.example.sultan.getread.model.Photo;
import com.example.sultan.getread.model.Post;
import com.example.sultan.getread.model.Task;
import com.example.sultan.getread.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Sultan on 2/24/2017.
 */

public interface APIService {
    @GET("users")
    Call<List<User>> getUserDetails();
    @GET("users/{id}")
    Call<User> getUser(@Path("id") int id);

    @GET("posts")
    Call<List<Post>> getPostDetails();
    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") int id);

    @GET("photos")
    Call<List<Photo>> getPhotoDetails();
    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") int id);

    @GET("todos")
    Call<List<Task>> getTaskDetails();
    @GET("todos/{id}")
    Call<Task> getTask(@Path("id") int id);


}
