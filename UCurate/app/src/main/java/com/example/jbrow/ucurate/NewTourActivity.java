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
        userID = intent.getStringExtra(User.USER_ID);

        setContentView(R.layout.activity_new_collection);

        Button createTour = (Button) findViewById(R.id.add_new_tour);
        createTour.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText tourName = (EditText) findViewById(R.id.new_tour_name);
                EditText tourDescription = (EditText) findViewById(R.id.new_tour_description);
                Tour newTour = new Tour(tourName.getText().toString(), tourDescription.getText().toString(), userID);
                FireBase.addTour(userID, newTour);
                Intent intent = new Intent(NewTourActivity.this, EditTourActivity.class);
                intent.putExtra(User.USER_ID, userID);
                intent.putExtra(Tour.TOUR_NAME, tourName.getText().toString());
                intent.putExtra(Tour.TOUR_DESCRIPTION, tourDescription.getText().toString());
                startActivity(intent);
            }
        });
    }

}

