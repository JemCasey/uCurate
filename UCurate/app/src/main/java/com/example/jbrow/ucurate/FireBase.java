package com.example.jbrow.ucurate;
import android.util.Log;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.DataSnapshot;

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
            //TODO uncomment once we're adding real tours
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
        toursRef.child(userid).push().setValue(newTour);

        return true;
    }

    //adds an artwork to the database
    public static boolean addArtwork(String userid, Artwork art){
        Log.d(TAG, "entered addArtwork");
        Artwork newArt = new Artwork(art);
        if (userid == null || userid.equals("")){
            return false;
        }
        if (newArt.location == null) {
            //TODO uncomment once we're storing actual artworks
            //return false;
        }
        //Map<String, Tour> t = new HashMap<String, Tour>();
        //t.put(userid, newTour);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference artsRef = ref.child("artwork");
        artsRef.child(userid).push().setValue(newArt);

        return true;
    }


    //TODO figure out how to get the user out of the inner class
    //gotta happen in onChildAdded
    //retrieve a user by their userid from the database
    public static User getUser(String userid){
        final User outputUser;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        Query qRef = ref.orderByKey().equalTo(userid);

        qRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                //outputUser = dataSnapshot.getValue(User.class);

            }
            @Override
            public void onChildRemoved(DataSnapshot d){}
            @Override
            public void onCancelled(DatabaseError d){}
            @Override
            public void onChildChanged(DataSnapshot s, String prevChildName){}
            @Override
            public void onChildMoved(DataSnapshot s, String prevChildName){}

        });

        return null;
    }


    //TODO Methods for checking correct Sign-in


    //TODO Methods for proper registration to the database


    //TODO Methods for getting a specific artwork of the current user signed in


    //TODO Methods for adding in an artwork linking it to the proper user and to the entire artwork database


    //TODO Methods for getting a specific tour of the current user signed in


    //TODO Methods for adding in a tour linking it to the proper user and to the entire tour database


}
