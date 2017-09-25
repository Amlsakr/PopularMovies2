package com.example.aml.popularmovies.UI;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.aml.popularmovies.Data.MovieAdapter;
import com.example.aml.popularmovies.Data.MovieData;
import com.example.aml.popularmovies.R;

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
import java.util.List;


public class MainFragment extends Fragment {


    GridView gridView;
    ImageView imageView;
    MovieAdapter movieAdapter;
    List<MovieData> movies = new ArrayList<>();
    String baseUrl;
    List<String> pat = new ArrayList<>();

    int index ;

    public MainFragment(String param) {
        // Required empty public constructor
        baseUrl = ("http://api.themoviedb.org/3/movie/" +
                param +
                "?api_key=d643f9d3c6ef8fc8c7aa952ba183c80a");

    }


    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        for (int i = 0; i < pat.size(); i++) {
            Log.e("PATHS", pat.get(i));
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        imageView = (ImageView) view.findViewById(R.id.image_view);
        gridView = (GridView) view.findViewById(R.id.grid_view);
        movieAdapter = new MovieAdapter(getActivity(), movies);
        index = gridView.getFirstVisiblePosition();   //for save the grid view position
        if (savedInstanceState != null) {
            movies = savedInstanceState.getParcelableArrayList("mo");
            int in = savedInstanceState.getInt("pos");
            movieAdapter.setMovielist(movies);
            gridView.smoothScrollToPosition(in);

//            gridView.post(new Runnable() {
//                @Override
//                public void run() {
//                    gridView.setSelection(index);
//                }
//            });
        } else {
            FMovieAsyncTask ms = new
                    FMovieAsyncTask(baseUrl);
            ms.execute();


        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MovieData movieData = movies.get(i);
                DetailsFragment f = new DetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", movieData);
                f.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(android.R.id.content, f);
                //ft.add(f,"f");
                ft.addToBackStack(null);
                ft.commit();
                //Intent intent = new Intent(getActivity(), Details.class);
                //  MovieData movieData ;
                //  movieData = movies.get(i);
                //  Bundle bundle = new Bundle();
                // bundle.putSerializable("object",  movieData);
                // intent.putExtras(bundle);
                // startActivity(intent);
            }
        });


        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mo", (ArrayList<? extends Parcelable>) movies);
        int a = gridView.getFirstVisiblePosition();
        outState.putInt("pos",a);
    }

    class FMovieAsyncTask extends AsyncTask<Void, Void, List<MovieData>> {

        String JsonData;
        String baseUrl;

        public FMovieAsyncTask(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<MovieData> doInBackground(Void... voids) {
            //  baseUrl = "http://api.themoviedb.org/3/movie/popular?api_key=d643f9d3c6ef8fc8c7aa952ba183c80a";
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            Uri uri = Uri.parse(baseUrl).buildUpon().build();
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
                JsonData = buffer.toString();

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
            Log.e("JSON DATA", String.valueOf(reader));
            return null;
        }


        @Override
        protected void onPostExecute(List<MovieData> movieDatas) {
            super.onPostExecute(movieDatas);

            if (baseUrl.contains("top_rated")) {
                movies.clear();
            }
            if (JsonData != null) {
                try {
                    JSONObject jsonObject = new JSONObject(JsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        pat.add(jsonObject1.getString("poster_path"));
                        movies.add(new MovieData(
                                jsonObject1.getString("poster_path"),
                                jsonObject1.getString("title"),
                                jsonObject1.getString("overview"),
                                jsonObject1.getString("vote_average"),
                                jsonObject1.getString("release_date"),
                                jsonObject1.getString("id")));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // MovieAdapter   mo = new MovieAdapter(getActivity(), movies);
            gridView.setAdapter(movieAdapter);

        }
    }

}