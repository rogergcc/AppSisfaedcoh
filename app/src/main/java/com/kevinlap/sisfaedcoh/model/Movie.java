

/*
 * Created by
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.kevinlap.sisfaedcoh.model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    String name;
    String director;
    String poster;
    String duration;
    String genre;
    String price;
    float rating;

    @SerializedName("released")
    boolean isReleased;

    public String getName() {
        return name;
    }

    public String getDirector() {
        return director;
    }

    public String getPoster() {
        return poster;
    }

    public String getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public String getPrice() {
        return price;
    }

    public float getRating() {
        return rating;
    }

    public boolean isReleased() {
        return isReleased;
    }
}
