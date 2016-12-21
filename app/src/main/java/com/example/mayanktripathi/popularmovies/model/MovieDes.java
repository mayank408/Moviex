package com.example.mayanktripathi.popularmovies.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mayanktripathi.popularmovies.Adapter.RecycleviewAdapter;
import com.example.mayanktripathi.popularmovies.Adapter.reviewAdapter;
import com.example.mayanktripathi.popularmovies.MainActivity;
import com.example.mayanktripathi.popularmovies.MovieSearchApi;
import com.example.mayanktripathi.popularmovies.R;
import com.example.mayanktripathi.popularmovies.TheMovieDbApi;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mayanktripathi on 19/12/16.
 */

public class MovieDes extends AppCompatActivity {


    final String TAG = MainActivity.class.getSimpleName();
    String API_KEY = "2b47a29cda3b623cc10069fd23476ea9";
    final String poster_URL = "http://image.tmdb.org/t/p/w1000";
    final String URL = "http://image.tmdb.org/t/p/w300";
    Call<MovieSearch> call;
    Call<MovieSearch> callreview;
    public String id;
    public String videokey;
    public String reviewstext;


    private Context context;
    private RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recylerViewLayoutManager;

    List<String> reviewlist = new ArrayList<>();
    String s = "dsa";



    TextView title, description, rating, realeasedate, language , review;
    ImageView poster, headposter;
    CollapsingToolbarLayout toolbar;


    TheMovieDbApi apiService =
            MovieSearchApi.getClient().create(TheMovieDbApi.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_des);


        toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        title = (TextView) findViewById(R.id.movieDetailTitle);
        language = (TextView) findViewById(R.id.language);
        realeasedate = (TextView) findViewById(R.id.releaseDate);
        poster = (ImageView) findViewById(R.id.posterImageDetail);
        headposter = (ImageView) findViewById(R.id.backdrop);
        description = (TextView) findViewById(R.id.movieSummary);

        context = getApplicationContext();



        recyclerView = (RecyclerView) findViewById(R.id.reviewRv);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewAdapter = new reviewAdapter(context, reviewlist);
        recyclerView.setAdapter(recyclerViewAdapter);






        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        final int pos = bundle.getInt("position");

        Log.v("main activity", "pos =  " + pos);


        if (MainActivity.isSearch)
            call = apiService.getsearch(API_KEY, MainActivity.searchquery);
        else {
            call = apiService.getresult(API_KEY);
        }


        call.enqueue(new Callback<MovieSearch>() {
                         @Override
                         public void onResponse(Call<MovieSearch> call, Response<MovieSearch> response) {

                             Log.d(TAG, "Number of movies received: " + response.body().getResults().size());

                             String title_movie = response.body().getResults().get(pos).getTitle();
                             String des = response.body().getResults().get(pos).getDescription();
                             String poster_movie = response.body().getResults().get(pos).getPosterUrl();
                             String release = response.body().getResults().get(pos).getRealasedate();
                             String img = response.body().getResults().get(pos).getImgUrl();
                             String lang = response.body().getResults().get(pos).getLanguage();
                             id = response.body().getResults().get(pos).getId();

                             getreviews(id);

                             Log.v(TAG, id);
                             img = URL + img;
                             poster_movie = poster_URL + poster_movie;

                             toolbar.setTitle(title_movie);

                             language.setText(lang);
                             title.setText(title_movie);
                             description.setText(des);
                             realeasedate.setText(release);
                             Glide.with(getBaseContext())
                                     .load(poster_movie)
                                     .error(R.mipmap.ic_launcher)
                                     .crossFade()
                                     .into(headposter);

                             Glide.with(getBaseContext())
                                     .load(img)
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


        headposter.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {


                                              call = apiService.getvideo(id, API_KEY);
                                              call.enqueue(new Callback<MovieSearch>() {
                                                  @Override
                                                  public void onResponse(Call<MovieSearch> call, Response<MovieSearch> response) {

                                                      Log.v(TAG, response.body().getResults().size() + "  no");

                                                      videokey = response.body().getResults().get(0).getKey();
                                                      Log.v(TAG, videokey);

                                                      String url = "https://www.youtube.com/watch?v=" + videokey;
                                                      Uri uri = Uri.parse(url);

                                                      // create an intent builder
                                                      CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

                                                      // Begin customizing
                                                      // set toolbar colors
                                                      intentBuilder.setToolbarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                                                      intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                                                      // set start and exit animations
                                                      //intentBuilder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
                                                      intentBuilder.setExitAnimations(getApplicationContext(), android.R.anim.slide_in_left,
                                                              android.R.anim.slide_out_right);

                                                      // build custom tabs intent
                                                      CustomTabsIntent customTabsIntent = intentBuilder.build();

                                                      // launch the url
                                                      customTabsIntent.launchUrl(MovieDes.this, uri);
                                                  }

                                                  @Override
                                                  public void onFailure(Call<MovieSearch> call, Throwable t) {

                                                      Log.e(TAG, t.toString());
                                                      Toast.makeText(MovieDes.this, t.toString(), Toast.LENGTH_SHORT).show();
                                                  }
                                              });

                                          }
                                      }
        );
    }

    public void getreviews(String ids) {

        callreview = apiService.getreviews(ids, API_KEY);

        callreview.enqueue(new Callback<MovieSearch>() {
            @Override
            public void onResponse(Call<MovieSearch> call, Response<MovieSearch> response) {

                Log.v(TAG, response.toString());
                Log.v(TAG, response.body().getResults().size() + "  size");



                for(int i = 0 ; i<response.body().getResults().size() ; i++)
                {
                    reviewstext = response.body().getResults().get(i).getReviews();


                    Log.v(TAG , reviewstext);

                     reviewlist.add(reviewstext);
                     recyclerViewAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<MovieSearch> call, Throwable t) {

                Log.e(TAG, t.toString() + " failed response");

            }
        });


    }


}
