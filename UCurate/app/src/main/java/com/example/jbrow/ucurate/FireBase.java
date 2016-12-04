package com.example.jbrow.ucurate;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;


import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;


/**
 * Created by randyflores on 11/29/16.
 */

public final class FireBase {

    private static final String TAG = "FireBase";
    private static FirebaseAuth auth;
    public static User currentUser;
    public static Artwork currentArtwork;
    public static Tour currentTour;
    public static String artworkID, tourID;
    public static ArrayList<Tour> tourArrayList = new ArrayList<Tour>();
    public static LimitedSizeQueue<Artwork> artworkQueue = new LimitedSizeQueue<Artwork>(10);
    public static LimitedSizeQueue<Tour> tourQueue = new LimitedSizeQueue<Tour>(10) ;
    public static ArrayList<Object> artworkTourArrayList = new ArrayList<>(10);


    FireBase() {}

    public static class LimitedSizeQueue<K> extends ArrayList<K> {

        private int maxSize;
        private static int youngestCounter;

        public LimitedSizeQueue(int size){
            this.maxSize = size;
            this.youngestCounter = size-1;
        }

        public boolean add(K k){
            boolean r = super.add(k);
            if (size() > maxSize){
                removeRange(0, size() - maxSize - 1);
            }
            return r;
        }

        public K getYongest() {
            youngestCounter = maxSize-1;
            return get(size() - 1);
        }

        public K getOldest() {
            return get(0);
        }

        public K getNextYoungest() {
            if (youngestCounter < 0)
                return null;
            youngestCounter--;
            return get(youngestCounter);
        }
    }

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
    public static String addTour(String userid, Tour tour) {
        Log.d(TAG, "entered addTour");
        Tour newTour = new Tour(tour);

        if (userid == null || userid.equals("")){
            return null;
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
        tourID = toursRef.child(userid).push().getKey();
        toursRef.child(userid).child(tourID).setValue(newTour);
        toursRef.child(userid).child(tourID).child("id").setValue(tourID);
        toursRef.child(userid).child(tourID).child("timeCreated").setValue(new Date());

        return tourID;
    }


    //adds an artwork to the database
    public static String addArtwork(String userid, Artwork art, Bitmap bitmap) {
        Log.d(TAG, "entered addArtwork");
        Artwork newArt = new Artwork(art);
        if (userid == null || userid.equals("")) {
            return null;
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
        artworkID = artsRef.child(userid).push().getKey();
        artsRef.child(userid).child(artworkID).setValue(newArt);
        artsRef.child(userid).child(artworkID).child("id").setValue(artworkID);
        artsRef.child(userid).child(artworkID).child("timeCreated").setValue(new Date());
        art.id = artworkID;
        art.uploadArtwork(bitmap);


        return artworkID;
    }


    //TODO figure out how to get the user out of the inner class
    //gotta happen in onChildAdded
    //retrieve a user by their userid from the database
    /*public static User getUser(String userid){
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
    }*/

    public static User getUser(String userid){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        Query qRef = ref.orderByKey().equalTo(userid);

        qRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return currentUser;
    }


    public static boolean changeUserName(String userID, String newUserID){
        Log.d(TAG, "entered changeUserName");

        if (userID == null || userID.equals("")) {
            return false;
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference usersRef = ref.child("users");
        usersRef.child(userID).child("name").setValue(newUserID);

        return true;
    }

    public static boolean changeUserBio(String userID, String newUserBio){
        Log.d(TAG, "entered changeUserName");

        if (userID == null || userID.equals("")) {
            return false;
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference usersRef = ref.child("users");
        usersRef.child(userID).child("biography").setValue(newUserBio);

        return true;
    }

    public static ArrayList<Artwork> getUserArtwork(String userID) {
        User currUser = getUser(userID);
        return currUser.getArtworkList();
    }

    public static ArrayList<Tour> getUserTour(String userID) {
        User currUser = getUser(userID);
        return currUser.getTourList();
    }

    public static String getUserName(String userID) {
        User currUser = getUser(userID);
        return currUser.getName();
    }

    public static String getUserBio(String userID) {
        User currUser = getUser(userID);
        return currUser.getBiography();
    }

    public static boolean changeUserArtwork(String userID, String artworkID, ArrayList<Artwork> newAddedArtwork){
        Log.d(TAG, "entered changeUserName");

        if (userID == null || userID.equals("")) {
            return false;
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference usersRef = ref.child("users");
        usersRef.child(userID).child("artworkList").child(artworkID).setValue(newAddedArtwork);

        return true;
    }

    public static boolean changeUserTour(String userID, String tourID, ArrayList<Tour> newAddedTour){
        Log.d(TAG, "entered changeUserName");

        if (userID == null || userID.equals("")) {
            return false;
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        DatabaseReference usersRef = ref.child("users");
        usersRef.child(userID).child("tourList").child(tourID).setValue(newAddedTour);

        return true;
    }


    public static ArrayList<Object> getRecent10() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference artworkRef = database.getReference("artwork");
        DatabaseReference tourRef = database.getReference("tours");

        artworkRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    artworkQueue.add(postSnapshot.getValue(Artwork.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    tourQueue.add(postSnapshot.getValue(Tour.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Tour currTour = tourQueue.getNextYoungest();
        Artwork currArtwork = artworkQueue.getNextYoungest();
        artworkTourArrayList.clear();
        while (artworkTourArrayList.size() != 10) {
            if (currTour.getDate().compareTo(currArtwork.getDate()) <= 0) {
                artworkTourArrayList.add(currArtwork);
                currArtwork = artworkQueue.getNextYoungest();
            }
            else {
                artworkTourArrayList.add(currTour);
                currTour = tourQueue.getNextYoungest();
            }
        }

        return artworkTourArrayList;
    }

    public static ArrayList<Tour> getTours(){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tours");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    tourArrayList.add(postSnapshot.getValue(Tour.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return tourArrayList;
    }

    public static Artwork getArtWork(String userID, String artworkID){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users").child(userID);
        Query qRef = ref.orderByKey().equalTo(artworkID);

        qRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentArtwork = dataSnapshot.getValue(Artwork.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return currentArtwork;
    }

    public static Tour getTour(String userID, String tourID){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users").child(userID);
        Query qRef = ref.orderByKey().equalTo(tourID);

        qRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentTour = dataSnapshot.getValue(Tour.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return currentTour;
    }
    /*public void uploadArtwork(Uri uri) {
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference mChildStorageRef = mStorageRef.child("Photos");

        mChildStorageRef.getMetadata().
        UploadTask uploadTask = mChildStorageRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //setPath(taskSnapshot.getMetadata().getPath());
            }
        });
    }*/



    //TODO Methods for checking correct Sign-in


    //TODO Methods for proper registration to the database


    //TODO Methods for getting a specific artwork of the current user signed in


    //TODO Methods for adding in an artwork linking it to the proper user and to the entire artwork database


    //TODO Methods for getting a specific tour of the current user signed in


    //TODO Methods for adding in a tour linking it to the proper user and to the entire tour database


}
