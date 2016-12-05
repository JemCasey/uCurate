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


    public static ArrayList<Artwork> getArtwork(Context context) {
        ArrayList<Artwork> artwork = new ArrayList<Artwork>();
        for (int i = 0; i < 10; i++) {

            Bitmap temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.spiritualheroes);
            artwork.add(new Artwork("test image", "test image description", new LatLng(38.990633, -76.949384), "bob", temp));
        }

        return artwork;
    }

    public static ArrayList<Tour> getTour(Context context) {
        ArrayList<Tour> tour = new ArrayList<Tour>();

        ArrayList<Artwork> artwork = new ArrayList<Artwork>();
        for (int i = 0; i < 10; i++) {

            Bitmap temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.spiritualheroes);
            artwork.add(new Artwork("test image", "test image description", new LatLng(38.990633, -76.949384), "bob", temp));
        }

        for (int i = 0; i < 10; i++) {
            tour.add(new Tour(artwork, "test tour", "bob", "test tour description"));
        }
        return tour;
    }

    public static User getUser(Context context) {

        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.betty);
        User user = new User("bob", "i'm a dummy value", image);

        ArrayList<Artwork> artwork = new ArrayList<Artwork>();
        for (int i = 0; i < 10; i++) {

            Bitmap temp = BitmapFactory.decodeResource(context.getResources(), R.drawable.spiritualheroes);
            artwork.add(new Artwork("test image", "test image description", new LatLng(38.990633, -76.949384), "bob", temp));
        }

        user.setArtworkList(artwork);

        ArrayList<Tour> tour = new ArrayList<Tour>();

        for (int i = 0; i < 10; i++) {
            tour.add(new Tour(artwork, "test tour", "bob", "test tour description"));
        }

        user.setTourList(tour);

        return user;
    }

    public static Artwork getArtWork(String userID, String curr, Context context) {
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.rheinii);
        return new Artwork(new Artwork("test image", "test image description", new LatLng(38.990633, -76.949384), "bob", image));
    }
}
