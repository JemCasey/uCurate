package com.example.jbrow.ucurate;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by charmipatel on 11/29/16.
 */

public class AccountItem {
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";

    private String mName;
    private String mDescription;
    private Bitmap mImage;

    public AccountItem(String name, String description, Bitmap image) {
        mName = name;
        mDescription = description;
        mImage = image;
    }

    public AccountItem(Intent intent) {
        mName = intent.getStringExtra(AccountItem.NAME);
        mDescription = intent.getStringExtra(AccountItem.DESCRIPTION);
        mImage = intent.getParcelableExtra(AccountItem.IMAGE);
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public Bitmap getImage() {
        return mImage;
    }
}
