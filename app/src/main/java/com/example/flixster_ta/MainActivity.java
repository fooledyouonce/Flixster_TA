package com.example.flixster_ta;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster_ta.adapters.MovieAdapter;
import com.example.flixster_ta.databinding.ActivityMainBinding;
import com.example.flixster_ta.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
//API KEY: adc6e3adbc2a696de289251ea3a7da96
//READ ACCESS TOKEN: eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhZGM2ZTNhZGJjMmE2OTZkZTI4OTI1MWVhM2E3ZGE5NiIsInN1YiI6IjYyN2U4NjUxY2VlNDgxMDA1MDIzZGJlYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Vka7qLp6WTCHKpLxi_aHVgXvRYuBVLt7YsLTIbi5I2I

    //using own API key here from TMDB
    //edited example API request to have "now_playing"
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=adc6e3adbc2a696de289251ea3a7da96";
    public static final String TAG = "MainActivity";
    List<Movie> movies;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        //setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        //create adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        //set adapter on RV
        rvMovies.setAdapter(movieAdapter);
        //set layout manager on RV
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        //getting network requests
        //returns JSON data!
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                //debugging techniques: log statements (logcat) and breakpoints (execution of prgm stops when breakpoint is set, run app in DEBUG mode)
                //DEBUG mode returns actual JSON data!
                //status code = 200
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                //key may not exist, need try/catch
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    //modifies data
                    movies.addAll(Movie.fromJsonArray(results));
                    //alert adapter
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                //status code = 500
                Log.d(TAG, "onFailure");
            }
        });
    }
}