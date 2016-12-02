package com.example.jbrow.ucurate;
import android.util.Log;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by randyflores on 11/29/16.
 */

public final class FireBase {

    private static final String TAG = "FireBase";

    FireBase() {}

    //Adds a new user to the firebase.  Takes a username, name, bio
    public static boolean addUser(String userid, String name, String bio){
        Log.d(TAG, "entered addUser");

        if (name == null || name.equals("")) {
            return false;
        }

        if (bio == null){
            //bio = getResources().getString(R.string.no_bio);
            bio = "no bio yet :(";
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference usersRef = ref.child("users");
        usersRef.child(userid).setValue(new User(name, bio));

        return true;
    }

    //TODO Methods for checking correct Sign-in


    //TODO Methods for proper registration to the database

    //TODO Methods for getting a specific artwork of the current user signed in


    //TODO Methods for adding in an artwork linking it to the proper user and to the entire artwork database


    //TODO Methods for getting a specific tour of the current user signed in


    //TODO Methods for adding in a tour linking it to the proper user and to the entire tour database


}
