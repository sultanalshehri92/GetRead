package com.example.sultan.getread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Button;


import com.example.sultan.getread.model.Data;
import com.example.sultan.getread.service.APIService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView textDetails;
    Button btnGetData;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDetails = (TextView) findViewById(R.id.textDetails);
        btnGetData = (Button) findViewById(R.id.btnGetData);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPeopleDetailts();
            }
        });
    }

    private void getPeopleDetailts(){
        showpDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<List<Data>> call = service.getData();

        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Response<List<Data>> response, Retrofit retrofit) {
                List<Data> students = response.body();
                String details = "";
                for (int i = 0; i < students.size(); i++){
                    String name = students.get(i).getName();
                    details += "\n name: " + name;
                }
                textDetails.setText(details);
                hidepDialog();
            }

            @Override
            public void onFailure(Throwable t) {
                hidepDialog();
                textDetails.setText("NN");
            }
        });


    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
