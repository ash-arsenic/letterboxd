package com.example.letterbox;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewsFragment extends Fragment {
    private ArrayList<ReviewModal> reviews;
    private ReviewsAdapter adapter;
    public ReviewsFragment(ArrayList<ReviewModal> reviews) {
        this.reviews = reviews;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        adapter = new ReviewsAdapter(reviews);
        RecyclerView reviewsList = view.findViewById(R.id.reviews_rv);
        reviewsList.setHasFixedSize(true);
        reviewsList.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsList.setAdapter(adapter);
        return view;
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
            View view = inflater.inflate(R.layout.reviews_rows, parent, false);

            return new ReviewsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
            holder.title.setText(reviews.get(position).getMovieTitle()+" ("+reviews.get(position).getMovieYear()+")");
            holder.review.setText(reviews.get(position).getReview());
            holder.username.setText(reviews.get(position).getUsername());
            holder.movieRatings.setText(reviews.get(position).getRating());
            Picasso.get().load(reviews.get(position).getMoviePoster()).into(holder.poster);
        }

        @Override
        public int getItemCount() {
            return reviews.size();
        }
    }

    private class ReviewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, username, review, movieRatings;
        ImageView poster;
        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.movie_title_r);
            username = itemView.findViewById(R.id.username_r);
            poster = itemView.findViewById(R.id.poster);
            review = itemView.findViewById(R.id.reviews);

            movieRatings = itemView.findViewById(R.id.ratings);
        }
    }
}