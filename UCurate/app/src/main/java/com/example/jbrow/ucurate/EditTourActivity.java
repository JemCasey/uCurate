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
    Tour tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tour);
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String tourName = intent.getStringExtra("tour_name");
        String tourDescription = intent.getStringExtra("tour_description");
        tour = null;
        if (tourName == null) {
            saved = true;
            String tourID = intent.getStringExtra("tour_id");
            //Todo: use FireBase method to get tour from tour_id
        }
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
                    //Todo FireBase.editTour()
                }
            });
        }

        viewTour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTourActivity.this, ViewTourActivity.class);
                intent.putExtra("tour_id", tour.getId());
                startActivity(intent);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_ARTWORKS && resultCode == RESULT_OK) {
            //ArrayList<Artwork> artworks = new ArrayList<>();
            ArrayList<String> parcelables = data.getStringArrayListExtra("artworks");
            for (String curr : parcelables) {
                //Todo get artwork from id
                Artwork temp = null;
                listAdapter.addItem(listAdapter.getItemCount(),temp);
            }
            saved = false;
        }
    }

}
