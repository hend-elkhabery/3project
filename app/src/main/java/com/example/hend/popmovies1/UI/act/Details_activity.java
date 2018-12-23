package com.example.hend.popmovies1.UI.act;

 import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

 import android.widget.TextView;


 import com.example.hend.popmovies1.API.TMDBinterface;
import com.example.hend.popmovies1.POJO.Review;
import com.example.hend.popmovies1.POJO.Trailers;
import com.example.hend.popmovies1.API.TrailersResponse;
 import com.example.hend.popmovies1.R;
 import com.example.hend.popmovies1.UI.Adapters.ReviewAdapter;
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


    Retrofit retrofit;
    TMDBinterface moviesAPI;

    trailersAdapter trailersAdapter ;
    ReviewAdapter reviewAdapter ;

    private RecyclerView rvtrailers;
    private RecyclerView rvreviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_activity);





        date =  findViewById(R.id.tvdate);
        description =  findViewById(R.id.tvoverview);
        rating =  findViewById(R.id.tvrating);
        title =  findViewById(R.id.tvtitle);
        ivposter =   findViewById(R.id.ivposter);

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
    }


    private void initViews() {

        rvtrailers = (RecyclerView) findViewById(R.id.rvtrailers);
        ArrayList <Trailers> mTrailers = new ArrayList<>();
        trailersAdapter = new trailersAdapter(mTrailers ,this);

        rvreviews= (RecyclerView) findViewById(R.id.rvreviews);
        ArrayList <Review.Results>  mReview = new ArrayList<>();
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
                //rvtrailers.hasFixedSize();

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
                List<Review.Results> reviewResponses = response.body().getResults();
                ReviewAdapter reviewAdapter = new ReviewAdapter(getApplicationContext() , reviewResponses);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext() , LinearLayoutManager.VERTICAL , false);
                rvreviews.setLayoutManager(mLayoutManager);
                rvreviews.setAdapter(reviewAdapter);
               // rvreviews.hasFixedSize();

            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });


    }
}
