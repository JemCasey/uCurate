package com.example.jbrow.ucurate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewArtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_art);

        Intent intent = getIntent();
        //String artId = intent.getStringExtra();
        Artwork art = null; // async task to get the art info

        ImageView artImage = (ImageView) findViewById(R.id.view_art_image);
        artImage.setImageBitmap(art.getImage());

        TextView artTitle = (TextView) findViewById(R.id.view_art_title);
        artTitle.setText(art.getTitle());

        TextView artDescription = (TextView) findViewById(R.id.view_art_description);
        artTitle.setText(art.getDescription());
    }



}
