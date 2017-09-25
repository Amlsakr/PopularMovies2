package com.example.aml.popularmovies.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aml.popularmovies.Data.MovieAdapter;
import com.example.aml.popularmovies.Data.MovieData;
import com.example.aml.popularmovies.R;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    TextView title;
    ImageView poster;
    TextView overview;
    TextView rate;
    TextView releasDate;
    MovieData movieData;
    TextView trailers;
    String id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            movieData = (MovieData) bundle.getSerializable("object");

            title = (TextView) findViewById(R.id.title);
            title.setText(movieData.getTitle());

            poster = (ImageView) findViewById(R.id.poster_image);
            Picasso.with(this).load(MovieAdapter.base_url + movieData.getMoviePoster()).into(poster);

            overview = (TextView) findViewById(R.id.overview);
            overview.setMovementMethod(new ScrollingMovementMethod());
            overview.setText(movieData.getOverview());

            rate = (TextView) findViewById(R.id.rate);
            rate.setText(movieData.getRate());

            releasDate = (TextView) findViewById(R.id.date);
            releasDate.setText(movieData.getDate());

            id = movieData.getID();


        }
    }
}
