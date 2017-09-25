package com.example.aml.popularmovies.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by aml on 13/04/17.
 */

public class MovieData extends ArrayList<Parcelable> implements Serializable, Parcelable {
    private  String title ;

    private String moviePoster_Path ;

    private String overview ;

    private String rate ;

    private String  date ;

    private String ID ;

    private ArrayList<String> trailerList ;

    private  ArrayList <String> reviewList ;


    public MovieData (){

    }

    public MovieData(ArrayList<String> trailerList, ArrayList<String> reviewList) {
        this.trailerList = trailerList;
        this.reviewList = reviewList;
    }

    protected MovieData(Parcel in) {
        title = in.readString();
        moviePoster_Path = in.readString();
        overview = in.readString();
        rate = in.readString();
        date = in.readString();
        ID = in.readString();
        trailerList = in.createStringArrayList();
        reviewList = in.createStringArrayList();
    }

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    public ArrayList<String> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(ArrayList<String> trailerList) {
        this.trailerList = trailerList;
    }

    public ArrayList<String> getReviewList() {
        return reviewList;
    }

    public void setReviewList(ArrayList<String> reviewList) {
        this.reviewList = reviewList;
    }

    public MovieData(String poster_path, String title, String overview, String vote_average, String release_date , String id)  {
        this.moviePoster_Path = poster_path;

        this.title = title;

        this.overview = overview;

        this.rate = vote_average;

        this.date = release_date;
        this.ID = id;
    }


    public String getMoviePoster() {

        return moviePoster_Path;

    }


    public void setMoviePoster(String moviePoster) {

        this.moviePoster_Path = moviePoster;
    }


    public String getTitle() {


        return title;
    }


    public void setTitle(String title) {


        this.title = title;
    }


    public String getOverview() {

        return overview;
    }


    public void setOverview(String overview) {



        this.overview = overview;
    }


    public String getRate() {

        return rate;
    }


    public void setRate(String rate) {

        this.rate = rate;
    }


    public String getDate() {

        return date;

    }


    public void setDate(String date) {

        this.date = date;

    }

    public  String getID (){
        return ID;
    }
    public  void setID (String ID){
        this.ID = ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(moviePoster_Path);
        dest.writeString(overview);
        dest.writeString(rate);
        dest.writeString(date);
        dest.writeString(ID);
        dest.writeStringList(trailerList);
        dest.writeStringList(reviewList);
    }


}
