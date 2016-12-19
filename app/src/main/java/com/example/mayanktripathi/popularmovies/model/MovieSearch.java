package com.example.mayanktripathi.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayanktripathi on 18/12/16.
 */

public class MovieSearch {

    @SerializedName("results")
    @Expose
    private List<MovieInfo> results;

    public List<MovieInfo> getResults() {
        return results;
    }

    public void setResults(List<MovieInfo> results) {
        this.results = results;
    }
}
