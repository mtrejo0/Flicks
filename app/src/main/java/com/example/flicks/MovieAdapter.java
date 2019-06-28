package com.example.flicks;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flicks.models.Config;
import com.example.flicks.models.Movie;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    // list of movies
    static ArrayList<Movie> movies;

    // config needed for image urls

    static Config config;


    // context view for rendering

    Context context ;




    public void setConfig(Config config) {
        this.config = config;
    }


// initialize the list

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }


    // create the moewholder as a static inner class

    public static class ViewHolder extends RecyclerView.ViewHolder {


        // track the view objects

        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivBackdropImage;
        TextView tvRating;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // lookup view objects by id

            ivPosterImage = itemView.findViewById(R.id.ivPosterImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);

            ivBackdropImage = itemView.findViewById(R.id.ivBackdropImage);
            tvRating = itemView.findViewById(R.id.tvRating);







            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i = getAdapterPosition();
                    Movie m = movies.get(i);




                    Log.i("POSITION",""+i);

                    // create new activity

                    Intent intent = new Intent(v.getContext(),MovieDescription.class);

                    // pass the data being edited

                    intent.putExtra("MOVIE_NAME",m.getTitle());
                    intent.putExtra("MOVIE_SYNOPSIS",m.getOverview());
                    intent.putExtra("MOVIE_DESCRIPTION",m.getRating());


                    String imageUrl = config.getImageUrl(config.getBackdropSize(),m.getBackDropPath());

                    intent.putExtra("MOVIE_POSTER_LOCATION", imageUrl);

                    intent.putExtra("MOVIE_RATING",m.getRating());
                    intent.putExtra("POPULARITY",m.getPopularity());
                    intent.putExtra("MOVIE_ID",m.getId());


                    // display the activity

                    v.getContext().startActivity(intent);

                }
            });





        }



    }



    // creates and inflates a new view
    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // get the context and create the inflater

        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // create the view using the item_movie layout

        View movieView = inflater.inflate(R.layout.item_movie,viewGroup,false);

        // return a new viewholder

        return new ViewHolder(movieView);

    }


    // binds and inflated view to a new item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolderolder, int i) {
        // get the movie data from the position


        Movie movie = movies.get(i);
        // populate the view with the movie data

        viewHolderolder.tvTitle.setText(movie.getTitle());


        viewHolderolder.tvOverview.setText(movie.getOverview());









        /// determine orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;


        // build url for poster image

         String imageUrl = config.getImageUrl(config.getPosterSize(),movie.getPosterPath());
        // if in portrait mode load the poster image
        if(!isPortrait)
        {
            imageUrl = config.getImageUrl(config.getBackdropSize(),movie.getBackDropPath());


        }
        else
        {
            Double rating = movie.getRating();
            viewHolderolder.tvRating.setText(Double.toString(rating));
            // maps rating to a color
            int ratingColor;
            if (rating < 5)
            {
                ratingColor = Color.rgb(255,(int)(rating/5*255),0);
            }
            else
            {
                ratingColor = Color.rgb((int)(255 - rating/5*255),255,0);
            }

            viewHolderolder.tvRating.setBackgroundColor(ratingColor);
        }


        int placeholderId = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;

        ImageView imageView = isPortrait ? viewHolderolder.ivPosterImage : viewHolderolder.ivBackdropImage;

        // load image with Glide



        Glide.with(context)
                .load(imageUrl)



                .error(placeholderId)

                .placeholder(placeholderId)
                .bitmapTransform(new RoundedCornersTransformation(context, 50, 30))





                .into(imageView);









    }

    // returns the total number of items in the list

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
