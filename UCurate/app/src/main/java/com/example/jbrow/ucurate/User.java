package com.example.jbrow.ucurate;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Created by randyflores on 12/1/16.
 */

public class User implements Parcelable {

    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_BIO = "user_bio";
    public static final String USER_IMAGE = "user_image";

    String name;
    String biography;
    Bitmap userImage;
    ArrayList<Artwork> artworkList = new ArrayList<Artwork>();
    ArrayList<Tour> tourList = new ArrayList<Tour>();

    public User() {}

    public User(String name, String biography) {
        this.name = name;
        this.biography = biography;
    }

    //copy constructor
    // -Matt
    public User(User other){
        this.name = other.name;
        this.biography = other.biography;
        this.userImage = other.userImage;
        this.setArtworkList(other.getArtworkList());
    }

    protected User(Parcel in) {
        name = in.readString();
        biography = in.readString();
        artworkList = in.createTypedArrayList(Artwork.CREATOR);
        tourList = in.createTypedArrayList(Tour.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(biography);
        dest.writeTypedList(artworkList);
        dest.writeTypedList(tourList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Bitmap getUserImage() { return userImage; }

    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }

    public ArrayList<Artwork> getArtworkList() {
        return artworkList;
    }

    public void setArtworkList(ArrayList<Artwork> artworkList) {
        this.artworkList = artworkList;
    }

    public ArrayList<Tour> getTourList() {
        return tourList;
    }

    public void setTourList(ArrayList<Tour> tourList) {
        this.tourList = tourList;
    }

    public void addArtwork(Artwork artwork) {
        artworkList.add(artwork);
    }

    public void addTour(Tour tour) {
        tourList.add(tour);
    }

    //TODO add in appropriate firebase stuff
}
