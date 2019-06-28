package com.example.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {

    // base url for images
    String imageBaseUrl;

    // poster size for images

    String posterSize;

    // bakcgrocp size for rotating
    String backdropSize;


    public Config(JSONObject object) throws JSONException {
        // get images json object

        JSONObject images = object.getJSONObject("images");
        // get the base url


        imageBaseUrl = images.getString("secure_base_url");


        // get poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");

        //use index 3 of the response and have default
        posterSize = posterSizeOptions.optString(3,"w342");


        // parsethe backdrop sizes and the use the option
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOptions.optString(1,"w780");


    }

    // helper method for creating urls

    public String getImageUrl(String size, String path){

        return String.format("%s%s%s",imageBaseUrl,size,path);
    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
