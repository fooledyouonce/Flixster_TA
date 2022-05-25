package com.example.flixster_ta.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
//NOTE: this is plain Java! We know how to do this :)
public class Movie {
    int movieId;
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    double rating;

    //required by parcel class
    public Movie() {}

    //constructor to construct json object
    //exception so that if ANY fail, entire constructor fails
    public Movie(JSONObject jsonObject) throws JSONException {
        movieId = jsonObject.getInt("id");
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
    }
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie((movieJsonArray.getJSONObject(i))));
        }
        return movies;
    }
    public String getPosterPath() {
        //configurations API response
        //width is HARDCODED (w342)
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }
    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }
    public String getTitle() {
        return title;
    }
    public String getOverview() {
        return overview;
    }
    public double getRating() { return rating; }

    public int getMovieId() { return movieId; }
}
