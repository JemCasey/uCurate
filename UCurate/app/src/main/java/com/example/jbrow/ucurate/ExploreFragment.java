package com.example.jbrow.ucurate;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.ListFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ExploreFragment extends ListFragment {

    ArrayList<Tour> exploreTours = new ArrayList<Tour>();

    ExploreItemAdapter mExploreItemAdapter;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //final Comparator<Tour> tourComparator = new TourComparator(getLocationOnce());

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tours");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //mUser = dataSnapshot.getValue(User.class);
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
//                    for(DataSnapshot tourSnapshot : userSnapshot.getChildren()) {
//                        exploreTours.add(tourSnapshot.getValue(Tour.class));
//                        Collections.sort(exploreTours, tourComparator);
//
//                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String userId = getArguments().getString(User.USER_ID);

        mExploreItemAdapter = new ExploreItemAdapter(getActivity().getApplicationContext(), userId);
    }

    private Location getLocationOnce() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        Location location = null;
        try {
            for (int i = providers.size() - 1; i >= 0; i--) {
                location = lm.getLastKnownLocation(providers.get(i));
                if (location != null) break;
            }
        } catch (SecurityException e) {

        }
        return location;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_explore, container, false);

        setListAdapter(mExploreItemAdapter);

        return view;

    }

    private class TourComparator implements Comparator<Tour> {
        Location userLocation;

        public TourComparator(Location userLocation) {
            this.userLocation = userLocation;
        }
        @Override
        public int compare(Tour tour1, Tour tour2) {
            LatLng Location1 = new LatLng(tour1.getLat(),tour1.getLng());
            LatLng Location2 = new LatLng(tour2.getLat(), tour2.getLng());
            float[] results1 = new float[3];
            float[] results2 = new float[3];

            Location.distanceBetween(
                    userLocation.getLatitude(),
                    userLocation.getLongitude(),
                    Location1.latitude,
                    Location1.longitude,
                    results1);

            Location.distanceBetween(
                    userLocation.getLatitude(),
                    userLocation.getLongitude(),
                    Location2.latitude,
                    Location2.longitude,
                    results2);

            if (results1[0] > results2[0]) {
                return 1;
            } else if (results1[0] < results2[0]) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
