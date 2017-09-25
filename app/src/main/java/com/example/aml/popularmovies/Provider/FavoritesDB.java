package com.example.aml.popularmovies.Provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aml on 08/07/17.
 */

public class FavoritesDB extends SQLiteOpenHelper {
    public static final String name = "Movie.db";
    public static final int version = 1 ;
    public Context context ;
    public FavoritesDB(Context context) {
        super(context, name, null, version);
        this.context = context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS favoritieMovie " +
                " (ID INTEGER PRIMARY KEY " +
                ", MovieID TEXT" +
                ", MovieTitle TEXT" +
                " , posterpath TEXT ," +
                " overview TEXT ," +
                " date TEXT , " +
                "rate TEXT ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//    public void insertRow (String MovieID , String movieTitle , String poster_path ,String  overview , String date , String rate ) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("MovieID", MovieID);
//        contentValues.put("MovieTitle",movieTitle);
//        contentValues.put("posterpath",poster_path);
//        contentValues.put("overview",overview);
//        contentValues.put("date",date);
//        contentValues.put("rate",rate);
//
//
//
//        long i =   db.insert("favoritieMovie" , null , contentValues);
//        if (i > -1)
//            Log.e("datasaved","DATA IS SAVED") ;
//        else
//            Log.e("datasaved" , "DATA IS NOT SAVED");
//
//
//    }

//    public List display (){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ArrayList <MovieData> arrayList = new ArrayList<>();
//        Cursor cursor = db.query("favoritieMovie" , new String [] {"MovieID" , "MovieTitle"
//                , "posterpath" , "overview" ,"date" ,"rate" }  ,null ,null , null , null , null);
//        cursor.moveToNext();
//        do {
//            MovieData movieData = new MovieData();
//            movieData.setID(cursor.getString(cursor.getColumnIndex("MovieID")));
//            movieData.setTitle(cursor.getString(cursor.getColumnIndex("MovieTitle")));
//            movieData.setMoviePoster(cursor.getString(cursor.getColumnIndex("posterpath")));
//            movieData.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
//            movieData.setDate(cursor.getString(cursor.getColumnIndex("date")));
//            movieData.setDate(cursor.getString(cursor.getColumnIndex("rate")));
//
//
//            arrayList.add(movieData);
//
//        }while (cursor.moveToNext());
//
//        cursor.close();
//        if (arrayList.size() > 0) {
//            Log.e("READ", "item is read");
//        } else  {
//            Log.e("READ", "item is not read");
//        }
//        return arrayList;
//    }
//
    public boolean search(String id) {

        Cursor cursor = getReadableDatabase().rawQuery("select * from favoritieMovie where MovieID =" + id + "", null);
        if (cursor == null || !cursor.moveToNext()) {
            return false;
        }
        return true;
    }
}
