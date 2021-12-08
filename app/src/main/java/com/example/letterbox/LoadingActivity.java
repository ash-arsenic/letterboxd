package com.example.letterbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadingActivity extends AppCompatActivity {
    private ArrayList<MovieModal> movies;
    private ArrayList<ReviewModal> reviewModals;
    public static final String MOVIES = "MOVIES";

    public static final String API_START = "https://api.themoviedb.org/3/movie/";
    public static final String API_END = "?api_key=314778b919b218a848acfcee10a6c785";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        movies = new ArrayList<>();
        reviewModals = new ArrayList<>();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(API_START+"top_rated"+API_END).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoadingActivity.this, "No Network", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String str = response.body().string();

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        boolean adult = object.getBoolean("adult");
                        String backdropPath = object.getString("backdrop_path");
                        JSONArray genre = object.getJSONArray("genre_ids");
                        ArrayList<Integer> genreIds = new ArrayList<>();
                        for (int j = 0; j < genre.length(); j++) {
                            genreIds.add(Integer.parseInt(genre.get(j).toString()));
                        }
                        int id = object.getInt("id");
                        String originalLanguage = object.getString("original_language");
                        String originalTitle = object.getString("original_title");
                        String overview = object.getString("overview");
                        double popularity = object.getDouble("popularity");
                        String posterPath = object.getString("poster_path");
                        String releaseDate = object.getString("release_date");
                        String title = object.getString("title");
                        String video = object.getString("video");
                        String voteAverage = object.getString("vote_average");
                        String voteCount = object.getString("vote_count");
                        Log.d("POPULAR", String.valueOf(popularity));
                        MovieModal movie = new MovieModal(adult, backdropPath, genreIds, id, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title, video, voteAverage, voteCount);
                        movies.add(movie);
                    }

                    final int[] i = {0};
                    final int[] currentId = {0};
                    while (i[0] < movies.size()) {
                        MovieModal modal = movies.get(i[0]);
                        if(currentId[0] == modal.getId()) {
                            continue;
                        }
                        currentId[0] = modal.getId();
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Request request = new Request.Builder().url(API_START+String.valueOf(modal.getId())+"/reviews"+API_END).build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Toast.makeText(LoadingActivity.this, "No network", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                String reviews = response.body().string();
                                JSONObject reviewObject = null;
                                try {
                                    reviewObject = new JSONObject(reviews);
                                    JSONArray reviewJsonArray = reviewObject.getJSONArray("results");
                                    for(int j=0; j<reviewJsonArray.length(); j++) {
                                        JSONObject obj = reviewJsonArray.getJSONObject(j);
                                        String username = obj.getString("author");
                                        String review = obj.getString("content");
                                        String rating="9.0";
                                        try {
                                            rating = String.valueOf(obj.getJSONObject("author_details").getDouble("rating"));
                                        }catch(JSONException e) {

                                        }
                                        if(rating.equals("")) {
                                            rating = "9.0";
                                        }
                                        reviewModals.add(new ReviewModal(modal.getPosterPath(), modal.getTitle(), modal.getReleaseDate(), String.valueOf(rating), username, review));
                                        currentId[0] = modal.getId();

                                        i[0]++;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(LoadingActivity.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    }
                    Log.d("MOVIE_D", String.valueOf(reviewModals.size()));
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra(MOVIES, gson.toJson(movies));
                    intent.putExtra("REVIEWS", gson.toJson(reviewModals));
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoadingActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}