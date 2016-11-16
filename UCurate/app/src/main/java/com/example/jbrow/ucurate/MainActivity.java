package com.example.jbrow.ucurate;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cameraButton = (Button) findViewById(R.id.camera_button);

        cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent openCamera = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(openCamera);
            }
        });
        Button mapButton = (Button) findViewById(R.id.map_button);

        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LatLng locone = new LatLng(38.990633, -76.949384);

                LatLng loctwo = new LatLng(38.984929, -76.947760);

                ArrayList<Artwork> artworks = new ArrayList<>();
                artworks.add(new Artwork("Rhein II", "this is by Andreas Gursky", locone));
                artworks.add(new Artwork("Betty", "this is by Gerhard Richter", loctwo));
                Intent openTour = new Intent(MainActivity.this,ViewTourActivity.class);
                openTour.putParcelableArrayListExtra("artworks", artworks);
                startActivity(openTour);
            }
        });
    }
}
