package com.example.jbrow.ucurate;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jbrow on 11/15/2016.
 */

public class Artwork implements Parcelable {
    Bitmap image;
    String title;
    String description;
    LatLng location;

    public Artwork(String title, String description, LatLng location) {
        this.title = title;
        this.description = description;
        this.location = location;
    }

    public Artwork(String title, String description, LatLng location, Bitmap image) {
        this(title, description, location);
        this.image = image;
    }

    protected Artwork(Parcel in) {
        image = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        title = in.readString();
        description = in.readString();
        location = (LatLng) in.readValue(LatLng.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(image);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeValue(location);
    }

    public static final Parcelable.Creator<Artwork> CREATOR = new Parcelable.Creator<Artwork>() {
        @Override
        public Artwork createFromParcel(Parcel in) {
            return new Artwork(in);
        }

        @Override
        public Artwork[] newArray(int size) {
            return new Artwork[size];
        }
    };


    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public Bitmap getImage() {

        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LatLng getLocation() {
        return location;
    }
}
