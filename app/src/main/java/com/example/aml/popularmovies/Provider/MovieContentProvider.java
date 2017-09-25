package com.example.aml.popularmovies.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by aml on 25/07/17.
 */

public class MovieContentProvider extends ContentProvider {
FavoritesDB favoritesDB ;



    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;
    // Declare a static variable for the Uri matcher that you construct
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final String TAG = MovieContentProvider.class.getName();

    // Define a static buildUriMatcher method that associates URI's with their int match
    public static UriMatcher buildUriMatcher() {
        // Initialize a UriMatcher
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // Add URI matches
        uriMatcher.addURI(contract.AUTHORITY, contract.PATH_MOVIE, MOVIES);
        uriMatcher.addURI(contract.AUTHORITY, contract.PATH_MOVIE + "/#", MOVIE_WITH_ID);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();

        favoritesDB = new FavoritesDB(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor reCursor ;
        switch (sUriMatcher.match(uri)){
            case MOVIES :
                reCursor = favoritesDB.getReadableDatabase().query(contract.MovieEntry.TABLE_NAME , projection,selection
                ,selectionArgs , null , null , sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknowen Uri :" +uri);

        }
        getContext().getContentResolver().notifyChange(uri , null);
        return reCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = favoritesDB.getReadableDatabase();

        // Write URI matching code to identify the match for the plants directory
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned
        switch (match) {
            case MOVIES:
                // Insert new values into the database
                long id = db.insert(contract.MovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(contract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
