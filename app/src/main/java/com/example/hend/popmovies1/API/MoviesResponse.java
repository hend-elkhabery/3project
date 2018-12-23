package com.example.hend.popmovies1.API;

import com.example.hend.popmovies1.POJO.MovieModel;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class MoviesResponse {

    private int page;
    private int total_results;
    private int total_pages;

    @SerializedName("results")
    private ArrayList<MovieModel> movies;
    public ArrayList<MovieModel> getMovies() {
        return movies;
    }
    public void setMovies(ArrayList<MovieModel> movies) {
        this.movies = movies;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }


}