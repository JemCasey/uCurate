package com.example.jbrow.ucurate;
import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by randyflores on 11/29/16.
 */

public final class FireBase {

    private static final String TAG = "FireBase";
    private static FirebaseAuth auth;

    FireBase() {}

    //Adds a new user to the firebase.  Takes a username, name, bio
    public static boolean addUser(String userid, User u){
        Log.d(TAG, "entered addUser");
        User newU = new User(u);
        if (userid == null || userid.equals("")) {
            return false;
        }

        if (u.name == null){
            u.name = "No name entered";
        }

        if (u.biography == null){
            //bio = getResources().getString(R.string.no_bio);
            u.biography = "no bio yet :(";
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference usersRef = ref.child("users");
        usersRef.child(userid).setValue(newU);

        return true;
    }

    //Adds a tour to the database
    public static boolean addTour(String userid, Tour tour) {
        Log.d(TAG, "entered addTour");
        Tour newTour = new Tour(tour);

        if (userid == null || userid.equals("")){
            return false;
        }
        if (newTour.start == null){
            //return false;
        }
        if (newTour.description == null){
            newTour.description = "No description";
        }
        if (newTour.title == null){
            newTour.title = "Untitled";
        }

        //Map<String, Tour> t = new HashMap<String, Tour>();
        //t.put(userid, newTour);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference toursRef = ref.child("tours");
        toursRef.child(userid).setValue(newTour);

        return true;
    }


    //TODO Methods for checking correct Sign-in


    //TODO Methods for proper registration to the database


    //TODO Methods for getting a specific artwork of the current user signed in


    //TODO Methods for adding in an artwork linking it to the proper user and to the entire artwork database


    //TODO Methods for getting a specific tour of the current user signed in


    //TODO Methods for adding in a tour linking it to the proper user and to the entire tour database


}
