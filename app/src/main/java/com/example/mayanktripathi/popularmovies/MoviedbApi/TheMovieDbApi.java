package com.example.mayanktripathi.popularmovies.MoviedbApi;

import com.example.mayanktripathi.popularmovies.model.MovieSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mayanktripathi on 18/12/16.
 */

public interface TheMovieDbApi {


    @GET("discover/movie?sort_by=popularity.desc")
    Call<MovieSearch> getresult(@Query("api_key") String apiKey);


    @GET("search/movie?&language=en-US&?&page=1&include_adult=false")
    Call<MovieSearch> getsearch(@Query("api_key") String apikey , @Query("query") String query);

    @GET("movie/{id}/videos?&language=en-US")
    Call<MovieSearch> getvideo(@Path("id") String id , @Query("api_key") String apikey );

    @GET("movie/{id}/reviews?&language=en-US&page=1")
    Call<MovieSearch> getreviews(@Path("id") String id , @Query("api_key") String apikey);

    @GET("movie/top_rated?&language=en-US&page=1")
    Call<MovieSearch> getpopular(@Query("api_key") String apiKey);


    @GET("movie/upcoming?&language=en-US&page=1")
    Call<MovieSearch> getupcoming(@Query("api_key") String apiKey);



}
