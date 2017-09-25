package com.example.aml.popularmovies.Data;

/**
 * Created by aml on 27/07/17.
 */

public class ReviewData {
    String singleReview ;

    public ReviewData(String singleReview) {
        this.singleReview = singleReview;
    }

    public ReviewData() {

    }

    public String getSingleReview() {
        return singleReview;
    }

    public void setSingleReview(String singleReview) {
        this.singleReview = singleReview;
    }
}
