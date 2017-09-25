package com.example.aml.popularmovies.Data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.aml.popularmovies.R;

import java.util.List;

/**
 * Created by aml on 26/07/17.
 */

public class TrailerAdapter extends ArrayAdapter {

    private List<String> trailerList;
    private Context context;
public String word ;
    public List<String> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<String> trailerList) {
        this.trailerList = trailerList;
    }

    private LayoutInflater layoutInflater;
        Button button ;
    public TrailerAdapter(Context context, List <String> list) {
        super(context, R.layout.trailer_grid_item ,list);
        this.context = context ;
        this.trailerList = list ;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.trailer_grid_item, parent, false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.button = (Button) convertView.findViewById(R.id.trailer_grid_button);
         word = trailerList.get(position) ;
     //  button = (Button) convertView.findViewById(R.id.trailer_grid_button);
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                               context.startActivity((new Intent("android.intent.action.VIEW",
                       Uri.parse(String.valueOf(Uri.parse("https://www.youtube.com/watch?v=" +
                               word))))));
            }
        });



//       View.OnClickListener l = new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {

//
//           }
//       };


        return convertView;

    }
// trailerList.get(position)

    @Override
    public int getCount() {
        return trailerList.size();
    }
}

class  ViewHolder {
    Button button ;
}
