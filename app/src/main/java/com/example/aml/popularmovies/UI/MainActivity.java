package com.example.aml.popularmovies.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aml.popularmovies.R;

public class MainActivity extends AppCompatActivity {
MainFragment m ;
    private  boolean mTwoPane ;
    public static final String mypreference = "mypref";
public    SharedPreferences     sharedpreferences ;
    public     SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE) ;
   editor = sharedpreferences.edit();

        if (findViewById(R.id.multi_pan_layout) != null) {
            mTwoPane = true ;
        }else  {
            mTwoPane = false ;
        }
        m = new MainFragment("popular");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, m)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.sort) {
            m = new MainFragment("top_rated");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main, m)
                    .commit();

            editor.putString("top_rated","top_rated");

        }else if (id == R.id.refresh){
            m = new MainFragment("popular");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main,m)
                    .commit();

            editor.putString("refersh","refersh");

        }else if (id == R.id.favo) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main,new FavoritesFragment())
                    .commit();

            editor.putString("favo","favo");

        }
        return true;
    }


}
