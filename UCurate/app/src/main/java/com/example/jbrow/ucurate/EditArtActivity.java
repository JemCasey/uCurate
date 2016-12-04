package com.example.jbrow.ucurate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class EditArtActivity extends AppCompatActivity {

    // Reference to the LocationManager
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_art);

        Intent intent = getIntent();
        Uri imageUri = Uri.parse(intent.getStringExtra("IMAGE_URI"));
        final String userId = intent.getStringExtra(User.USER_ID);

        Bitmap image = null;
        try {
            image = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Image could not be found", Toast.LENGTH_LONG);
            // TODO: go back to main?
            e.printStackTrace();
        }
        final Bitmap finalImage = image;

        ImageView editArtImage = (ImageView) findViewById(R.id.edit_art_image);
        editArtImage.setImageBitmap(image);

        final EditText editArtTitle = (EditText) findViewById(R.id.edit_art_title);
        final EditText editArtDescription = (EditText) findViewById(R.id.edit_art_description);

//        // Spinner that displays all current tours
//        final Spinner toursSpinner = (Spinner) findViewById(R.id.edit_art_tours);
//        // TODO: get all tours from database
//        List<String> allTours = null;
//        ArrayAdapter<String> toursAdapter =
//                new ArrayAdapter<String>(getApplicationContext(),
//                        android.R.layout.simple_spinner_item, allTours);
//        toursSpinner.setAdapter(toursAdapter);


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

                String artTitle = editArtTitle.getText().toString();
                String artDescription = editArtDescription.getText().toString();
                LatLng location = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

                Artwork artwork = new Artwork(artTitle, artDescription, location, userId, finalImage);
                artwork.uploadArtwork();

                String artworkId = FireBase.addArtwork(userId, artwork);

                // TODO: update feed?

                // TODO: start view art activity
                Intent viewArtIntent = new Intent(getApplicationContext(), ViewArtActivity.class);
                viewArtIntent.putExtra(Artwork.ARTWORK_ID, artworkId);
                viewArtIntent.putExtra(User.USER_ID, userId);
            }
        });
    }
}
