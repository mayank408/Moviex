package com.example.mayanktripathi.popularmovies.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mayanktripathi.popularmovies.MainActivity;
import com.example.mayanktripathi.popularmovies.MovieSearchApi;
import com.example.mayanktripathi.popularmovies.R;
import com.example.mayanktripathi.popularmovies.TheMovieDbApi;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mayanktripathi on 19/12/16.
 */

public class MovieDes extends AppCompatActivity {


    final String TAG = MainActivity.class.getSimpleName();
    String API_KEY = "2b47a29cda3b623cc10069fd23476ea9";
    final String URL = "http://image.tmdb.org/t/p/w185";


    TextView title, description, rating, realeasedate, language;
    ImageView poster, headposter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_des);


        title = (TextView) findViewById(R.id.movieDetailTitle);
        language = (TextView) findViewById(R.id.language);
        realeasedate = (TextView) findViewById(R.id.releaseDate);
        poster = (ImageView) findViewById(R.id.posterImageDetail);
        headposter = (ImageView) findViewById(R.id.backdrop);
        description = (TextView) findViewById(R.id.movieSummary);


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        final int pos = bundle.getInt("position");


        Log.v("main activity", "pos =  " + pos);


        TheMovieDbApi apiService =
                MovieSearchApi.getClient().create(TheMovieDbApi.class);

        Call<MovieSearch> call = apiService.getresult(API_KEY);
        call.enqueue(new Callback<MovieSearch>() {
                         @Override
                         public void onResponse(Call<MovieSearch> call, Response<MovieSearch> response) {

                             Log.d(TAG, "Number of movies received: " + response.body().getResults().size());

                             String title_movie = response.body().getResults().get(pos).getTitle();
                             String des = response.body().getResults().get(pos).getDescription();
                             String poster_movie = response.body().getResults().get(pos).getPosterUrl();
                             String release = response.body().getResults().get(pos).getRealasedate();
                             String img = response.body().getResults().get(pos).getImgUrl();
                             img = URL + img;

                             title.setText(title_movie);
                             description.setText(des);
                             realeasedate.setText(release);
                             Glide.with(getBaseContext())
                                     .load(poster_movie)
                                     .error(R.mipmap.ic_launcher)
                                     .crossFade()
                                     .into(poster);


                         }

                         @Override
                         public void onFailure(Call<MovieSearch> call, Throwable t) {

                             Log.e(TAG, t.toString());


                         }

                     }

        );


    }
}