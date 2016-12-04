package com.example.jbrow.ucurate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;

public class EditTourActivity extends Activity {
    private static final int GET_ARTWORKS = 1;
    DragListView mDragListView;
    ArrayList<Artwork> mItemArray;
    TourItemAdapter listAdapter;
    boolean saved = false;
    String userID;
    Tour tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tour);
        Intent intent = getIntent();
        userID = intent.getStringExtra(User.USER_ID);
        String tourName = intent.getStringExtra(Tour.TOUR_NAME);
        String tourDescription = intent.getStringExtra(Tour.TOUR_DESCRIPTION);
        tour = null;
        TextView titleText = (TextView) findViewById(R.id.edit_tour_name);
        TextView descriptionText = (TextView) findViewById(R.id.edit_tour_description);

        if (tourName == null) {
            saved = true;
            String tourID = intent.getStringExtra("tour_id");
            Tour tour = FireBase.getTour(userID, tourID);
            titleText.setText(tour.getTitle());
            descriptionText.setText(tour.getDescription());
        } else {
            titleText.setText(tourName);
            descriptionText.setText(tourDescription);
        }

        mDragListView = (DragListView) findViewById(R.id.drag_list_view);
        mDragListView.setDragListListener(new DragListView.DragListListener() {
            @Override
            public void onItemDragStarted(int position) {
            }

            @Override
            public void onItemDragging(int itemPosition, float x, float y) {

            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                tour.setArtworkList(new ArrayList(listAdapter.getItemList()));

            }
        });
        if (tour != null) {
            mItemArray = tour.getArtworkList();
        } else {
            mItemArray = new ArrayList();
        }
        //mItemArray.add(new Artwork("Rhein II", "This is by Andreas Gursky.",new LatLng(38.990633, -76.949384), BitmapFactory.decodeResource(getResources(),R.drawable.rheinii)));
        //mItemArray.add(new Artwork("Germany's Spiritual Heroes", "this is by Anselm Kiefer", new LatLng(38.992884, -76.948417),BitmapFactory.decodeResource(getResources(),R.drawable.spiritualheroes)));
        mDragListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listAdapter = new TourItemAdapter(mItemArray, R.layout.tour_item, R.id.artwork_image, false);
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setCanDragHorizontally(false);

        Button addArt = (Button) findViewById(R.id.add_art_to_tour);
        Button viewTour = (Button) findViewById(R.id.view_tour);
        Button saveTour = (Button) findViewById(R.id.save_tour);

        //Don't allow editing of list if user did not create
        if (tour.getUserID() != userID) {
            mDragListView.setDragEnabled(false);
            addArt.setVisibility(View.GONE);
            saveTour.setVisibility(View.GONE);
            TextView instructions = (TextView) findViewById(R.id.drag_instructions);
            instructions.setVisibility(View.GONE);
            saved = true;
        } else {
            addArt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EditTourActivity.this, AddToTourActivity.class);
                    startActivityForResult(intent, GET_ARTWORKS);
                }
            });
            saveTour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saved = true;
                    //FireBase.changeUserTour(userID, tour.getId(), tour.getArtworkList());
                }
            });
        }

        viewTour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (saved == false) {
                    Toast.makeText(getApplicationContext(), "Please save the tour before viewing on map", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(EditTourActivity.this, ViewTourActivity.class);
                    intent.putExtra(Tour.TOUR_ID, tour.getId());
                    intent.putExtra(User.USER_ID, userID);
                    startActivity(intent);
                }

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_ARTWORKS && resultCode == RESULT_OK) {
            //ArrayList<Artwork> artworks = new ArrayList<>();
            ArrayList<String> artworks = data.getStringArrayListExtra("artworks");
            for (String curr : artworks) {
                //get artwork from id
                Artwork temp = FireBase.getArtWork(userID, curr);
                listAdapter.addItem(listAdapter.getItemCount(),temp);
            }
            tour.setArtworkList(new ArrayList(listAdapter.getItemList()));
            saved = false;
        }
    }

}
