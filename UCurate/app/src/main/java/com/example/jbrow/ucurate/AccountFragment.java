package com.example.jbrow.ucurate;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.ListFragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class AccountFragment extends ListFragment {

    AccountItemAdapter mAccountItemAdapter;
    User user;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String userId = getArguments().getString(User.USER_ID);
        mAccountItemAdapter = new AccountItemAdapter(getActivity().getApplicationContext(), userId);
        // TODO: add when firebase functionality added


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //mUser = dataSnapshot.getValue(User.class);
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if (postSnapshot.getKey().equals(userId)) {
                        Log.d("AccountFragment", "Found: " + userId);
                        user = postSnapshot.getValue(User.class);
                    }
                    Log.d("onDataChange", "leaving onDataChanged");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//      user = FireBase.getUser(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);

        setListAdapter(mAccountItemAdapter);


//        TextView username = (TextView) view.findViewById(R.id.username);
//        username.setText(user.getName());
//
//        TextView userBio = (TextView) view.findViewById(R.id.user_bio);
//        userBio.setText(user.getBiography());
//
//        ImageView userImage = (ImageView) view.findViewById(R.id.user_image);
//        userImage.setImageBitmap(user.getUserImage());

        addTypeSpinner(view);
        //addSortBySpinner(view);
        addSearchBox(view);

        return view;
    }

    // drop down for artwork vs tours
    private void addTypeSpinner(View view) {
        Spinner typeSpinner = (Spinner) view.findViewById(R.id.item_type);
        ArrayAdapter<CharSequence> typeAdapter =
                ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                        R.array.item_types, android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        // clear all items
                        mAccountItemAdapter.clear();
                        // list only tours
                        // TODO: get tours from database;
                        List<Tour> allTours = user.getTourList();
                        mAccountItemAdapter.addAllTours(allTours);
                        return;
                    default:
                        // list only paintings
                        mAccountItemAdapter.clear();
                        List<Artwork> allArtwork = user.getArtworkList();
                        mAccountItemAdapter.addAllArtwork(allArtwork);
                        return;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    // drop down for sort by options
    private void addSortBySpinner(View view) {
        Spinner sortSpinner = (Spinner) view.findViewById(R.id.sort_by);
        ArrayAdapter<CharSequence> sortAdapter =
                ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                        R.array.sort_by_values, android.R.layout.simple_spinner_item);
        sortSpinner.setAdapter(sortAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // sort by name
                        return;
                    case 2:
                        // sort by tour
                        return;
                    default:
                        // sort by date
                        return;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void addSearchBox(View view) {
        // search box
        EditText searchBox = (EditText) view.findViewById(R.id.search_box);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAccountItemAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
