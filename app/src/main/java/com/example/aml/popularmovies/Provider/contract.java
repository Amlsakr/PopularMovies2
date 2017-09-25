package com.example.aml.popularmovies.Provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by aml on 27/07/17.
 */

public class contract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.aml.popularmovies";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "plants" directory
    public static final String PATH_MOVIE = "movies";

    public static final long INVALID_MOVIE_ID = -1;

    public static final class MovieEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME = "favoritieMovie";
        public static final String movie_ID = "MovieID";
        public static final String movie_title = "MovieTitle";
        public static final String movie_poster_path = "posterpath";
        public static final String movie_overview = "overview";
        public static final String movie_date = "date";
        public static final String movie_rate = "rate";
    }
}
