package com.example.letterbox;

import java.util.ArrayList;

public class Database {
    public static ArrayList<MovieModal> getData() {
        ArrayList<MovieModal> data = new ArrayList<>();
        data.add(new MovieModal("https://m.media-amazon.com/images/I/519NBNHX5BL._SY445_.jpg", "Shawsank Redemption", "1998"));
        data.add(new MovieModal("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg", "the godfather", "1999"));
        data.add(new MovieModal("https://images-na.ssl-images-amazon.com/images/I/91ebheNmoUL._RI_.jpg", "The Dark Night", "2000"));
        data.add(new MovieModal("https://upload.wikimedia.org/wikipedia/commons/b/b5/12_Angry_Men_%281957_film_poster%29.jpg", "12 Angry Men", "2001"));
        data.add(new MovieModal("https://m.media-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_FMjpg_UX1000_.jpg", "Schindler's List", "2002"));
        data.add(new MovieModal("https://images.moviesanywhere.com/45bc0ec075bfc0b4d8f184a7cc5bf993/876ed805-83b1-4387-b0d0-62d08c36536d.jpg", "The Lord of the Ring", "2003"));
        data.add(new MovieModal("https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg", "Pulp Fiction", "2004"));
        data.add(new MovieModal("https://m.media-amazon.com/images/M/MV5BMmEzNTkxYjQtZTc0MC00YTVjLTg5ZTEtZWMwOWVlYzY0NWIwXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_FMjpg_UX1000_.jpg", "Fight Club", "2005"));
        data.add(new MovieModal("https://m.media-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_FMjpg_UX1000_.jpg", "Forrest Gump", "2006"));
        data.add(new MovieModal("https://www.themoviedb.org/t/p/w500/ukfxDqM7v5LHdLvsGWaQyXVBYMq.jpg", "The Butterfly Effect", "2005"));

        return data;
    }
    public static ArrayList<ReviewModal> getReviews() {
        ArrayList<ReviewModal> data = new ArrayList<>();

        data.add(new ReviewModal("https://m.media-amazon.com/images/I/519NBNHX5BL._SY445_.jpg", "Shawsank Redemption", "1998", "5", "Karsten", "Just best looking Hollywood movie ever made"));
        data.add(new ReviewModal("https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg", "the godfather", "1999", "5", "Sergio", "Patrizia was, -ahem-, truly CAUGHT IN A BAD ROMANCE"));
        data.add(new ReviewModal("https://images-na.ssl-images-amazon.com/images/I/91ebheNmoUL._RI_.jpg", "The Dark Night", "2000", "5", "Jonathan", "Vanity fair actors roundtable gone wrong"));
        data.add(new ReviewModal("https://upload.wikimedia.org/wikipedia/commons/b/b5/12_Angry_Men_%281957_film_poster%29.jpg", "12 Angry Men", "2001", "5", "Dakota", "Mindblowing, phuurrrr"));
        data.add(new ReviewModal("https://m.media-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_FMjpg_UX1000_.jpg", "Schindler's List", "2002", "5", "Cristiano", "My GOD jane companion really knows what she's doing. This is my joker"));
        data.add(new ReviewModal("https://images.moviesanywhere.com/45bc0ec075bfc0b4d8f184a7cc5bf993/876ed805-83b1-4387-b0d0-62d08c36536d.jpg", "The Lord of the Ring", "2003", "5", "Karen", "Congrats to tick, tick... boom for being a james corden-free musical"));
        data.add(new ReviewModal("https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg", "Pulp Fiction", "2004", "5", "Brat", "Just best looking Hollywood movie ever made"));
        data.add(new ReviewModal("https://m.media-amazon.com/images/M/MV5BMmEzNTkxYjQtZTc0MC00YTVjLTg5ZTEtZWMwOWVlYzY0NWIwXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_FMjpg_UX1000_.jpg", "Fight Club", "2005", "5", "Tom", "I can't tell if Jared Leto was amazing or terrible. All I know is it's chic."));
        data.add(new ReviewModal("https://m.media-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_FMjpg_UX1000_.jpg", "Forrest Gump", "2006", "5", "Phil", "Vanity fair actors roundtable gone wrong"));
        data.add(new ReviewModal("https://www.themoviedb.org/t/p/w500/ukfxDqM7v5LHdLvsGWaQyXVBYMq.jpg", "The Butterfly Effect", "2005", "5", "Matt", "Just best looking Hollywood movie ever made"));

        return data;
    }
}
