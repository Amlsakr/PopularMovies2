package com.example.aml.popularmovies.Data;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.aml.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aml on 13/04/17.
 */

public class MovieAdapter   extends ArrayAdapter {
    private List<MovieData> Movielist;
    private Context context;
   public final static String base_url = "http://image.tmdb.org/t/p/w185/";
    private LayoutInflater layoutInflater;
    ImageView imageView;

    public List<MovieData> getMovielist() {
        return Movielist;
    }

    public void setMovielist(List<MovieData> movielist) {
        Movielist = movielist;
    }

    public MovieAdapter(Context cxt, List<MovieData> imageURL) {
        super(cxt, R.layout.grid_item, imageURL);
        this.context = cxt;
        this.Movielist = imageURL;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.grid_item, parent, false);

        imageView = (ImageView) convertView.findViewById(R.id.image_view);


        MovieData movieData = new MovieData();
        movieData.setMoviePoster(Movielist.get(position).getMoviePoster());
        Picasso.with(context).load(base_url + Movielist.get(position).getMoviePoster()).into(imageView);

        return convertView;

    }


    @Override
    public int getCount() {
        return Movielist.size();
    }
}

