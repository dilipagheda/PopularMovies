package com.example.android.popularmovies1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.popularmovies1.R;
import com.example.android.popularmovies1.model.Result;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Result> myDataSet;
    private int page;
    private static final int PAGE_SIZE = 20;
    private static final String TAG = "ImageAdapter";

    public ImageAdapter(Context c, ArrayList<Result> myDataSet, int page) {
        mContext = c;
        this.myDataSet = myDataSet;
        this.page = page;
    }

    public void setDataSet(ArrayList<Result> myDataSet, int page) {

        this.myDataSet = myDataSet;
        this.page = page;
    }

    public int getCount() {
        return myDataSet.size();
    }

    public String getItem(int position) {

        Result r = myDataSet.get(position+(page-1)*PAGE_SIZE);
        return r.getAbsolutePosterPath();
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        ImageView imageView=null;
        if (view == null) {
            // if it's not recycled, initialize some attributes
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_item,parent,false);
        }
        imageView = view.findViewById(R.id.movieImage);

        Log.d(TAG,"Position:"+position+"  Title:"+myDataSet.get(position).getTitle());
        Picasso.with(parent.getContext())
                .load(myDataSet.get(position).getAbsolutePosterPath())
                .into(imageView);

        return view;
    }

}
