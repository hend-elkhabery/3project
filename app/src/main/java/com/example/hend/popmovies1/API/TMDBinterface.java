package com.example.hend.popmovies1.API;



 import com.example.hend.popmovies1.POJO.Review;

 import retrofit2.Call;
import retrofit2.http.GET;
 import retrofit2.http.Path;
 import retrofit2.http.Query;


public interface TMDBinterface {

    public static String myApiKey = "3bf93f4a120049dc68df9e3c1aa2772a";

    @GET("/3/movie/popular?api_key=" + myApiKey)
    Call<MoviesResponse> getPopularMovies();

    @GET("/3/movie/top_rated?api_key=" + myApiKey)
    Call<MoviesResponse> getTopratedMovies();


    @GET("/3/movie/{movie_id}/videos?api_key=" + myApiKey)
    Call<TrailersResponse> getMoviesVideos(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("/3/movie/{movie_id}/reviews")
    Call<Review> getReview(@Path("movie_id") int id, @Query("api_key") String apiKey);



}