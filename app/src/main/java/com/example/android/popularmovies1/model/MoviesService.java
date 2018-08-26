package com.example.android.popularmovies1.model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("/3/movie/popular")
    Call<Movies> getMoviesByPopularity(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("/3/movie/top_rated")
    Call<Movies> getMoviesByTopRating(@Query("api_key") String apiKey, @Query("page") int page);

}
