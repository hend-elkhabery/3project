package com.example.hend.popmovies1.API;

import com.example.hend.popmovies1.POJO.Trailers;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TrailersResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("results")
    @Expose
    private ArrayList<Trailers> results = new ArrayList<Trailers>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Trailers> getVideoResults() {
        return results;
    }

    public void setResults(ArrayList<Trailers> results) {
        this.results = results;
    }
}
