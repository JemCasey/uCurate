package com.example.jbrow.ucurate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class EditArtActivity extends AppCompatActivity {

    // Reference to the LocationManager
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_art);

        Intent intent = getIntent();
        // TODO: what should the default be?
        int imageId = intent.getIntExtra(Artwork.ARTWORK_ID, 0);

        // TODO: get image from database using imageID
        Bitmap image = null;

        ImageView editArtImage = (ImageView) findViewById(R.id.view_art_image);
        editArtImage.setImageBitmap(image);

        final EditText editArtTitle = (EditText) findViewById(R.id.edit_art_title);
        final EditText editArtDescription = (EditText) findViewById(R.id.edit_art_description);

        // Spinner that displays all current tours
        final Spinner toursSpinner = (Spinner) findViewById(R.id.edit_art_tours);
        // TODO: get all tours from database
        List<String> allTours = null;
        ArrayAdapter<String> toursAdapter =
                new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, allTours);
        toursSpinner.setAdapter(toursAdapter);


        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        Button createImageButton = (Button) findViewById(R.id.edit_art_create_button);
        createImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location lastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                long tourId = toursSpinner.getId();
                String artTitle = editArtTitle.getText().toString();
                String artDescription = editArtDescription.getText().toString();
                LatLng location = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

                // add data to database
                //Artwork art = new Artwork(artTitle, artDescription, location);
                // TODO: needs to take associated tour
                // TODO: how do I get the user
                //FireBase.addArtwork(userId, art);
                // TODO: update feed?
            }
        });
    }
}
