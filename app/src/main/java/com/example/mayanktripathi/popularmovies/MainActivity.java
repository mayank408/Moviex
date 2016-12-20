package com.example.mayanktripathi.popularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


import com.example.mayanktripathi.popularmovies.Adapter.RecycleviewAdapter;
import com.example.mayanktripathi.popularmovies.model.MovieSearch;
import com.example.mayanktripathi.popularmovies.model.movies;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleviewAdapter adapter;
    private List<movies> moviesList;
    private String imgUrl;
    private String title;
    private String rating;
    private SearchView searchView = null;
    private Menu menu;
    public static String searchquery;
    public static boolean isSearch = false;
    public ProgressDialog pdialog;

    final String TAG = MainActivity.class.getSimpleName();

    // TODO - insert your themoviedb.org API KEY here
    String  API_KEY = "2b47a29cda3b623cc10069fd23476ea9";
    final String URL = "http://image.tmdb.org/t/p/w300";


    TheMovieDbApi apiService =
            MovieSearchApi.getClient().create(TheMovieDbApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pdialog=new ProgressDialog(this);
        pdialog.setCancelable(true);
        pdialog.setMessage("Welcome !! Awesome movies are waiting for you ....");
        pdialog.show();

        recyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        moviesList = new ArrayList<>();
        adapter = new RecycleviewAdapter(this, moviesList);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.onActionViewCollapsed();
                moviesList.removeAll(moviesList);
                recyclerView.removeAllViewsInLayout();
                adapter.notifyDataSetChanged();

                Call<MovieSearch> calls = apiService.getresult(API_KEY);
                calls.enqueue(new Callback<MovieSearch>() {
                    @Override
                    public void onResponse(Call<MovieSearch> call, Response<MovieSearch> response) {

                        if(!isNetworkConnected())
                            Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();


                        handleresponse(response);
                    }

                    @Override
                    public void onFailure(Call<MovieSearch> call, Throwable t) {
                        if(!isNetworkConnected())
                            Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                });


                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchquery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return true;
    }

    private void searchquery(String query) {

        searchquery = query;

        isSearch = true;
        moviesList.removeAll(moviesList);
        recyclerView.removeAllViewsInLayout();
        adapter.notifyDataSetChanged();

        query = query.replace(" ", "+");

        Call<MovieSearch> call = apiService.getsearch(API_KEY, query);
        call.enqueue(new Callback<MovieSearch>() {
            @Override
            public void onResponse(Call<MovieSearch> call, Response<MovieSearch> response) {

                handleresponse(response);
            }

            @Override
            public void onFailure(Call<MovieSearch> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    private void handleresponse(Response<MovieSearch> response) {

        Log.d(TAG, "Number of movies quered: " + response.body().getResults().size());

        if(response.body().getResults().size()==0)
            Toast.makeText(MainActivity.this, "No Movie Found", Toast.LENGTH_SHORT).show();

        for (int i = 0; i < response.body().getResults().size(); i++) {
            title = response.body().getResults().get(i).getTitle();
            rating = response.body().getResults().get(i).getRating();
            imgUrl = response.body().getResults().get(i).getImgUrl();
            imgUrl = URL + imgUrl;


            moviesList.add(new movies(title, rating, imgUrl));
            adapter.notifyDataSetChanged();


            Log.v(TAG, imgUrl);

        }

    }



    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));

                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {

        isSearch = false;


        Call<MovieSearch> call = apiService.getresult(API_KEY);
        call.enqueue(new Callback<MovieSearch>() {
            @Override
            public void onResponse(Call<MovieSearch>call, Response<MovieSearch> response) {

                pdialog.hide();
                handleresponse(response);

            }

            @Override
            public void onFailure(Call<MovieSearch>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}


