package com.example.jbrow.ucurate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by charmipatel on 12/4/16.
 */

public class DummyData {

    ArrayList<Artwork> artwork = new ArrayList<Artwork>();
    ArrayList<Tour> tour = new ArrayList<Tour>();
    User user;

    public DummyData(Context context) {
        for (int i = 0; i < 10; i++) {

            Bitmap temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.spiritualheroes);
            artwork.add(new Artwork("test image", "test image description", new LatLng(38.990633, -76.949384), "bob", temp));
        }
        for (int i = 0; i < 10; i++) {
            tour.add(new Tour(artwork, "test tour", "bob", "test tour description"));
        }
        user = new User("bob", "i'm a dummy value");
        user.setArtworkList(artwork);
        user.setTourList(tour);
    }

    public ArrayList<Artwork> getArtwork() {
        return artwork;
    }

    public ArrayList<Tour> getTour() {
        return tour;
    }

    public User getUser() {
        return user;
    }

}
