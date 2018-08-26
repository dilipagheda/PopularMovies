package com.example.android.popularmovies1.view;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.popularmovies1.adapter.ImageAdapter;
import com.example.android.popularmovies1.R;
import com.example.android.popularmovies1.model.Movies;
import com.example.android.popularmovies1.model.MoviesLoader;
import com.example.android.popularmovies1.model.Result;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<Movies>
{

    private static final String TAG = "MainActivity";

    GridView gridview;
    ImageAdapter mAdapter;
    TextView mTextView;
    String sort_order;
    int page = 1;
    int total_pages=0;
    boolean flag_loading = false;
    Context mContext;
    ProgressBar mProgressBar;
    ArrayList<Result> results;
    LoaderManager lm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridview = (GridView) findViewById(R.id.gridview);
        mTextView = (TextView) findViewById(R.id.error);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mContext = this;
        results = new ArrayList<>();
        page=1;
        mAdapter = new ImageAdapter(this,results,page);
        mAdapter.setDataSet(results,page);
        gridview.setAdapter(mAdapter);

        //check network and display error accordingly
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            showError();
            return;
        }

        //get the preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        sort_order = sharedPreferences.getString(getString(R.string.sort_order_key),getString(R.string.most_popular_value));

        //initialize loader
        lm = getLoaderManager();
        mProgressBar.setVisibility(View.VISIBLE);
        lm.initLoader(0,null,this);

        //set up item listener
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG,"Item clicked:"+i+" Page:"+page);
                Result result = results.get(i);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("image", result.getAbsolutePosterPath());
                intent.putExtra("title",result.getTitle());
                intent.putExtra("releaseDate",result.getReleaseDate());
                intent.putExtra("vote",result.getVoteAverage());
                intent.putExtra("plot",result.getOverview());
                startActivity(intent);
            }
        });

        //set up scroll listener for pagination
        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItemVisible, int visibleItemCount, int totalItemCount) {
                Log.d(TAG,"First Item Visible:"+firstItemVisible+" Visible Item Count:"+visibleItemCount
                        +"Total Item Count:"+totalItemCount);

                if((firstItemVisible+visibleItemCount) == totalItemCount && totalItemCount!=0){
                    if(flag_loading == false)
                    {
                        flag_loading = true;
                        addItems();
                    }
                }
            }

            private void addItems(){
                //load more
                page++;
                //do the check when page is not valid and no more data to fetch.
                if(page > total_pages){
                    Toast.makeText(mContext, getString(R.string.no_more_data_to_load),Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);
                getLoaderManager().restartLoader(0, null, MainActivity.this);

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);

        //Save and restore state is not implemented in this version yet. so just destroying the loader when user changes orientation to avoid data corruption.
        if(lm!=null){
            lm.destroyLoader(0);
        }

    }

    private void showError(){
        mTextView.setVisibility(View.VISIBLE);
        gridview.setVisibility(View.GONE);
    }

    private void showImagesInGrid(){
        mTextView.setVisibility(View.GONE);
        gridview.setVisibility(View.VISIBLE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Intent i = new Intent(this,SettingsActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals(getString(R.string.sort_order_key))){
            //get new sort order from shared preferences
            sort_order = sharedPreferences.getString(getString(R.string.sort_order_key),getString(R.string.most_popular_value));
            //reset page here
            page = 1;
            results.clear();
            //start progress bar
            mProgressBar.setVisibility(View.VISIBLE);
            //restart the loader
            getLoaderManager().restartLoader(0, null, this);

        }
    }

    @Override
    public Loader<Movies> onCreateLoader(int i, Bundle bundle) {

        return new MoviesLoader(this,sort_order,page);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Movies> loader) {
        Log.d(TAG, "onLoaderReset");
    }

    @Override
    public void onLoadFinished(android.content.Loader<Movies> loader, Movies movies) {
        mProgressBar.setVisibility(View.GONE);

        if(movies!=null) {

                    results.addAll(movies.getResults());
                    //images = movies.getPosterImages();
                    total_pages = movies.getTotalPages();
                    if (results != null) {
                        showImagesInGrid();

                        mAdapter.setDataSet(results,page);
                        mAdapter.notifyDataSetChanged();
                        flag_loading = false;
                    }

        }else{
            showError();
        }
    }
}
