package com.example.aml.popularmovies.UI;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.aml.popularmovies.Data.MovieAdapter;
import com.example.aml.popularmovies.Data.MovieData;
import com.example.aml.popularmovies.Provider.FavoritesDB;
import com.example.aml.popularmovies.Provider.contract;
import com.example.aml.popularmovies.R;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment {

    GridView gridView;
    ImageView imageView;
    MovieAdapter movieAdapter;
    List<MovieData> movies = new ArrayList<>();
    List<String> pat = new ArrayList<>();
    FavoritesDB favoritesDB ;


    private static final String[] MOVIE_COLUMNS = {


            contract.MovieEntry.movie_ID,
            contract.MovieEntry.movie_title,
           contract.MovieEntry.movie_poster_path,
            contract.MovieEntry.movie_overview ,
            contract.MovieEntry.movie_date ,
            contract.MovieEntry.movie_rate};

    public FavoritesFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false) ;
        gridView = (GridView) view.findViewById(R.id.favo_grid_view);
        imageView = (ImageView) view.findViewById(R.id.image_view);
        favoritesDB = new FavoritesDB(this.getContext());
        movieAdapter = new MovieAdapter(this.getContext(),movies);


            // movies = favoritesDB.display();
            Cursor cursor = getContext().getContentResolver().query(contract.MovieEntry.CONTENT_URI, MOVIE_COLUMNS, null, null
                    , null);
            if (cursor != null & cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    String movieID = cursor.getString(0);
                    String movieTitle = cursor.getString(1);
                    String moviePosterPath = cursor.getString(2);
                    String movieOverview = cursor.getString(3);
                    String movieDate = cursor.getString(4);
                    String movieRate = cursor.getString(5);

                    MovieData md = new MovieData(moviePosterPath, movieTitle, movieOverview, movieDate, movieRate, movieID);
                    movies.add(md);
                }
                cursor.close();
            }


            gridView.setAdapter(movieAdapter);



       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               MovieData movieData = movies.get(position);
               DetailsFragment f = new DetailsFragment();
               Bundle bundle = new Bundle();
               bundle.putSerializable("object", movieData);
               f.setArguments(bundle);
               FragmentTransaction ft = getFragmentManager().beginTransaction();
               ft.replace(android.R.id.content, f);
               //ft.add(f,"f");
               ft.addToBackStack(null);
               ft.commit();
           }
       });
        return view ;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("favorites", (ArrayList<? extends Parcelable>) movies);

    }
}
