package com.example.jbrow.ucurate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FeedFragment extends ListFragment {

    FeedItemAdapter mFeedItemAdapter;
    private SwipeRefreshLayout swipeDownContainer;
    private ArrayList<Object> artworkAndTours = new ArrayList<Object>();
    private ArrayList<Object> dummyData = new ArrayList<Object>();


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference artworkRef = database.getReference("artwork");
        DatabaseReference tourRef = database.getReference("tours");

//        artworkRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
//                    for(DataSnapshot artworkSnapshot : userSnapshot.getChildren()) {
//                        artworkAndTours.add(artworkSnapshot.getValue(Artwork.class));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
//                    for(DataSnapshot tourSnapshot : userSnapshot.getChildren()) {
//                        artworkAndTours.add(tourSnapshot.getValue(Tour.class));
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        String userId = getArguments().getString(User.USER_ID);

        mFeedItemAdapter = new FeedItemAdapter(getActivity().getApplicationContext(), userId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        mFeedItemAdapter.addAll(dummyData);
        setListAdapter(mFeedItemAdapter);

        swipeDownContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeDownContainer);
        swipeDownContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // get new data
                mFeedItemAdapter.clear();
                mFeedItemAdapter.addAll(dummyData);
                swipeDownContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeDownContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        return view;
    }
}
