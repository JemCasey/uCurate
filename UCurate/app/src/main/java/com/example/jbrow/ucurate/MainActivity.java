package com.example.jbrow.ucurate;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements OnFragmentInteractionListener {

    private static final String ANONYMOUS = "None";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    // FAB Animations
    private Animation fabRotateCw;
    private Animation fabRotateCcw;
    private Animation fabOpen;
    private Animation fabClose;

    private FloatingActionButton mainFab;
    private FloatingActionButton addArtFab;
    private FloatingActionButton addtourFab;

    private boolean isFabOpen;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername;
    String mCurrentPhotoPath; //tracks for URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mUsername = getIntent().getStringExtra("UserID");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        //mGoogleApiClient = getIntent().getParcelableExtra("mGoogleApiClient");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        loadFab();

        Log.d("MainActivity", "onCreate entered");
        FireBase.addUser("userIDtest4", new User("nametest4", "biotest4"));
        FireBase.addTour("userIDtest4", new Tour("testTour3","testTourDesc3"));
        FireBase.addArtwork("userID1", new Artwork("testArt","testArtDesc"));

    }


    

    public void loadFab() {
        Log.d("MainActivity", "loadFab entered");

        mainFab = (FloatingActionButton) findViewById(R.id.main_fab);
        mainFab.setClickable(true);

        addArtFab = (FloatingActionButton) findViewById(R.id.add_art_fab);
        addArtFab.setClickable(false);

        addtourFab = (FloatingActionButton) findViewById(R.id.add_tour_fab);
        addtourFab.setClickable(false);

        fabRotateCw = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_cw);
        fabRotateCcw = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_ccw);
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        isFabOpen = false;


        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });


        addArtFab.setOnClickListener(new View.OnClickListener() {
            // start activity to add an image
            public void onClick(View view) {


            }
        });

        addtourFab.setOnClickListener(new View.OnClickListener() {
            // start activity to add a tour
            public void onClick(View view) {
                Intent addTourIntent = new Intent(getApplicationContext(), NewTourActivity.class);
                startActivity(addTourIntent);
            }
        });

    }



    public void animateFab() {
        if(isFabOpen) {
            mainFab.startAnimation(fabRotateCcw);
            isFabOpen = false;

            addArtFab.startAnimation(fabClose);
            addArtFab.setClickable(false);

            addtourFab.startAnimation(fabClose);
            addtourFab.setClickable(false);
        } else {
            mainFab.startAnimation(fabRotateCw);


            addArtFab.startAnimation(fabOpen);
            addArtFab.setClickable(true);

            addtourFab.startAnimation(fabOpen);
            addtourFab.setClickable(true);

            isFabOpen = true;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_sample_tour) {
            LatLng locone = new LatLng(38.990633, -76.949384);

            LatLng loctwo = new LatLng(38.984929, -76.947760);

            ArrayList<Artwork> artworks = new ArrayList<>();
            artworks.add(new Artwork("Rhein II", "This is by Andreas Gursky. It became the most expensive photograph ever sold when a print was auctioned for $4.3 million in 2011.", locone));
            artworks.add(new Artwork("Betty", "this is by Gerhard Richter", loctwo));
            artworks.add(new Artwork("Germany's Spiritual Heroes", "this is by Anselm Kiefer", new LatLng(38.992884, -76.948417)));
            Intent openTour = new Intent(MainActivity.this,ViewTourActivity.class);
            openTour.putParcelableArrayListExtra("artworks", artworks);
            startActivity(openTour);

        } else if (id == R.id.action_sample_edit_tour) {
            Intent openEditTour = new Intent(MainActivity.this, EditTourActivity.class);
            startActivity(openEditTour);
        } else if (id == R.id.sign_out_menu) {
            mFirebaseAuth.signOut();
            mUsername = ANONYMOUS;
            startActivity(new Intent(this, Login.class));
        } else if (id == R.id.action_sample_share) {
            Intent openShare = new Intent(MainActivity.this, ShareActivity.class);
            startActivity(openShare);
        }

        return super.onOptionsItemSelected(item);
    }


    public void onFragmentInteraction(int position) {
//        FeedFragment articleFrag = (FeedFragment)
//                getSupportFragmentManager().findFragmentById(position);

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final int mNumTabs = 3;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//            return PlaceholderFragment.newInstance(position + 1);
            switch (position) {
                case 0:
                    return new FeedFragment();
                case 1:
                    return new ExploreFragment();
                case 2:
                    return new AccountFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mNumTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.feed_title);
                case 1:
                    return getString(R.string.explore_title);
                case 2:
                    return getString(R.string.account_title);
                default:
                    return null;
            }
        }
    }
}
