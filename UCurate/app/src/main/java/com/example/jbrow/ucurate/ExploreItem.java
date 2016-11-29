package com.example.jbrow.ucurate;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by charmipatel on 11/17/16.
 */

public class ExploreItem {

    public static final String TOUR_NAME = "tour_name";
    public static final String TOUR_DESCRIPTION = "tour_description";
    public static final String TOUR_IMAGE = "tour_image";

    private String mName;
    private String mDescription;
    private Bitmap mTourImage;

    public ExploreItem(String name, String description, Bitmap tourImage) {
        mName = name;
        mDescription = description;
        mTourImage = tourImage;
    }

    public ExploreItem(Intent intent) {
        mName = intent.getStringExtra(ExploreItem.TOUR_NAME);
        mDescription = intent.getStringExtra(ExploreItem.TOUR_DESCRIPTION);
        mTourImage = intent.getParcelableExtra(ExploreItem.TOUR_IMAGE);
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public Bitmap getTourImage() {
        return mTourImage;
    }

}
