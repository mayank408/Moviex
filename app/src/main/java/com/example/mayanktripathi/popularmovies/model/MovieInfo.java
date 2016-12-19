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

    @SerializedName("overview")
    @Expose
    private String description;

    @SerializedName("release_date")
    @Expose
    private String realasedate;

    @SerializedName("backdrop_path")
    @Expose
    private String posterUrl;

    @SerializedName("original_language")
    @Expose
    private String language;


    @SerializedName("popularity")
    @Expose
    private String popularity;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRealasedate() {
        return realasedate;
    }

    public void setRealasedate(String realasedate) {
        this.realasedate = realasedate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

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
