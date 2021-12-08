package com.example.letterbox;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDescriptionActivity extends AppCompatActivity {
    boolean adult;
    String backdropPath, homepage, imdbId, originalLanguage,  originalTitle;
    String overview, posterPath, productionCountry, releaseDate, status, title, producer;
    long budget, voteCount, id, runtime;
    double popularity, voteAverage;
    ArrayList<String> genres, imageList;
    ArrayList<ReviewModal> reviewModals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);
        this.getSupportActionBar().hide();
        String str = getIntent().getStringExtra("MOVIE");
        String images = getIntent().getStringExtra("IMAGES");
        String reviews = getIntent().getStringExtra("REVIEWS");
        Log.d("MOVIE_D", reviews);
        imageList = new ArrayList<>();
        genres = new ArrayList<>();
        reviewModals = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(str);
            adult = object.getBoolean("adult");
            backdropPath = object.getString("backdrop_path");
            budget = object.getLong("budget");
            JSONArray genre = object.getJSONArray("genres");
            for(int i=0; i<genre.length(); i++) {
                JSONObject o = genre.getJSONObject(i);
                genres.add(o.getString("name"));
            }
            homepage = object.getString("homepage");
            id = object.getLong("id");
            voteCount = object.getLong("vote_count");
            imdbId  = object.getString("imdb_id");
            runtime = object.getLong("runtime");
            originalLanguage = object.getString("original_language");
            originalTitle = object.getString("original_title");
            overview  = object.getString("overview");
            popularity  = object.getDouble("popularity");
            voteAverage  = object.getDouble("vote_average");
            releaseDate = object.getString("release_date");
            status = object.getString("status");
            title  = object.getString("title");
            posterPath = "https://image.tmdb.org/t/p/w500"+object.getString("poster_path");
            JSONArray array = object.getJSONArray("production_companies");
            producer = array.getJSONObject(0).getString("name");


            JSONObject imageObject = new JSONObject(images);
            JSONArray imageJsonArray = imageObject.getJSONArray("backdrops");

            for(int i=0; i<imageJsonArray.length(); i++) {
                JSONObject obj = imageJsonArray.getJSONObject(i);
                imageList.add("https://image.tmdb.org/t/p/w500"+obj.getString("file_path"));
            }

            JSONObject reviewObject = new JSONObject(reviews);
            JSONArray reviewJsonArray = reviewObject.getJSONArray("results");
            for(int i=0; i<reviewJsonArray.length(); i++) {
                JSONObject obj = reviewJsonArray.getJSONObject(i);
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
                reviewModals.add(new ReviewModal(posterPath, title, releaseDate, String.valueOf(rating), username, review));
            }

            // Toolbar
            CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar);
            toolbarLayout.setTitle(title);

            ImageView movieBanner = findViewById(R.id.movie_banner);
            Picasso.get().load(imageList.get(0)).into(movieBanner);

            FloatingActionButton watchMovieButton = findViewById(R.id.watch_movie);
            watchMovieButton.setEnabled(true);
            watchMovieButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/"+imdbId+"/?ref_=nv_sr_srsg_0"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            // Rest of the page
            TextView director = findViewById(R.id.director_name);
            director.setText(producer);

            TextView releaseDateTV = findViewById(R.id.release_date_d);
            releaseDateTV.setText(releaseDate);

            TextView runTimeTV = findViewById(R.id.runtime_d);
            runTimeTV.setText(String.valueOf(runtime)+" mins");

            ImageView posterIV = findViewById(R.id.movie_poster_d);
            Picasso.get().load(posterPath).into(posterIV);

            Button trailer = findViewById(R.id.trailer_d);
            trailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/-FmWuCgJmxo"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            TextView overviewTV = findViewById(R.id.overview_d);
            overviewTV.setText(overview);

            //Ratings
            RatingBar ratingBar = findViewById(R.id.ratingBar);
            ratingBar.setRating((float) voteAverage/2);

            TextView voteCountTV = findViewById(R.id.vote_count_d);
            voteCountTV.setText("Vote count: "+String.valueOf(voteCount));

            TextView avgVoteTV = findViewById(R.id.avg_vote_d);
            avgVoteTV.setText("Average vote: "+String.valueOf(voteAverage));


            //Genre
            RecyclerView genreList = findViewById(R.id.genre_list);
            genreList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            genreList.setHasFixedSize(true);
            genreList.setAdapter(new GenreAdapter(genres));

            //Gallery
            RecyclerView galleryList = findViewById(R.id.gallery_list);
            galleryList.setHasFixedSize(true);
            galleryList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true));
            galleryList.setAdapter(new GalleryAdapter(imageList));

            // Reviews
            RecyclerView reviewList = findViewById(R.id.review_list_d);
            reviewList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            reviewList.setHasFixedSize(true);
            reviewList.setAdapter(new ReviewsAdapter(reviewModals));

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MovieDescriptionActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
            finish();
        }
//        title = findViewById(R.id.title_desc);
////        title.setText(movie.getTitle());
//
//        year = findViewById(R.id.year_desc);
////        year.setText(movie.getYear());
//
//        poster = findViewById(R.id.poster_desc);
////        Picasso.get().load(movie.getPosterPath()).into(poster);
//
//        time = findViewById(R.id.time_desc);
//        director = findViewById(R.id.directer_desc);
//        description = findViewById(R.id.description_desc);
//        trailer = findViewById(R.id.trailer_desc);
//        trailer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/-FmWuCgJmxo"));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });
//        ArrayList<ReviewModal> data = new ArrayList<>();
//        data.add(new ReviewModal("https://m.media-amazon.com/images/I/519NBNHX5BL._SY445_.jpg", "Shawsank Redemption", "1998", "4", "Sergio", "My GOD jane companion really knows what she's doing. This is my joker"));
//        data.add(new ReviewModal("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg", "the godfather", "1999", "2", "Giada", "Congrats to tick, tick... boom for being a james corden-free musical"));
//        data.add(new ReviewModal("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg", "the godfather", "1999", "5", "Jonathan", "I can't tell if Jared Leto was amazing or terrible. All I know is it's chic."));
//
//        reviews = findViewById(R.id.reviews_desc);
//        reviews.setHasFixedSize(true);
//        reviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        reviews.setAdapter(new ReviewsAdapter(data));
    }
    private class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
        ArrayList<String> gallery;

        public GalleryAdapter(ArrayList<String> gallery) {
            this.gallery = gallery;
            Log.d("MOVIE_D", String.valueOf(gallery.size()));
        }

        @NonNull
        @Override
        public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.gallery_row, parent, false);

            return new GalleryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
            Picasso.get().load(gallery.get(position)).into(holder.gallery);
//            Glide.with(MovieDescriptionActivity.this).load(gallery.get(position)).into(holder.gallery);
        }

        @Override
        public int getItemCount() {
            if(gallery.size()>3) {
                return 4;
            }
            return gallery.size();
        }
    }
    private class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView gallery;
        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            gallery = itemView.findViewById(R.id.movie_poster_g);
        }
    }

    private class GenreAdapter extends RecyclerView.Adapter<GenreViewHolder> {
        ArrayList<String> genres;

        public GenreAdapter(ArrayList<String> genres) {
            this.genres = genres;
        }

        @NonNull
        @Override
        public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.genre_row, parent, false);

            return new GenreViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
            holder.genreName.setText(genres.get(position));
        }

        @Override
        public int getItemCount() {
            return genres.size();
        }
    }
    private class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView genreName;
        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genreName = itemView.findViewById(R.id.genre_name);
        }
    }
    private class ReviewsAdapter extends RecyclerView.Adapter<ReviewsViewHolder> {
        ArrayList<ReviewModal> reviews;

        public ReviewsAdapter(ArrayList<ReviewModal> reviews) {
            this.reviews = reviews;
        }

        @NonNull
        @Override
        public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.sub, parent, false);

            return new ReviewsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
            holder.review.setText(reviews.get(position).getReview());
            holder.username.setText(reviews.get(position).getUsername());
            holder.movieRatings.setText(reviews.get(position).getRating());
        }

        @Override
        public int getItemCount() {
            return reviews.size();
        }
    }
    private class ReviewsViewHolder extends RecyclerView.ViewHolder {
        TextView username, review, movieRatings;
        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username_sub);
            review = itemView.findViewById(R.id.reviews_sub);
            movieRatings = itemView.findViewById(R.id.ratings_sub);
        }
    }
}