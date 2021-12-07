package com.example.letterbox;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MovieDescriptionActivity extends AppCompatActivity {
    private TextView title, year, director, time, description;
    private Button trailer;
    private RecyclerView reviews;
    private ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);

        Gson gson = new Gson();
        Type type = new TypeToken<MovieModal>() {}.getType();
        MovieModal movie = gson.fromJson(getIntent().getStringExtra("MOVIE"), type);

        title = findViewById(R.id.title_desc);
        title.setText(movie.getTitle());

        year = findViewById(R.id.year_desc);
//        year.setText(movie.getYear());

        poster = findViewById(R.id.poster_desc);
        Picasso.get().load(movie.getPosterPath()).into(poster);

        time = findViewById(R.id.time_desc);
        director = findViewById(R.id.directer_desc);
        description = findViewById(R.id.description_desc);
        trailer = findViewById(R.id.trailer_desc);
        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/-FmWuCgJmxo"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        ArrayList<ReviewModal> data = new ArrayList<>();
        data.add(new ReviewModal("https://m.media-amazon.com/images/I/519NBNHX5BL._SY445_.jpg", "Shawsank Redemption", "1998", "4", "Sergio", "My GOD jane companion really knows what she's doing. This is my joker"));
        data.add(new ReviewModal("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg", "the godfather", "1999", "2", "Giada", "Congrats to tick, tick... boom for being a james corden-free musical"));
        data.add(new ReviewModal("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg", "the godfather", "1999", "5", "Jonathan", "I can't tell if Jared Leto was amazing or terrible. All I know is it's chic."));

        reviews = findViewById(R.id.reviews_desc);
        reviews.setHasFixedSize(true);
        reviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reviews.setAdapter(new ReviewsAdapter(data));
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