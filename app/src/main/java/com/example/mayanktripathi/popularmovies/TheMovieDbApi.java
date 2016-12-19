package com.example.mayanktripathi.popularmovies;

import com.example.mayanktripathi.popularmovies.model.MovieSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mayanktripathi on 18/12/16.
 */

public interface TheMovieDbApi {


    @GET("discover/movie?sort_by=popularity.desc")
    Call<MovieSearch> getresult(@Query("api_key") String apiKey);

}
