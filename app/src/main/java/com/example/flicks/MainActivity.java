package com.example.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.flicks.models.Config;
import com.example.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    //constants
    //base URL for api

    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    //API key param

    public final static String API_KEY_PARAM = "api_key";
    //API key


    // LOG tag

    public final static String TAG = "MainActivity";

    // instance fields

    AsyncHttpClient client;


    // the list of currently playing movies

    ArrayList<Movie> movies;


    // the recycler view

    RecyclerView rvMovies;

    // the adapter wired in the recycler view

    MovieAdapter adapter;

    // image config

    Config config;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.i("MainActivity","START");

        //initialize list of movies
        movies = new ArrayList<>();

        // initialize client

        client = new AsyncHttpClient();


        // initialize the adapter -- movies array cannot be reinitialized after this pint

        adapter = new MovieAdapter(movies);

        // resolve the recycler view and connect a layout manager and the adapter

        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);



        // get configuration on app creation
        getConfiguration();



        setupViewListener();















    }


    // get the list of currently playing movies
    private void getNowPlaying(){

        // url
        String url = API_BASE_URL + "/movie/now_playing";
        // set the request parameters
        RequestParams params = new RequestParams();
        // api key param put in
        params.put(API_KEY_PARAM, getString(R.string.api_key));

        client.get(url, params, new JsonHttpResponseHandler(){


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // load the results into movie list
                try {
                    JSONArray results = response.getJSONArray("results");
                    // iterate through results  and create movie objects
                    for(int i = 0; i < results.length();i++)
                    {
                        // create movie objects and remember them
                        Movie movie = new Movie(results.getJSONObject(i));








                        movies.add(movie);
                        // notify adapter a row was added at last position

                        adapter.notifyItemInserted(movies.size()-1);



                    }

                    Log.i(TAG,String.format("Loaded %s movies",results.length()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get data from now playing endpoint",throwable,true);
            }
        });


//        for(int i = 0; i < movies.size();i++)
//        {
//            url = API_BASE_URL + String.format(" /movie/%d/videos", rvMovies.getId());
//
//            params = new RequestParams();
//            // api key param put in
//            params.put(API_KEY_PARAM, getString(R.string.api_key));
//
//            client.get(url, params, new JsonHttpResponseHandler(){
//
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    // load the results into movie list
//                    try {
//                        JSONObject results = response.getJSONObject("results");
//                        String movieKey = results.getString("key");
//                        movies.get(i).setVideoKey(movieKey);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    logError("Failed to get data from now playing endpoint",throwable,true);
//                }
//            });
//
//        }


    }

    // set up listener for which movie was selected
    private void setupViewListener()
    {
        rvMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OK","OK");
            }
        });





    }

    // get config of the API

    private void getConfiguration() {

        // url
        String url = API_BASE_URL + "/configuration";
        // set the request parameters
        RequestParams params = new RequestParams();
        // api key param put in
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        // execute get method on url

        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);



                try {

                    config = new Config(response);

                    Log.i(TAG,String.format("Loaded config with imageBaseUrl as %s and size of %s",config.getImageBaseUrl(),config.getPosterSize()));
                    getNowPlaying();

                    // pass config to adapter
                     adapter.setConfig(config);






                } catch (JSONException e) {
                    logError("Failed parsing configuration",e,true);
                }






            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting configuration", throwable, true);


            }
        });

        // get now playing movie list



    }
    // handle erros lof and alert user
    private void logError(String message, Throwable error, boolean alertUser)
    {

        //display the message
        Log.e(TAG,message,error);

        // notify user if flag is true

        if(alertUser)
        {
            //show toast with error message

            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        }

    }

}
