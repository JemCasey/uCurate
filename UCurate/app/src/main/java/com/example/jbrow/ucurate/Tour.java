package com.example.jbrow.ucurate;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
/**
 * Created by randyflores on 11/30/16.
 */


public class Tour implements Parcelable {
    ArrayList<Artwork> artworkList = new ArrayList<Artwork>();
    String title;
    String description;
    LatLng location;
    Artwork start;

    public Tour(ArrayList<Artwork> artworkList, String title, String description) {
        this.artworkList.addAll(artworkList);
        this.title = title;
        this.description = description;
        this.location = artworkList.get(0).location;
        this.start = artworkList.get(0);
    }

    protected Tour(Parcel in) {
        artworkList = in.createTypedArrayList(Artwork.CREATOR);
        title = in.readString();
        description = in.readString();
        location = (LatLng) in.readValue(LatLng.class.getClassLoader());
        start = (Artwork) in.readValue(Artwork.class.getClassLoader());
    }

    public ArrayList<Artwork> getArtworkList() {
        return artworkList;
    }

    public void setArtworkList(ArrayList<Artwork> artworkList) {
        this.artworkList = artworkList;
    }

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
}
