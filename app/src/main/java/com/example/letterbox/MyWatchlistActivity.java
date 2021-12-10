package com.example.letterbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyWatchlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_watchlist);

        RecyclerView list = findViewById(R.id.watchlist_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list.setAdapter(new WatchlistAdapter(LoadingActivity.watchList));

    }
    private class WatchlistAdapter extends RecyclerView.Adapter<WatchlistViewholder> {
        ArrayList<MovieModal> watchlist;

        public WatchlistAdapter(ArrayList<MovieModal> watchlist) {
            this.watchlist = watchlist;
        }

        @NonNull
        @Override
        public WatchlistViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.watchlist_row, parent, false);
            return new WatchlistViewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WatchlistViewholder holder, @SuppressLint("RecyclerView") int position) {
            Picasso.get().load(watchlist.get(position).getPosterPath()).into(holder.poster);
            holder.title.setText(watchlist.get(position).getTitle());
            holder.year.setText(watchlist.get(position).getReleaseDate().substring(0,4));
            holder.rating.setText(watchlist.get(position).getVoteAverage());
            holder.showMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyWatchlistActivity.this, LoadMovieDetailsActivity.class);
                    intent.putExtra("MOVIE_ID", watchlist.get(position).getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return watchlist.size();
        }
    }
    private class WatchlistViewholder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title, year, rating;
        ConstraintLayout showMovie;
        public WatchlistViewholder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.movie_poster_w);
            title = itemView.findViewById(R.id.movie_title_w);
            year = itemView.findViewById(R.id.movie_year_w);
            rating = itemView.findViewById(R.id.ratings_w);
            showMovie = itemView.findViewById(R.id.watchlist_movie);
        }
    }
}