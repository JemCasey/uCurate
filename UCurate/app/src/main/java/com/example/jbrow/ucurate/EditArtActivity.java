package com.example.jbrow.ucurate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditArtActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    // Reference to the LocationManager
    private LocationManager mLocationManager;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mUsername = getIntent().getStringExtra(User.USER_ID);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setContentView(R.layout.activity_edit_art);

            final String userId = mUsername;
            ImageView imageView = (ImageView) findViewById(R.id.edit_art_image);

            final Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);


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

                    Artwork artwork = new Artwork(artTitle, artDescription, location, userId, bitmap);

                    //String artworkId = FireBase.addArtwork(userId, artwork, bitmap);

                    // TODO: update feed?

                    // TODO: start view art activity
//                    Intent viewArtIntent = new Intent(getApplicationContext(), ViewArtActivity.class);
//                    viewArtIntent.putExtra(Artwork.ARTWORK_ID, artworkId);
//                    viewArtIntent.putExtra(User.USER_ID, userId);
                    finish();
                }
            });
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        // mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


}

