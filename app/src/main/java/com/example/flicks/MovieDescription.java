package com.example.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);


        // get valuespassed in from intent
        String movieName = getIntent().getStringExtra("MOVIE_NAME");
        String synopsis = getIntent().getStringExtra("MOVIE_SYNOPSIS");
        String imageUrl = getIntent().getStringExtra("MOVIE_POSTER_LOCATION");
        Double rating = getIntent().getDoubleExtra("MOVIE_RATING",0);
        Double popularity = getIntent().getDoubleExtra("POPULARITY",0);


        // create ratingbar
        RatingBar rbRating = findViewById(R.id.rbRating);
        // make it out of 5 stars
        rbRating.setNumStars(5);
        // make it able to display small float values
        rbRating.setStepSize(.1f);

        // prevent user from editing the rating
        rbRating.setIsIndicator(true);

        // map rating to stars
        rbRating.setRating((float)(rating/10*5));


        // set popularity text
        TextView tvPopularity = findViewById(R.id.tvPopularity);
        tvPopularity.setText("Popularity: "+popularity);


        // set text values to what they should be
        TextView tvMovieName = findViewById(R.id.tvMovieName);
        tvMovieName.setText(movieName);

        TextView tvMovieSynopsis = findViewById(R.id.tvMovieSynopsis);
        tvMovieSynopsis.setText(synopsis);


        ImageView poster = findViewById(R.id.ivPoster);


        // display the backdrop image of the poster
        Glide.with(poster.getContext())


                .load(imageUrl)



                .error(R.drawable.flicks_backdrop_placeholder)

                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .bitmapTransform(new RoundedCornersTransformation(poster.getContext(), 50, 30))





                .into(poster);



    }
}
