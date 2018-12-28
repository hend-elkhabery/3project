package com.example.hend.popmovies1.UI.act;

 import android.content.SharedPreferences;
 import android.graphics.Movie;
 import android.preference.PreferenceManager;
 import android.support.design.widget.Snackbar;
 import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
 import android.view.View;
 import android.widget.Button;
 import android.widget.ImageView;

 import android.widget.TextView;


 import com.example.hend.popmovies1.API.ReviewResponse;
 import com.example.hend.popmovies1.API.TMDBinterface;
 import com.example.hend.popmovies1.Data.FavoriteDbHelper;
 import com.example.hend.popmovies1.POJO.MovieModel;
 import com.example.hend.popmovies1.POJO.Review;
import com.example.hend.popmovies1.POJO.Trailers;
import com.example.hend.popmovies1.API.TrailersResponse;
 import com.example.hend.popmovies1.R;
 import com.example.hend.popmovies1.UI.Adapters.ReviewAdapter;
 import com.example.hend.popmovies1.UI.Adapters.SampleRecycler;
 import com.example.hend.popmovies1.UI.Adapters.trailersAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class Details_activity extends AppCompatActivity {

    public static String Base_URL = "http://api.themoviedb.org";

    ImageView ivposter;
    TextView date  , rating ,description , title;
    Button btnfav;


    Retrofit retrofit;
    TMDBinterface moviesAPI;

    int movie_id;

    trailersAdapter trailersAdapter ;
    ReviewAdapter reviewAdapter ;

    private RecyclerView rvtrailers;
    private RecyclerView rvreviews;

    MovieModel movie;

      FavoriteDbHelper favoriteDbHelper;
      boolean fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_activity);

/*
        rvreviews= (RecyclerView) findViewById(R.id.rvreviews);
       // List <ReviewResponse>  mReview = new ArrayList<>();
        SampleRecycler reviewAdapter = new SampleRecycler();
        rvreviews.setAdapter(reviewAdapter);
        reviewAdapter.notifyDataSetChanged();

*/
        btnfav  = findViewById(R.id.btnfav);

        date =  findViewById(R.id.tvdate);
        description =  findViewById(R.id.tvoverview);
        rating =  findViewById(R.id.tvrating);
        title =  findViewById(R.id.tvtitle);
        ivposter =   findViewById(R.id.ivposter);

         movie_id = getIntent().getExtras().getInt("id");

        date.setText(getIntent().getExtras().getString("release_date"));
        description.setText(getIntent().getExtras().getString("overview"));
        rating.setText(String.valueOf(getIntent().getExtras().getDouble("vote_average"))+"/10");
        title.setText(getIntent().getExtras().getString("title"));
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185/" + getIntent().getExtras().getString("poster_path"))
                .placeholder(R.color.colorAccent)
                .error(R.drawable.ic_launcher_background)
                .into(ivposter);

        initViews();
////////////////////////////////////  add favorite
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        btnfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     if (fav){
                        SharedPreferences.Editor editor = getSharedPreferences("Details activity", MODE_PRIVATE).edit();
                        editor.putBoolean("Favorite Added", true);
                        editor.commit();
                        saveFavorite();
                        Snackbar.make(btnfav, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
                    }else{
                        int movie_id = getIntent().getExtras().getInt("id");
                        favoriteDbHelper = new FavoriteDbHelper(Details_activity.this);
                        favoriteDbHelper.deleteFavorite(movie_id);

                        SharedPreferences.Editor editor = getSharedPreferences("Detail activity", MODE_PRIVATE).edit();
                        editor.putBoolean("Favorite Removed", true);
                        editor.commit();
                        Snackbar.make(btnfav, "Removed from Favorite", Snackbar.LENGTH_SHORT).show();
                    }

            }
        });

    }
    public void saveFavorite(){
        FavoriteDbHelper favoriteDbHelper = new FavoriteDbHelper(Details_activity.this);
         MovieModel favorite = new MovieModel();

        Double rate = movie.getVote_average();
        favorite.setId(movie_id);
        favorite.setOriginal_title(getIntent().getExtras().getString("title"));
        favorite.setPoster_path(movie.getPoster_path());
        favorite.setVote_average(rate);
        favorite.setOverview(getIntent().getExtras().getString("overview"));

        favoriteDbHelper.addFavorite(favorite);
    }

    private void initViews() {

        rvtrailers = (RecyclerView) findViewById(R.id.rvtrailers);
        List <Trailers> mTrailers = new ArrayList<>();
        trailersAdapter = new trailersAdapter(mTrailers ,this);

/*
        RecyclerView myRecycler = (RecyclerView) findViewById(R.id.rvnothing);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));
        myRecycler.setAdapter(new SampleRecycler());

*/

        rvreviews= (RecyclerView) findViewById(R.id.rvreviews);
        ArrayList <ReviewResponse>  mReview = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this , mReview);


       loadReviews();
       loadTrailers();


    }
     public void loadTrailers() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        moviesAPI = retrofit.create(TMDBinterface.class);

        int  movie_id = getIntent().getExtras().getInt("movie_id");

        Call<TrailersResponse> trailersCall = moviesAPI.getMoviesVideos(movie_id , TMDBinterface.myApiKey);
        trailersCall.enqueue(new Callback<TrailersResponse>() {
            @Override
            public void onResponse(Call<TrailersResponse> call, Response<TrailersResponse> response) {
                List<Trailers> trailers = response.body().getVideoResults();
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false);
                rvtrailers.setLayoutManager(mLayoutManager);
                rvtrailers.setAdapter(new trailersAdapter(trailers , getApplicationContext()));
                rvtrailers.hasFixedSize();

            }

            @Override
            public void onFailure(Call<TrailersResponse> call, Throwable t) {

            }
        });


    }
     public void loadReviews(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        moviesAPI = retrofit.create(TMDBinterface.class);

        int  movie_id = getIntent().getExtras().getInt("movie_id");

        Call<Review> reviewCall = moviesAPI.getReview(movie_id , TMDBinterface.myApiKey);
        reviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                ArrayList<ReviewResponse> reviewResponses = new ArrayList<>();
                reviewResponses.addAll(response.body().getResults());
                LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rvreviews.setLayoutManager(firstManager);
                ReviewAdapter reviewAdapter = new ReviewAdapter(getApplicationContext(), reviewResponses);
                rvreviews.setAdapter(reviewAdapter);
                reviewAdapter.notifyDataSetChanged();
                rvreviews.setHasFixedSize(true);
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });




    }

}
