package com.example.jbrow.ucurate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;

public class ShareActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private Artwork shareArt;
    private ShareDialog mShareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        shareArt = new Artwork("Betty", "this is by Gerhard Richter", new LatLng(38.99, -76.947760), BitmapFactory.decodeResource(getResources(),R.drawable.betty));

        ImageView image = (ImageView) findViewById(R.id.share_image);
        image.setImageBitmap(shareArt.getImage());

        TextView title = (TextView) findViewById(R.id.share_title);
        title.setText(shareArt.getTitle());

        TextView description = (TextView) findViewById(R.id.share_description);
        description.setText(shareArt.getDescription());

        setUpFacebookButton();
    }

    private void setUpFacebookButton() {
        mShareDialog = new ShareDialog(this);

        ShareButton shareFacebook = (ShareButton) findViewById(R.id.share_facebook);
        shareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> permissionNeeds = Arrays.asList("publish_actions");

                //this loginManager helps you eliminate adding a LoginButton to your UI
                loginManager = LoginManager.getInstance();

                loginManager.logInWithPublishPermissions(ShareActivity.this, permissionNeeds);

                loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
                {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        sharePhotoToFacebook();
                    }

                    @Override
                    public void onCancel()
                    {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception)
                    {
                        System.out.println("onError");
                    }
                });
            }
        });

    }

    private void sharePhotoToFacebook(){
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(shareArt.getImage())
                .setCaption("Facebook SDK Test")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        mShareDialog.show(content);
        //ShareApi.share(content, null);

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }
}
