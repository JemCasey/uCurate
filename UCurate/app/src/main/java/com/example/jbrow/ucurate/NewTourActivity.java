package com.example.jbrow.ucurate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewTourActivity extends Activity {
    String userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        setContentView(R.layout.activity_new_collection);

        Button createTour = (Button) findViewById(R.id.add_new_tour);
        createTour.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText tourName = (EditText) findViewById(R.id.new_tour_name);
                EditText tourDescription = (EditText) findViewById(R.id.new_tour_description);
                Tour newTour = new Tour(tourName.getText().toString(), tourDescription.getText().toString(), userID);
                FireBase.addTour("1", newTour);
                Intent intent = new Intent(NewTourActivity.this, AddToTourActivity.class);
                intent.putExtra("tour_name", tourName.getText());
                intent.putExtra("tour_description", tourDescription.getText());

                startActivity(intent);
            }
        });
    }

}

