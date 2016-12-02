package com.example.jbrow.ucurate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewTourActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_collection);
        Button createCollection = (Button) findViewById(R.id.newcollection);
        createCollection.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText collectionName = (EditText) findViewById(R.id.collectioname);
                EditText collectionDescription = (EditText) findViewById(R.id.collectiondescription);
                Intent intent = new Intent(NewTourActivity.this, AddToTourActivity.class);
                intent.putExtra("name", collectionName.getText());
                startActivity(intent);
            }
        });
    }

}

