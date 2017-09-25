package com.example.aml.popularmovies.UI;

import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aml.popularmovies.Data.MovieAdapter;
import com.example.aml.popularmovies.Data.MovieData;
import com.example.aml.popularmovies.Data.ReviewAdapter;
import com.example.aml.popularmovies.Data.TrailerAdapter;
import com.example.aml.popularmovies.Provider.FavoritesDB;
import com.example.aml.popularmovies.Provider.contract;
import com.example.aml.popularmovies.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class DetailsFragment extends Fragment {


    TextView title;
    ImageView poster;
    TextView overview;
    TextView rate;
    TextView releasDate;
    MovieData movieData;

    String id = "";
    TextView   t ;
    TextView  review ;
    ImageView favor ;
    FavoritesDB favoritesDB ;
    String trailersKey ;
    String reviewResponse;


    ArrayList <String> trailers = new ArrayList<>();
    ArrayList<String> reviews  = new ArrayList<>();

    TrailerAdapter trailerAdapter ;
    ReviewAdapter reviewAdapter ;


    GridView trGridView ;
    GridView revGridView ;


    public DetailsFragment() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        Bundle bundle = getArguments();

//        if (savedInstanceState != null){
//            movieData = savedInstanceState.getParcelable("moviedata");
//            reviews = savedInstanceState.getStringArrayList("review");
//            trailers = savedInstanceState.getStringArrayList("trailer");
//
//            reviewAdapter.setReviewList(reviews);
//            trailerAdapter.setTrailerList(trailers);
//        }  else  {
            movieData = (MovieData) bundle.getSerializable("object");



        favoritesDB = new FavoritesDB(this.getContext());


       // Log.e("DETAILSFragment" , movieData.getTitle());
        if (bundle != null) {
            movieData = (MovieData) bundle.getSerializable("object");

            title = (TextView) view.findViewById(R.id.title);
            title.setText(movieData.getTitle());

            poster = (ImageView) view.findViewById(R.id.poster_image);
            Picasso.with(getActivity()).load(MovieAdapter.base_url + this.movieData.getMoviePoster()).into(this.poster);
           // Picasso.with(this).load(MovieAdapter.base_url + movieData.getMoviePoster()).into(poster);

            overview = (TextView) view.findViewById(R.id.overview);
            overview.setMovementMethod(new ScrollingMovementMethod());
            overview.setText(movieData.getOverview());

            releasDate = (TextView) view.findViewById(R.id.date);
            releasDate.setText(movieData.getDate());

            rate = (TextView) view.findViewById(R.id.rate);
            rate.setText(movieData.getRate());



            id = movieData.getID();
Log.e("MOVIEID" , id);


            revGridView  = (GridView) view.findViewById(R.id.review_grid_view);


trGridView = (GridView) view.findViewById(R.id.trailer_grid_view) ;

            trGridView.setAdapter(trailerAdapter);
            trailerAdapter = new TrailerAdapter(getContext() , trailers);
            movieTrailers mt = new movieTrailers();
            mt.execute();
            reviewAdapter = new ReviewAdapter(getContext() , reviews);
            movieReviews mr = new movieReviews();
            mr.execute();

//trailers.get(position)
            t = (TextView) view.findViewById(R.id.t);
//            video = (Button) view.findViewById(R.id.trailer);
//            review = (TextView) view.findViewById(R.id.reviewss) ;
Log.e("MOVIEID" , id);

            favor = (ImageView) view.findViewById(R.id.favstar);
            if (favoritesDB.search(id)) {
                favor.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
            favor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favoritesDB.search(id)) {
                     //   favor.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        Toast.makeText(getContext(), "YOU ADD ITEM BEFORE", Toast.LENGTH_LONG).show();
                        Log.e("AA", "YOU ADD ITEM BEFORE");
                    } else {
                        favor.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(contract.MovieEntry.movie_ID , movieData.getID());
                        contentValues.put(contract.MovieEntry.movie_poster_path , movieData.getMoviePoster());
                        contentValues.put(contract.MovieEntry.movie_overview , movieData.getOverview());
                        contentValues.put(contract.MovieEntry.movie_rate , movieData.getRate());
                        contentValues.put(contract.MovieEntry.movie_date , movieData.getDate());

                        getContext().getContentResolver().insert(contract.MovieEntry.CONTENT_URI , contentValues);

//                        favoritesDB.insertRow(movieData.getID(), movieData.getTitle() ,movieData.getMoviePoster(),
//                                movieData.getOverview(),
//                                movieData.getDate(), movieData.getRate());
                        Log.e("MOVIEDATE" , movieData.getDate());

                        Log.e("DB", "item is inserted");
                    }
                }
            });

        }



        return view;
    }



    //*********************  Trailers ********************************************** //

    class movieTrailers extends AsyncTask<Void, Void, String> {

        String turl;

        public movieTrailers() {
Log.e("MOVIEID" , id);
      // http://api.themoviedb.org/3/movie/21612/videos?api_key=d643f9d3c6ef8fc8c7aa952ba183c80a
            this.turl = "http://api.themoviedb.org/3/movie/" +id + "/videos?api_key=d643f9d3c6ef8fc8c7aa952ba183c80a" ;
        }


        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            Uri uri = Uri.parse(turl).buildUpon().build();
            URL url = null;
            try {
                url = new URL(uri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();


                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                trailersKey = buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("MalformedURLException", e + "");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("IOException", e + "");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IOException", e + "");
                }
            }
            Log.e("Trailer DATA", String.valueOf(reader));
            return trailersKey;

        }

        @Override
        protected void onPostExecute(String JsonData) {
            super.onPostExecute(JsonData);
            if (JsonData != null) {
                try {
                    JSONObject jsonObject = new JSONObject(JsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String bg = jsonObject1.getString("key");
                        trailers.add(bg);
                            Log.e("TrailersKey",trailers.get(0));
                    }
                    movieData.setTrailerList(trailers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//            video.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent("android.intent.action.VIEW",
//                            Uri.parse(String.valueOf(Uri.parse("https://www.youtube.com/watch?v=" + trailers)))));
//                }
//            });
            trGridView.setAdapter(trailerAdapter);
        }


    }

    class  movieReviews extends  AsyncTask<Void,Void, String>{

        String rurl ;
        public movieReviews() {
            http://api.themoviedb.org/3/movie/297762/reviews?api_key=d643f9d3c6ef8fc8c7aa952ba183c80a
            rurl = "http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=d643f9d3c6ef8fc8c7aa952ba183c80a";
        }


        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            Uri uri = Uri.parse(rurl).buildUpon().build();
            URL url = null;
            try {
                url = new URL(uri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();


                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                reviewResponse = buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("MalformedURLException", e + "");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("IOException", e + "");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IOException", e + "");
                }
            }
            Log.e("Review DATA", String.valueOf(reader));
            return reviewResponse;

        }

        @Override
        protected void onPostExecute(String reviewResponse) {
            super.onPostExecute(reviewResponse);
            if (reviewResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(reviewResponse);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String nb        = jsonObject1.getString("content");
                        reviews.add(nb);
                        movieData.setReviewList(reviews);
                            Log.e("Reviews",reviews.get(0));

                    }
                    movieData.setReviewList(reviews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            reviewAdapter = new ReviewAdapter(getContext(),reviews);
            revGridView.setAdapter(reviewAdapter);
           // review.setText(reviews);
           // review.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("moviedata",movieData);
        outState.putStringArrayList("review", reviews);
        outState.putStringArrayList("trailer",trailers);
    }
}
