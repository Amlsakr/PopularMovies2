package com.example.aml.popularmovies.Data;

import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aml.popularmovies.R;

import java.util.List;

/**
 * Created by aml on 26/07/17.
 */

public class ReviewAdapter extends ArrayAdapter {

    private List<String> reviewList;
    private Context context;
    private LayoutInflater layoutInflater;
    TextView textView;

    public ReviewAdapter(Context context, List <String> list) {
        super(context, R.layout.review_grid_item ,list);
        this.context = context ;
        this.reviewList = list ;
    }

    public List<String> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<String> reviewList) {
        this.reviewList = reviewList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.review_grid_item, parent, false);


        textView = (TextView) convertView.findViewById(R.id.review_grid_text);
        textView.setMovementMethod(new ScrollingMovementMethod());

      ReviewData reviewData = new ReviewData();
       reviewData.setSingleReview(reviewList.get(position));



        textView.setText(reviewData.getSingleReview());


        return convertView;

    }


    @Override
    public int getCount() {
        return reviewList.size();
    }
}
