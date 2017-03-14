package com.example.sultan.getread.activity;

import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sultan.getread.R;
import com.example.sultan.getread.model.Photo;
import com.example.sultan.getread.model.Post;
import com.example.sultan.getread.network.ApiClient;
import com.example.sultan.getread.service.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDetailedActivity extends PhotosActivity {

    private SwipeRefreshLayout swipeContainer;
    Photo photo;
    int index;

    private TextView albumId, photo_id, photo_title;
    private ImageView thumbnailUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detailed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        albumId = (TextView)findViewById(R.id.albumId);
        photo_id = (TextView)findViewById(R.id.photo_id);
        photo_title = (TextView)findViewById(R.id.photo_title);
        thumbnailUrl = (ImageView)findViewById(R.id.thumbnailUrl);

        getDetails();

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.activity_post_detailed);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APIService service = ApiClient.getRetrofit().create(APIService.class);
                Call<Photo> call = service.getPhoto(index);
                call.enqueue(new Callback<Photo>() {
                    @Override
                    public void onResponse(Call<Photo> call, Response<Photo> response) {
                        getDetails();
                        Toast.makeText(PhotoDetailedActivity.this, "Updated", Toast.LENGTH_SHORT).show();
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
        if (getIntent().getExtras().getParcelable("p") != null) {
            photo = getIntent().getExtras().getParcelable("p");
            index = getIntent().getExtras().getInt("index");

            albumId.setText(photo.getAlbumId());
            photo_id.setText(photo.getId());
            photo_title.setText(photo.getTitle());
            thumbnailUrl.setImageURI(Uri.parse(photo.getThumbnailUrl()));
        }
    }
}
