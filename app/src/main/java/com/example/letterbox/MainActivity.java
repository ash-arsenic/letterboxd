package com.example.letterbox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

public class MainActivity extends AppCompatActivity {
    private ArrayList<MovieModal> movies;
    private ArrayList<ReviewModal> reviews;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public static final String URL = "https://api.themoviedb.org/3/movie/popular?api_key=314778b919b218a848acfcee10a6c785";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MovieModal>>() {}.getType();
        movies = gson.fromJson(getIntent().getStringExtra(LoadingActivity.MOVIES), type);
        if(movies == null) {
            movies = new ArrayList<>();
        }

        type = new TypeToken<ArrayList<ReviewModal>>() {}.getType();
        reviews = gson.fromJson(getIntent().getStringExtra("REVIEWS"), type);
//        Log.d("MOVIE_D", reviews.get(0).getReview());
//        loadData();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ShowMoviesFragment(movies));
        fragments.add(new ReviewsFragment(reviews));
        ArrayList<String> tabNames = new ArrayList<>();
        tabNames.add("FILMS");
        tabNames.add("REVIEWS");

        viewPager.setAdapter(new MovieListAdapter(getSupportFragmentManager(), fragments, tabNames));
        tabLayout.setupWithViewPager(viewPager);

    }

    public void loadData() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String str = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        boolean adult = object.getBoolean("adult");
                        String backdropPath = object.getString("backdrop_path");
                        JSONArray genre = object.getJSONArray("genre_ids");
                        ArrayList<Integer> genreIds = new ArrayList<>();
                        for(int j=0; j<genre.length(); j++) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private class MovieListAdapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> fragments;
        ArrayList<String> tabNames;
        public MovieListAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> tabNames) {
            super(fm);
            this.fragments = fragments;
            this.tabNames = tabNames;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames.get(position);
        }
    }
}