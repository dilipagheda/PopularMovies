package com.example.android.popularmovies1.model;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

public class MoviesLoader extends AsyncTaskLoader<Movies>{
    private static final String API_KEY = "<put your key>";

    public static final String LOG_TAG = "MoviesLoader";
    private Response<Movies> movies;
    private Movies data;
    private String sort_order;
    private  int page;

    public MoviesLoader(Context context,  String sort_order, int page) {
        super(context);
        this.sort_order = sort_order;
        this.page = page;
    }


    @Override
    public Movies loadInBackground() {
        Log.d(LOG_TAG,"Load in background..");


        getMovies();

        if(movies!=null){
            data = movies.body();
            return data;

        }
        else
         return null;
    }

    @Override
    protected void onStartLoading() {
        Log.d(LOG_TAG,"OnStartLoading..");

        if(movies!=null){
            deliverResult(movies.body());
            return;
        }
        forceLoad();
    }

    @Override
    public void deliverResult(Movies data) {
        this.data = data;
        super.deliverResult(data);
    }

    private void getMovies() {

        MoviesService moviesService = RetrofitInstance.getService();
        Call<Movies> callBackend;
        if(sort_order.equals("most_popular")){
            callBackend = moviesService.getMoviesByPopularity(API_KEY,page);
        }else{
            callBackend = moviesService.getMoviesByTopRating(API_KEY,page);
        }

        Log.d(LOG_TAG,callBackend.request().url().toString());

        try{
            movies = callBackend.execute();


        }catch(IOException e){
            Log.d(LOG_TAG,"IOException.."+e);
        }


    }
}
