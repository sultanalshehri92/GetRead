package com.example.sultan.getread.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.Photo;
import com.example.sultan.getread.model.User;
import com.example.sultan.getread.network.ApiClient;
import com.example.sultan.getread.service.APIService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sultan.getread.adapter.RecyclerViewAdapter.getUserList;
import static java.lang.String.valueOf;

public class PhotoDetailedActivity extends PhotosActivity {

    private SwipeRefreshLayout swipeContainer;
    private List<User> u = getUserList();
    Photo photo;
    int index;

    private TextView albumId, photo_id, photo_title;
    private ImageView urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detailed);

        albumId = (TextView)findViewById(R.id.album_Id);
        photo_id = (TextView)findViewById(R.id.photoId);
        photo_title = (TextView)findViewById(R.id.photoTitle);
        urlImage = (ImageView)findViewById(R.id.url);

        getDetails();

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setTitle("Photo: " + valueOf(photo.getId()));
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.activity_photo_detailed);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPhoto();
                swipeContainer.setRefreshing(false);
                Toast.makeText(PhotoDetailedActivity.this, "Updated", Toast.LENGTH_SHORT).show();            }
        });
    }

    private void getDetails(){
        index = getIntent().getExtras().getInt("Id");
        if (getIntent().hasExtra("Photo"))
            photo = getIntent().getExtras().getParcelable("Photo");

        if (photo != null) {
            albumId.setText(getUserList().get(index).getName());
            albumId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     Intent intent = new Intent();
                     Bundle b = new Bundle();
                     b.putParcelable("User", getUserList().get(index));
                     b.putInt("Id", getUserList().get(index).getId());
                     intent.putExtras(b);
                     intent.setClass(PhotoDetailedActivity.this, UserActivity.class);
                     startActivity(intent);
                }
            });
            //photo_id.setText(valueOf(photo.getId()));
            photo_title.setText(photo.getTitle());

            String thumbnailUrl = photo.getUrl();
            if (!thumbnailUrl.startsWith("https://")){

                if (thumbnailUrl.startsWith("http://"))
                    thumbnailUrl = new StringBuilder(thumbnailUrl).insert(4, "s").toString();
                else
                    thumbnailUrl = new StringBuilder(thumbnailUrl).insert(0, "https://").toString();
            }

            Picasso.with(PhotoDetailedActivity.this).
                    load(thumbnailUrl).
                    placeholder(urlImage.getDrawable()).
                    fit().
                    error(R.mipmap.ic_launcher).
                    into(urlImage);
        } else
            getPhoto();
    }

    private void getPhoto(){
        APIService service = ApiClient.getRetrofit().create(APIService.class);
        Call<Photo> call = service.getPhoto(-1);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                getDetails();
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                Toast.makeText(PhotoDetailedActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}
