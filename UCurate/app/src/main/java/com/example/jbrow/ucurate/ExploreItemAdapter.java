package com.example.jbrow.ucurate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.widget.BaseAdapter;

/**
 * Created by charmipatel on 11/17/16.
 */

public class ExploreItemAdapter extends BaseAdapter {

    private final List<Tour> mItems = new ArrayList<Tour>();
    private final Context mContext;

    public ExploreItemAdapter(Context context) {
        mContext = context;
    }

    public void add(Tour exploreItem) {
        mItems.add(exploreItem);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int pos) {
        return mItems.get(pos);
    }

    public long getItemId(int pos) {
        return pos;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        Tour tour = (Tour) getItem(pos);

        RelativeLayout itemLayout = null;

        if (convertView == null) {
            itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.explore_item, parent, false);
        } else {
            itemLayout = (RelativeLayout) convertView;
        }

        // TODO: find out if we need this
//        ImageView tourImage = (ImageView) itemLayout.findViewById(R.id.explore_tour_image);
//        tourImage.setImageBitmap(exploreItem.getTourImage());

        TextView tourName = (TextView) itemLayout.findViewById(R.id.explore_tour_name);
        tourName.setText(tour.getTitle());

        TextView tourDescription = (TextView) itemLayout.findViewById(R.id.explore_tour_description);
        tourDescription.setText(tour.getDescription());

        itemLayout.setOnClickListener(new View.OnClickListener() {
            // start view tour activity
            public void onClick(View view) {

            }
        });

        return itemLayout;
    }


}
