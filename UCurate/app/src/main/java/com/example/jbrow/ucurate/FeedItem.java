package com.example.jbrow.ucurate;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by charmipatel on 11/15/16.
 */

public class FeedItem {

    public static final String USER = "user";
    public static final String UPDATE = "update";
    public static final String USER_IMAGE = "user_image";
    public static final String UPDATE_IMAGE = "update_image";

    private String mUser;
    private String mUpdate;
    private Bitmap mUserImage;
    private Bitmap mUpdateImage;

    public FeedItem(String user, String update, Bitmap userImage, Bitmap updateImage) {
        mUser = user;
        mUpdate = update;
        mUserImage = userImage;
        mUpdateImage = updateImage;
    }

    public FeedItem(Intent intent) {
        mUser = intent.getStringExtra(FeedItem.USER);
        mUpdate = intent.getStringExtra(FeedItem.UPDATE);
        mUserImage = intent.getParcelableExtra(FeedItem.USER_IMAGE);
        mUpdateImage = intent.getParcelableExtra(FeedItem.UPDATE_IMAGE);
    }

    public String getUser() {
        return mUser;
    }

    public String getUpdate() {
        return mUpdate;
    }

    public Bitmap getUserImage() {
        return mUserImage;
    }

    public Bitmap getUpdateImage() {
        return mUpdateImage;
    }
}
