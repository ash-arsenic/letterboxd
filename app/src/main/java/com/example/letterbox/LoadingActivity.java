package com.example.letterbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    public static final String MOVIES = "MOVIES";

    public static final String API_START = "https://api.themoviedb.org/3/movie/";
    public static final String API_END = "?api_key=314778b919b218a848acfcee10a6c785";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        movies = new ArrayList<>();
        LoadDataFromApi api = new LoadDataFromApi();
        String str = api.load(API_START+"top_rated"+API_END);
        if (str.equals("Failed")) {
            Toast.makeText(LoadingActivity.this, "Server Down", Toast.LENGTH_SHORT).show();
        } else {

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
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                Gson gson = new Gson();
                intent.putExtra(MOVIES, gson.toJson(movies));
                startActivity(intent);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}