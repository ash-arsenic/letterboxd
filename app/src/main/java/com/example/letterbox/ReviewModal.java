package com.example.letterbox;

public class ReviewModal {
    String movieTitle;
    String movieYear;
    String moviePoster;
    String rating;
    String username;
    String review;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public ReviewModal(String moviePoster, String movieTitle, String movieYear, String rating, String username, String review) {
        this.movieTitle = movieTitle.toUpperCase();
        this.movieYear = movieYear;
        this.moviePoster = moviePoster;
        this.rating = rating;
        this.username = username;
        this.review = review;
    }
}
