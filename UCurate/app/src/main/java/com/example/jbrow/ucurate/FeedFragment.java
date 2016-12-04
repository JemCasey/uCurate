package com.example.jbrow.ucurate;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;


public class FeedFragment extends ListFragment {

    FeedItemAdapter mFeedItemAdapter;
    private SwipeRefreshLayout swipeDownContainer;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userId = getArguments().getString(User.USER_ID);

        mFeedItemAdapter = new FeedItemAdapter(getActivity().getApplicationContext(), userId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        // TODO: get data from database
//        List<FeedItem> data = null; // get data
//        mFeedItemAdapter.addAll(data);
        setListAdapter(mFeedItemAdapter);

        swipeDownContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeDownContainer);
        swipeDownContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // get new data
                // TODO: get data from database
                List<FeedItem> newData = null; // get data
                mFeedItemAdapter.clear();
                //mFeedItemAdapter.addAll(newData);
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
