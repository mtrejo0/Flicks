package com.example.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {

    // values from the API

    private String title;
    private String overview;
    private String posterPath;
    private String backDropPath;
    private Double rating;
    private Double popularity;
    private Integer id;
    private String videoKey;


    // initialize form JSON data

    public Movie(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backDropPath = object.getString("backdrop_path");
        rating = object.getDouble("vote_average");
        popularity = object.getDouble("popularity");
        id = object.getInt("id");




    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public Double getRating() {
        return rating;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Integer getId() {
        return id;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }
}
