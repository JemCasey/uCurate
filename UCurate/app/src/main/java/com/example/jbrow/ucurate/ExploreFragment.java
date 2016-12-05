package com.example.jbrow.ucurate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.ListFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ExploreFragment extends ListFragment {

    ArrayList<Tour> exploreTours = new ArrayList<Tour>();

    ExploreItemAdapter mExploreItemAdapter;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tours");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //mUser = dataSnapshot.getValue(User.class);
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    for(DataSnapshot tourSnapshot : userSnapshot.getChildren()) {
                            exploreTours.add(tourSnapshot.getValue(Tour.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        String userId = getArguments().getString(User.USER_ID);

        mExploreItemAdapter = new ExploreItemAdapter(getActivity().getApplicationContext(), userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_explore, container, false);

        setListAdapter(mExploreItemAdapter);

        return view;

    }
}
