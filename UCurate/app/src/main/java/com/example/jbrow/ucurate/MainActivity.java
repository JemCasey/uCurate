package com.example.jbrow.ucurate;


import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
    implements OnFragmentInteractionListener {

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
    private FloatingActionButton addGalleryFab;

    private boolean isFabOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

    public void loadFab() {
        mainFab = (FloatingActionButton) findViewById(R.id.main_fab);
        mainFab.setClickable(true);

        addArtFab = (FloatingActionButton) findViewById(R.id.add_art_fab);
        addArtFab.setClickable(false);

        addGalleryFab = (FloatingActionButton) findViewById(R.id.add_gallery_fab);
        addGalleryFab.setClickable(false);

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

        addGalleryFab.setOnClickListener(new View.OnClickListener() {
            // start activity to add a gallery
            public void onClick(View view) {

            }
        });

    }

    public void animateFab() {
        if(isFabOpen) {
            mainFab.startAnimation(fabRotateCcw);
            isFabOpen = false;

            addArtFab.startAnimation(fabClose);
            addArtFab.setClickable(false);

            addGalleryFab.startAnimation(fabClose);
            addGalleryFab.setClickable(false);
        } else {
            mainFab.startAnimation(fabRotateCw);


            addArtFab.startAnimation(fabOpen);
            addArtFab.setClickable(true);

            addGalleryFab.startAnimation(fabOpen);
            addGalleryFab.setClickable(true);

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
