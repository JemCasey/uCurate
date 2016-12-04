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
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;

public class EditTourActivity extends Activity {
    private static final int GET_ARTWORKS = 1;
    DragListView mDragListView;
    ArrayList<Artwork> mItemArray;
    TourItemAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tour);
        mDragListView = (DragListView) findViewById(R.id.drag_list_view);
        mDragListView.setDragListListener(new DragListView.DragListListener() {
            @Override
            public void onItemDragStarted(int position) {
                Toast.makeText(getApplicationContext(), "Start - position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragging(int itemPosition, float x, float y) {

            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                if (fromPosition != toPosition) {
                    Toast.makeText(getApplicationContext(), "End - position: " + toPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Todo: get tour from FireBase
        Tour tour = new Tour("blah", "blah2", "foo");
        mItemArray = tour.getArtworkList();
        //mItemArray.add(new Artwork("Rhein II", "This is by Andreas Gursky.",new LatLng(38.990633, -76.949384), BitmapFactory.decodeResource(getResources(),R.drawable.rheinii)));
        //mItemArray.add(new Artwork("Germany's Spiritual Heroes", "this is by Anselm Kiefer", new LatLng(38.992884, -76.948417),BitmapFactory.decodeResource(getResources(),R.drawable.spiritualheroes)));
        mDragListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listAdapter = new TourItemAdapter(mItemArray, R.layout.tour_item, R.id.artwork_image, false);
        mDragListView.setAdapter(listAdapter, true);
        mDragListView.setCanDragHorizontally(false);
        if (tour.getUserID() != "foo") {
            mDragListView.setDragEnabled(false);
        }
        Button addArt = (Button) findViewById(R.id.add_art_to_tour);
        addArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTourActivity.this, AddToTourActivity.class);
                startActivityForResult(intent, GET_ARTWORKS);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_ARTWORKS && resultCode == RESULT_OK) {
            //ArrayList<Artwork> artworks = new ArrayList<>();
            ArrayList<Parcelable> parcelables = data.getParcelableArrayListExtra("artworks");
            for (Parcelable curr : parcelables) {
                Artwork temp = (Artwork) curr;
                listAdapter.addItem(listAdapter.getItemCount(),temp);
            }
        }
    }

}
