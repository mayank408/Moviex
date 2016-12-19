package com.example.mayanktripathi.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mayanktripathi on 18/12/16.
 */
public class MovieInfo {


    @SerializedName("original_title")
    @Expose
    private String title;


    @SerializedName("vote_average")
    @Expose
    private String rating;

    @SerializedName("poster_path")
    @Expose
    private String imgUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
