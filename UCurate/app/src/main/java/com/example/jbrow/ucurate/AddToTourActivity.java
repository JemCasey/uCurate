package com.example.jbrow.ucurate;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

public class AddToTourActivity extends AppCompatActivity {
    ArtAdapter dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_tour);
        Intent intent = getIntent();
        String userID = intent.getStringExtra(User.USER_ID);

        //Todo use Firebase method to get all artworks

        ArrayList<Artwork> artworks = FireBase.getUserArtwork(User.USER_ID);

        //    artworks.add(new Artwork("Betty", "this is by Gerhard Richter", new LatLng(38.984929, -76.947760)/*,BitmapFactory.decodeResource(getResources(),R.drawable.betty)*/));
        //    artworks.add(new Artwork("Higher Beings Commanded: Paint the Upper-Right Corner Black!", "this is by Sigmar Polke", new LatLng(38.99, -76.947760)/*,BitmapFactory.decodeResource(getResources(),R.drawable.betty)*/));


        dataAdapter = new ArtAdapter(this, R.layout.tour_item, artworks);
        ListView listView = (ListView) findViewById(R.id.listView1);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        Button button = (Button) findViewById(R.id.addSelected);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ArrayList<Artwork> artList = dataAdapter.artList;
                ArrayList<String> selectedArt = new ArrayList();
                for(int i=0;i < artList.size();i++){
                    Artwork artwork = artList.get(i);
                    Boolean isSelected = dataAdapter.isSelected.get(artwork.getTitle());
                    if(isSelected != null && isSelected.booleanValue() == true){
                        selectedArt.add(artwork.getId());
                    }
                }

                Intent data = new Intent();
                data.putStringArrayListExtra("artworks", selectedArt);
                try {
                    setResult(RESULT_OK, data);
                    finish();
                } catch (Exception e) {
                }

            }
        });

    }

    private class ArtAdapter extends ArrayAdapter<Artwork> {

        private ArrayList<Artwork> artList;
        private HashMap<String, Boolean> isSelected;

        public ArtAdapter(Context context, int viewResourceId,
                          ArrayList<Artwork> artList) {
            super(context, viewResourceId, artList);
            this.artList = new ArrayList<Artwork>();
            isSelected = new HashMap();
            this.artList.addAll(artList);
        }

        private class ViewHolder {
            ImageView image;
            TextView title;
            CheckBox box;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.add_to_tour_item, null);

                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.artwork_title);
                holder.box = (CheckBox) convertView.findViewById(R.id.checkbox);
                holder.image = (ImageView) convertView.findViewById(R.id.artwork_image);
                convertView.setTag(holder);

                holder.box.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Artwork artwork = (Artwork) cb.getTag();
                        /*Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/
                        isSelected.put(artwork.getTitle(),cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Artwork art = artList.get(position);
            holder.title.setText(" (" +  art.getTitle() + ")");
            holder.image.setImageBitmap(art.getImage());
            holder.box.setChecked(false);
            holder.box.setTag(art);

            return convertView;

        }

    }

}
