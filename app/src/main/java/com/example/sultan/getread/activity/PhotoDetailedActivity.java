package com.example.sultan.getread.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.Photo;
import com.example.sultan.getread.network.ApiClient;
import com.example.sultan.getread.service.APIService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetailedActivity extends PhotosActivity {

    private SwipeRefreshLayout swipeContainer;
    Photo photo;
    int index, idNu;

    private TextView albumId, photo_id, photo_title;
    private ImageView urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detailed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        albumId = (TextView)findViewById(R.id.album_Id);
        photo_id = (TextView)findViewById(R.id.photoId);
        photo_title = (TextView)findViewById(R.id.photoTitle);
        urlImage = (ImageView)findViewById(R.id.url);

        getDetails();

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.activity_photo_detailed);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APIService service = ApiClient.getRetrofit().create(APIService.class);
                Call<Photo> call = service.getPhoto(index);
                call.enqueue(new Callback<Photo>() {
                    @Override
                    public void onResponse(Call<Photo> call, Response<Photo> response) {
                        getDetails();
                        Toast.makeText(PhotoDetailedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Photo> call, Throwable t) {
                        Toast.makeText(PhotoDetailedActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                });
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void getDetails(){
        if (getIntent().getExtras() != null) {
            photo = getIntent().getExtras().getParcelable("p");
            index = getIntent().getExtras().getInt("index");
            idNu= photo.getId();

            albumId.setText(photo.getAlbumId());
            photo_id.setText(photo.getId());
            photo_title.setText(photo.getTitle());


            String Url = photo.getUrl();
            Picasso.with(PhotoDetailedActivity.this).
                load(Url).
                placeholder(urlImage.getDrawable()).
                resize(300,300).
                error(R.mipmap.ic_launcher).
                into(urlImage);
        }
    }
}
