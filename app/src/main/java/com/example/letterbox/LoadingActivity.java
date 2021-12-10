package com.example.letterbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadingActivity extends AppCompatActivity implements LoadDataFromApi.ApiListener {
    private ArrayList<MovieModal> movies;
    private ArrayList<MovieModal> topRated;
    private ArrayList<MovieModal> latest;
    private ArrayList<MovieModal> upcoming;

    public static ArrayList<MovieModal> watchList;

    private int popularPN = 1;
    private int topRatedPN = 1;
    private int latestPN = 1;
    private int upcomingPN = 1;
    private ArrayList<ReviewModal> reviewModals;
    public static final String MOVIES = "MOVIES";
    LoadDataFromApi api ;
    LoadDataFromApi tapi ;
    LoadDataFromApi lapi ;
    LoadDataFromApi uapi ;
    public static final String API_START = "https://api.themoviedb.org/3/movie/";
    public static final String API_END = "?api_key=314778b919b218a848acfcee10a6c785";
    private int pageNo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        movies = new ArrayList<>();
        topRated = new ArrayList<>();
        latest = new ArrayList<>();
        upcoming = new ArrayList<>();
        reviewModals = new ArrayList<>();
        loadData();
        api = new LoadDataFromApi(LoadingActivity.this);
        tapi = new LoadDataFromApi(LoadingActivity.this);
        lapi = new LoadDataFromApi(LoadingActivity.this);
        uapi = new LoadDataFromApi(LoadingActivity.this);
        api.load(API_START+"popular"+API_END+"&page="+popularPN, "popular");
        tapi.load(API_START+"top_rated"+API_END+"&page="+topRatedPN, "top_rated");
        lapi.load(API_START+"now_playing"+API_END+"&page="+latestPN, "now_playing");
        uapi.load(API_START+"upcoming"+API_END+"&page="+upcomingPN, "upcoming");
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(API_START+"popular"+API_END).build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(LoadingActivity.this, "No Network", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                String str = response.body().string();
//
//                try {
//                    JSONObject jsonObject = new JSONObject(str);
//                    JSONArray jsonArray = jsonObject.getJSONArray("results");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        boolean adult = object.getBoolean("adult");
//                        String backdropPath = object.getString("backdrop_path");
//                        JSONArray genre = object.getJSONArray("genre_ids");
//                        ArrayList<Integer> genreIds = new ArrayList<>();
//                        for (int j = 0; j < genre.length(); j++) {
//                            genreIds.add(Integer.parseInt(genre.get(j).toString()));
//                        }
//                        int id = object.getInt("id");
//                        String originalLanguage = object.getString("original_language");
//                        String originalTitle = object.getString("original_title");
//                        String overview = object.getString("overview");
//                        double popularity = object.getDouble("popularity");
//                        String posterPath = object.getString("poster_path");
//                        String releaseDate = object.getString("release_date");
//                        String title = object.getString("title");
//                        String video = object.getString("video");
//                        String voteAverage = object.getString("vote_average");
//                        String voteCount = object.getString("vote_count");
//                        Log.d("POPULAR", String.valueOf(popularity));
//                        MovieModal movie = new MovieModal(adult, backdropPath, genreIds, id, originalLanguage, originalTitle, overview, popularity, posterPath, releaseDate, title, video, voteAverage, voteCount);
//                        movies.add(movie);
//                    }
//
//                    final int[] i = {0};
//                    final int[] currentId = {0};
//                    while (i[0] < movies.size()) {
//                        MovieModal modal = movies.get(i[0]);
//                        if(currentId[0] == modal.getId()) {
//                            continue;
//                        }
//                        currentId[0] = modal.getId();
//                        OkHttpClient okHttpClient = new OkHttpClient();
//                        Request request = new Request.Builder().url(API_START+String.valueOf(modal.getId())+"/reviews"+API_END).build();
//                        okHttpClient.newCall(request).enqueue(new Callback() {
//                            @Override
//                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                                Toast.makeText(LoadingActivity.this, "No network", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                                String reviews = response.body().string();
//                                JSONObject reviewObject = null;
//                                try {
//                                    reviewObject = new JSONObject(reviews);
//                                    JSONArray reviewJsonArray = reviewObject.getJSONArray("results");
//                                    for(int j=0; j<reviewJsonArray.length(); j++) {
//                                        JSONObject obj = reviewJsonArray.getJSONObject(j);
//                                        String username = obj.getString("author");
//                                        String review = obj.getString("content");
//                                        String rating="9.0";
//                                        try {
//                                            rating = String.valueOf(obj.getJSONObject("author_details").getDouble("rating"));
//                                        }catch(JSONException e) {
//
//                                        }
//                                        if(rating.equals("")) {
//                                            rating = "9.0";
//                                        }
//                                        reviewModals.add(new ReviewModal(modal.getPosterPath(), modal.getTitle(), modal.getReleaseDate(), String.valueOf(rating), username, review));
//                                        currentId[0] = modal.getId();
//
//                                        i[0]++;
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    Toast.makeText(LoadingActivity.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
//                                    finish();
//                                }
//                            }
//                        });
//                    }
//                    Log.d("MOVIE_D", String.valueOf(reviewModals.size()));
//                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
//                    Gson gson = new Gson();
//                    intent.putExtra(MOVIES, gson.toJson(movies));
//                    intent.putExtra("REVIEWS", gson.toJson(reviewModals));
//                    startActivity(intent);
//                    finish();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(LoadingActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    @Override
    public void send(String json, String type) {
        if (json.equals("Failed")) {
            Toast.makeText(LoadingActivity.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
        } else {
            switch (type) {
                case "popular":
//                    Log.d("ASHISH", type+": "+popularPN/);
                    movies.addAll(parseJson(json));
                    if (popularPN<3) {
                        popularPN++;
                        api.load(API_START+"popular"+API_END+"&page="+popularPN, "popular");
                    }
                    break;
                case "top_rated":
//                    Log.d("ASHISH", type+": "+topRatedPN);
                    topRated.addAll(parseJson(json));
                    if (topRatedPN<3) {
                        topRatedPN++;
                        tapi.load(API_START+"top_rated"+API_END+"&page="+topRatedPN, "top_rated");
                    }
                    break;
                case "now_playing":
//                    Log.d("ASHISH", type+": "+latestPN);
                    latest.addAll(parseJson(json));
                    if (latestPN<3) {
                        latestPN++;
                        lapi.load(API_START+"now_playing"+API_END+"&page="+latestPN, "now_playing");
                    }
                    break;
                case "upcoming":
//                    Log.d("ASHISH", type+": "+upcomingPN);
                    upcoming.addAll(parseJson(json));
                    if (upcomingPN<3) {
                        upcomingPN++;
                        uapi.load(API_START+"upcoming"+API_END+"&page="+upcomingPN, "upcoming");
                    }
                    break;
            }
            if(popularPN+topRatedPN+latestPN+upcomingPN>=12) {
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                Gson gson = new Gson();
                intent.putExtra(MOVIES, gson.toJson(movies));
                intent.putExtra("POPULAR", gson.toJson(movies));
                intent.putExtra("TOP_RATED", gson.toJson(topRated));
                intent.putExtra("LATEST", gson.toJson(latest));
                intent.putExtra("UPCOMING", gson.toJson(upcoming));

                intent.putExtra("REVIEWS", gson.toJson(reviewModals));
                startActivity(intent);
                finish();
            }
        }

//        if(json.equals("Failed")) {
//            Toast.makeText(LoadingActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
//        } else {
//            switch (type) {
//                case "popular":
//                    movies.addAll(parseJson(json));
//                    break;
//                case "top_rated":
//                    topRated.addAll(parseJson(json));
//                    break;
//                case "latest":
//                    latest.addAll(parseJson(json));
//                    break;
//                case "upcoming":
//                    upcoming.addAll(parseJson(json));
//                    break;
//            }
//            if(movies.size()+topRated.size()+upcoming.size()+latest.size()>=240) {
//                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
//                Gson gson = new Gson();
//                intent.putExtra(MOVIES, gson.toJson(movies));
//                intent.putExtra("POPULAR", gson.toJson(movies));
//                intent.putExtra("TOP_RATED", gson.toJson(topRated));
//                intent.putExtra("LATEST", gson.toJson(latest));
//                intent.putExtra("UPCOMING", gson.toJson(upcoming));
//
//                intent.putExtra("REVIEWS", gson.toJson(reviewModals));
//                startActivity(intent);
//                finish();
//            } else {
//                pageNo++;
//                callApi(pageNo);
//            }
//        }
    }

    ArrayList<MovieModal> parseJson(String str) {
        ArrayList<MovieModal> movies = new ArrayList<>();
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
        }catch(JSONException e) {
            e.printStackTrace();
            Toast.makeText(LoadingActivity.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
        }
        return movies;
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("WATCHLIST", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("LIST", "");
        Type type = new TypeToken<ArrayList<MovieModal>>() {}.getType();
        watchList = gson.fromJson(json, type);
        if(watchList == null) {
            watchList = new ArrayList<>();
        }
    }
}