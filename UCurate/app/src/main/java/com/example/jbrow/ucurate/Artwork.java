package com.example.jbrow.ucurate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import java.util.Date;

/**
 * Created by jbrow on 11/15/2016.
 */

public class Artwork implements Parcelable{

    public static final String ARTWORK_NAME = "artwork_name";
    public static final String ARTWORK_DESCRIPTION = "artwork_description";
    public static final String ARTWORK_IMAGE = "artwork_image";
    public static final String ARTWORK_ID = "artwork_id";

    Bitmap image;
    String title;
    String description;
    LatLng location;
    String userID;
    String path;
    String id;
    Date timeCreated;

    private StorageReference mStorageRef;
    private StorageReference mChildStorageRef;


    public Artwork() {}

    public Artwork(String title, String description, LatLng location, String userID) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.userID = userID;
    }

    public Artwork(String title, String description, LatLng location, String userID, Bitmap image) {
        this(title, description, location, userID);
        this.image = image;
    }

    //copy constructor
    // -Matt
    public Artwork(Artwork other){
        this.title = other.title;
        this.description = other.description;
        this.location = other.location;
    }
    //basic constructor for testing
    // -Matt
    public Artwork(String title, String description) {
        this.title = title;
        this.description = description;
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

    public String getUserID() { return userID; }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void downloadArtwork() {
        StorageReference mSpecificStorageRef;
        mSpecificStorageRef = mStorageRef.child(path);
        final long ONE_MEGABYTE = 1024 * 1024;
        mSpecificStorageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap result = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                setImage(result);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void uploadArtwork(Uri uri) {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mChildStorageRef = mStorageRef.child("Photos");

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
                setPath(taskSnapshot.getMetadata().getPath());
            }
        });
    }

    public void uploadArtwork(Bitmap inputBitmap) {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Log.d("Artwork", "uploadArtwork: id=" + id);
        mChildStorageRef = mStorageRef.child(id);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        inputBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mChildStorageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                setPath(taskSnapshot.getMetadata().getPath());

            }
        });
    }

    public void uploadArtwork() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mChildStorageRef = mStorageRef.child(id);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mChildStorageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                setPath(taskSnapshot.getMetadata().getPath());

            }
        });
    }



    public void setId(String id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.timeCreated = date;
    }

    public Date getDate() {
        return timeCreated;
    }
}
