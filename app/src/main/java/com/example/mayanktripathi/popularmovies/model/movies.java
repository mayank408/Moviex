package com.example.mayanktripathi.popularmovies.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayanktripathi on 17/12/16.
 */

public class movies {

    private String title;
    private String genre;
    private String imgUrl;


    public movies(String title, String genre, String imgUrl) {
        this.title = title;
        this.genre = genre;
        this.imgUrl = imgUrl;
    }

    public movies(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


}


