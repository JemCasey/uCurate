package com.example.jbrow.ucurate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent openTour = new Intent(MainActivity.this,ViewTourActivity.class);
                startActivity(openTour);
            }
        });
    }
}
