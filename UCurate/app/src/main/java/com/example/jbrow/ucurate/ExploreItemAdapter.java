package com.example.jbrow.ucurate;

import android.content.Context;
import android.content.Intent;
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
    private final String mUserId;

    public ExploreItemAdapter(Context context, String userId) {
        mContext = context;
        mUserId = userId;
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
        final String tourId = tour.getId();

        RelativeLayout itemLayout = null;

        if (convertView == null) {
            itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.explore_item, parent, false);
        } else {
            itemLayout = (RelativeLayout) convertView;
        }

        ImageView tourImage = (ImageView) itemLayout.findViewById(R.id.explore_tour_image);

        TextView tourName = (TextView) itemLayout.findViewById(R.id.explore_tour_name);
        tourName.setText(tour.getTitle());

        TextView tourDescription = (TextView) itemLayout.findViewById(R.id.explore_tour_description);
        tourDescription.setText(tour.getDescription());

        itemLayout.setOnClickListener(new View.OnClickListener() {
            // start view tour activity
            public void onClick(View view) {
                Intent viewTourIntent = new Intent(mContext, EditTourActivity.class);
                viewTourIntent.putExtra(Tour.TOUR_ID, tourId);
                viewTourIntent.putExtra(User.USER_ID, mUserId);
                viewTourIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(viewTourIntent);
            }
        });

        return itemLayout;
    }


}
