package com.example.android.popularmovies1.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Movies {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    @Expose(serialize = false,deserialize = false)
    private static final String BASE_URL_FOR_IMAGE="http://image.tmdb.org/t/p/w185";

    @Expose(serialize = false,deserialize = false)
    private static final String TAG="Movies";

    @Expose(serialize = false,deserialize = false)
    private ArrayList<String> images = null;

    public Movies() {
        images = new ArrayList<>();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Result> getResults() {
        return results;
    }

//    public ArrayList<String> getPosterImages(){
//        for(Result r:results){
//            Log.d(TAG,r.getTitle()+" "+r.getPosterPath());
//            images.add(BASE_URL_FOR_IMAGE+r.getPosterPath());
//        }
//        return images;
//
//    }
    public void setResults(List<Result> results) {
        this.results = results;
    }

}

