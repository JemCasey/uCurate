package com.example.jbrow.ucurate;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charmipatel on 11/29/16.
 */

public class AccountItemAdapter extends BaseAdapter implements Filterable {
    private List<Object> mItems = new ArrayList<Object>(); // all items
    //private List<AccountItem> mDisplayItems = new ArrayList<AccountItem>(); // displayed items
    private final Context mContext;
    private final String mUserId;

    public AccountItemAdapter(Context context, String userId) {
        mContext = context;
        mUserId = userId;
    }

    public void add(Object item) {
        mItems.add(item);
//        mDisplayItems.add(accountItem);
        notifyDataSetChanged();
    }

    public void addAll(List<Object> items) {
//        mDisplayItems.addAll(accountItems);
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void addAllTours(List<Tour> tourItems) {
        for (int i = 0; i < tourItems.size(); i++) {
            mItems.add((Object) tourItems.get(i));
        }
        notifyDataSetChanged();
    }

    public void addAllArtwork(List<Artwork> artworkItems) {
        for (int i = 0; i < artworkItems.size(); i++) {
            mItems.add((Object) artworkItems.get(i));
        }
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
//        mDisplayItems.clear();
        notifyDataSetChanged();
    }

    public int getCount() {
//        return mDisplayItems.size();
        return mItems.size();
    }

    public Object getItem(int pos) {
//        return mDisplayItems.get(pos);
        return mItems.get(pos);
    }

    public long getItemId(int pos) {
        return pos;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        final Object item = (Object) getItem(pos);

        Bitmap image = null;
        String title = null;
        String description = null;

        if (item instanceof Artwork) {
            image = ((Artwork) item).getImage();
            title = ((Artwork) item).getTitle();
            description = ((Artwork) item).getDescription();
        } else if (item instanceof Tour) {
            image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_place_black_24dp);
            title = ((Tour) item).getTitle();
            description = ((Tour) item).getDescription();
        } else {
            // TODO: so hacky..is there a better way?
            Toast.makeText(mContext, "Help", Toast.LENGTH_LONG);
        }

        RelativeLayout itemLayout = null;

        if (convertView == null) {
            itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.account_item, parent, false);
        } else {
            itemLayout = (RelativeLayout) convertView;
        }

        ImageView imageView = (ImageView) itemLayout.findViewById(R.id.account_item_image);
        imageView.setImageBitmap(image);

        TextView titleView = (TextView) itemLayout.findViewById(R.id.account_item_name);
        titleView.setText(title);

        TextView descriptionView = (TextView) itemLayout.findViewById(R.id.account_item_description);
        descriptionView.setText(description);

        itemLayout.setOnClickListener(new View.OnClickListener() {
            // start view image/tour activity
            public void onClick(View view) {
                if (item instanceof Artwork) {
                    String artworkId = ((Artwork) item).getId();
                    Intent viewArtworkIntent = new Intent(mContext, ViewArtActivity.class);
                    viewArtworkIntent.putExtra(Artwork.ARTWORK_ID, artworkId);
                    mContext.startActivity(viewArtworkIntent);
                } else if (item instanceof Tour) {
                    String tourId = ((Tour) item).getId();
                    Intent viewTourIntent = new Intent(mContext, ViewTourActivity.class);
                    viewTourIntent.putExtra(Tour.TOUR_ID, tourId);
                    mContext.startActivity(viewTourIntent);
                } else {
                    // TODO: so hacky..is there a better way?
                    Toast.makeText(mContext, "Help", Toast.LENGTH_LONG);
                }
            }
        });

        return itemLayout;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String filterString = constraint.toString().toLowerCase();
                FilterResults filterResults = new FilterResults();

                List<Object> filteredList = new ArrayList<Object>();

                int itemCount = mItems.size();

                for (int i = 0; i < itemCount; i++) {
                    Object current = mItems.get(i);

                    String title = null;
                    String description = null;

                    if (current instanceof Artwork) {
                        title = ((Artwork) current).getTitle().toLowerCase();
                        description = ((Artwork) current).getDescription().toLowerCase();
                    } else if (current instanceof Tour) {
                        title = ((Tour) current).getTitle();
                        description = ((Tour) current).getDescription();
                    } else {
                        // TODO: so hacky..is there a better way?
                        Toast.makeText(mContext, "Help", Toast.LENGTH_LONG);
                    }

                    if (title.contains(filterString) || description.contains(filterString)) {
                        filteredList.add(current);
                    }
                }

                filterResults.values = filteredList;
                filterResults.count = filteredList.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mItems = (ArrayList<Object>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
