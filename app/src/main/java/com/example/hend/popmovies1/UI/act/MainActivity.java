package com.example.hend.popmovies1.UI.act;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hend.popmovies1.POJO.MovieModel;
import com.example.hend.popmovies1.API.MoviesResponse;
import com.example.hend.popmovies1.API.TMDBinterface;
import com.example.hend.popmovies1.R;
import com.example.hend.popmovies1.UI.Adapters.MoviesAdapter;

import java.util.ArrayList;
 import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static Retrofit retrofit;
    public static String Base_URL = "http://api.themoviedb.org";

    TMDBinterface moviesAPI;


    private static final String TAG = MainActivity.class.getSimpleName();

     ArrayList<MovieModel> Lmostpopular = new ArrayList<>();
    ArrayList<MovieModel> Ltoprated = new ArrayList<>();

    int Select;
    RecyclerView mRecyclerView;
    GridLayoutManager mGridLayoutManager;
    MoviesAdapter moviesAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = findViewById(R.id.recyclerview);
        mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        loadDataList();



    }
    private void loadDataList() {

        retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        moviesAPI = retrofit.create(TMDBinterface.class);

        Call<MoviesResponse> Callpopularmovies = moviesAPI.getPopularMovies();

        Callpopularmovies.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Lmostpopular =  response.body().getMovies();
                Select = 2;
                moviesAdapter  = new MoviesAdapter(Lmostpopular, MainActivity.this);
                mRecyclerView.setAdapter(moviesAdapter);

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });

        Call<MoviesResponse> calltopratedmovies =  moviesAPI.getTopratedMovies();
        calltopratedmovies.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Ltoprated =  response.body().getMovies();
                Select = 1;
                moviesAdapter  = new MoviesAdapter(Ltoprated, MainActivity.this);
                mRecyclerView.setAdapter(moviesAdapter);

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

                Log.e(TAG ,"faild");
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.toprated) {
            Select = 1;
            mRecyclerView.setAdapter(new MoviesAdapter(Ltoprated, MainActivity.this));

        }
        if (id == R.id.mostpopular) {
            Select = 2;
            mRecyclerView.setAdapter(new MoviesAdapter(Lmostpopular, MainActivity.this));
        }
        return super.onOptionsItemSelected(item);
    }





}
