package com.example.letterbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowMoviesFragment extends Fragment {

    private ArrayList<MovieModal> movies;
    private MovieListAdapter adapter;

    public ShowMoviesFragment(ArrayList<MovieModal> movies) {
        this.movies = movies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_movies, container, false);

        adapter = new MovieListAdapter(movies);
        RecyclerView moviesList = view.findViewById(R.id.movies_rv);
        moviesList.setHasFixedSize(true);
        moviesList.setLayoutManager(new GridLayoutManager(getContext(), 4));
        moviesList.setAdapter(adapter);
        return view;
    }

    private class MovieListAdapter extends RecyclerView.Adapter<MovieListViewHolder> {
        private ArrayList<MovieModal> movies;

        public MovieListAdapter(ArrayList<MovieModal> movies) {
            this.movies = movies;
        }

        @NonNull
        @Override
        public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.movies_row, parent, false);

            return new MovieListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MovieListViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Picasso.get().load(movies.get(position).getPosterPath()).into(holder.moviePoster);
            holder.movie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MovieDescriptionActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("MOVIE", gson.toJson(movies.get(position)));
                    startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

    private class MovieListViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        LinearLayout movie;
        public MovieListViewHolder(@NonNull View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.movie_poster);
            movie = itemView.findViewById(R.id.movie_layout);
        }
    }
}