package com.example.flicks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.example.flicks.MainActivity.API_BASE_URL;
import static com.example.flicks.MainActivity.API_KEY_PARAM;

public class MovieTrailerActivity extends YouTubeBaseActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        final Integer movieId = getIntent().getIntExtra("MOVIE_ID",0);



        String videoUrl = API_BASE_URL + String.format("/movie/"+movieId+"/videos");
        Log.d("VIDEO",videoUrl);


        RequestParams videoParams = new RequestParams();
        // api key param put in
        videoParams.put(API_KEY_PARAM, getString(R.string.api_key));

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(videoUrl, videoParams, new JsonHttpResponseHandler(){


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // load the results into movie list
                try {
                    setYoutube(response.getJSONArray("results").getJSONObject(0).getString("key"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Failed to get data","Not ok");
            }
        });

    }


    public void setYoutube(final String youtubeKey) {

        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        // initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });

    }
}
