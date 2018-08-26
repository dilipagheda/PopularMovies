package com.example.android.popularmovies1.view;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies1.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        String title = intent.getStringExtra("title");
        String releaseDate = intent.getStringExtra("releaseDate");
        double vote = intent.getDoubleExtra("vote",0);


        String plot = intent.getStringExtra("plot");

        TextView titleView =  (TextView) findViewById(R.id.title);
        TextView releaseDateView = (TextView) findViewById(R.id.release_date);
        TextView voteView = (TextView) findViewById(R.id.vote);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        TextView plotView = (TextView) findViewById(R.id.plot);

        titleView.setText(title);
        releaseDateView.setText(releaseDate);
        voteView.setText(String.valueOf(vote));
        plotView.setText(plot);

        Picasso.with(this)
                .load(image)
                .into(imageView);

    }
}
