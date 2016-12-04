package com.example.jbrow.ucurate;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by randyflores on 11/30/16.
 */


public class Tour implements Parcelable {

    public static final String TOUR_NAME = "tour_name";
    public static final String TOUR_DESCRIPTION = "tour_description";
    public static final String TOUR_IMAGE = "tour_image";
    public static final String TOUR_ID = "tour_id";

    ArrayList<Artwork> artworkList = new ArrayList<Artwork>();
    String title;
    String description;
    LatLng location;
    String userID;
    Artwork start;
    String id;
    Date timeCreated;

    public Tour() {}

    public Tour(ArrayList<Artwork> artworkList, String title, String userID, String description) {
        this.artworkList.addAll(artworkList);
        this.title = title;
        this.description = description;
        this.userID = userID;
        this.location = artworkList.get(0).location;
        this.start = artworkList.get(0);
    }

    //copy constructor
    // -Matt
    public Tour(Tour other){
        this.artworkList = new ArrayList<Artwork>();
        for (Artwork a : other.artworkList){
            this.artworkList.add(a);
        }
        this.title = other.title;
        this.description = other.description;
        this.userID = other.userID;
        this.location = other.location;
        this.start = other.start;
    }
    //constructor for empty tour, no list of artwork yet
    //I may very well have missed an important instantiation,
    //feel free to change this, it's just for testing in a world
    //were lists of artwork are hard to come by.
    // -Matt
    public Tour(String title, String description, String UserId) {
        this.title = title;
        this.description = description;
    }

    protected Tour(Parcel in) {
        artworkList = in.createTypedArrayList(Artwork.CREATOR);
        title = in.readString();
        description = in.readString();
        location = (LatLng) in.readValue(LatLng.class.getClassLoader());
        start = (Artwork) in.readValue(Artwork.class.getClassLoader());
    }

    public Tour(Intent intent) {
        title = intent.getStringExtra(Tour.TOUR_NAME);
        description = intent.getStringExtra(Tour.TOUR_DESCRIPTION);
        // TODO: find out if we need this
        // tourImage = intent.getParcelableExtra(Tour.TOUR_IMAGE);
    }

    public ArrayList<Artwork> getArtworkList() {
        return artworkList;
    }

    public void setArtworkList(ArrayList<Artwork> artworkList) {
        this.artworkList = artworkList;
    }

    public String getUserID() { return userID; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public Artwork getStart() {
        return start;
    }

    public void setStart(Artwork start) {
        this.start = start;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(artworkList);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeValue(location);
        parcel.writeValue(start);
    }

    public static final Parcelable.Creator<Tour> CREATOR = new Parcelable.Creator<Tour>() {
        @Override
        public Tour createFromParcel(Parcel in) {
            return new Tour(in);
        }

        @Override
        public Tour[] newArray(int size) {
            return new Tour[size];
        }
    };

    public void addToTour(ArrayList<Artwork> newStops) {
        // TODO Account for a user rearranging the order of a tour
        artworkList.addAll(newStops);
        start = artworkList.get(0);
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) { this.id = id; }
    public void setDate(Date date) { this.timeCreated = date; }
}
