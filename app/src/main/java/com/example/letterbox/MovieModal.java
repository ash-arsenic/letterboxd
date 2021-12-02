package com.example.letterbox;

public class MovieModal {
    String poster;
    String title;
    String year;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public MovieModal(String poster, String title, String year) {
        this.poster = poster;
        this.title = title.toUpperCase();
        this.year = year;
    }
}
