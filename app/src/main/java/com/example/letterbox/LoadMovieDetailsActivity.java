package com.example.letterbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadMovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_movie_details);
        int id = getIntent().getIntExtra("MOVIE_ID", 580489);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(LoadingActivity.API_START+String.valueOf(id)+LoadingActivity.API_END).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoadMovieDetailsActivity.this, "No Network", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String str = response.body().string();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(LoadingActivity.API_START+String.valueOf(id)+"/images"+LoadingActivity.API_END).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoadMovieDetailsActivity.this, "No Network", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String images = response.body().string();

                        OkHttpClient okHttpClient = new OkHttpClient();
                        Request request = new Request.Builder().url(LoadingActivity.API_START+String.valueOf(id)+"/reviews"+LoadingActivity.API_END).build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoadMovieDetailsActivity.this, "No Network", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                String reviews = response.body().string();

                                Intent intent = new Intent(LoadMovieDetailsActivity.this, MovieDescriptionActivity.class);
                                intent.putExtra("MOVIE", str);
                                intent.putExtra("IMAGES", images);
                                intent.putExtra("REVIEWS", reviews);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });
            }
        });
    }
}