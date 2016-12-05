package com.example.jbrow.ucurate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FeedItemAdapter extends BaseAdapter {

    private final List<Artwork> mItems = new ArrayList<Artwork>();
    private final Context mContext;
    private final String mUserId;

    public FeedItemAdapter(Context context, String userId) {
        mContext = context;
        mUserId = userId;
    }

    public void add(Artwork item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<Artwork> items) {
        mItems.addAll(items);
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
        final Object item = (Object) getItem(pos);

        String userId = null;
        String title = null;
        String description = null;
        Bitmap image = null;

        if (item instanceof Artwork) {
            userId = ((Artwork) item).getUserID();
            image = ((Artwork) item).getImage();
            title = ((Artwork) item).getTitle();
            description = ((Artwork) item).getDescription();
        } else if (item instanceof Tour) {
            userId = ((Artwork) item).getUserID();
            image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_place_black_24dp);
            title = ((Tour) item).getTitle();
            description = ((Tour) item).getDescription();
        } else {
            // TODO: so hacky..is there a better way?
            Toast.makeText(mContext, "Help", Toast.LENGTH_LONG);
        }

        RelativeLayout itemLayout = null;

        if (convertView == null) {
            itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.feed_item, parent, false);
        } else {
            itemLayout = (RelativeLayout) convertView;
        }

        //User user = FireBase.getUser(userId);
        User user = DummyData.getUser(mContext);


        ImageView userImage = (ImageView) itemLayout.findViewById(R.id.user_image);
        userImage.setImageBitmap(user.getUserImage());

//        userImage.setOnClickListener(new View.OnClickListener() {
//            // start view account activity
//            public void onClick(View view) {
//
//            }
//        });

        final int finalpos = pos;

        TextView updateTitle = (TextView) itemLayout.findViewById(R.id.update_title);
        updateTitle.setText(title);

        updateTitle.setOnClickListener(new View.OnClickListener() {
            // start view tour/view art activity
            public void onClick(View view) {
                startViewItem(item, finalpos);
            }
        });

        TextView updateContent = (TextView) itemLayout.findViewById(R.id.update_description);
        updateContent.setText(description);

        updateContent.setOnClickListener(new View.OnClickListener() {
            // start view tour/view art activity
            public void onClick(View view) {
                startViewItem(item, finalpos);
            }
        });

        ImageView updateImage = (ImageView) itemLayout.findViewById(R.id.update_image);
        updateImage.setImageBitmap(image);

        updateImage.setOnClickListener(new View.OnClickListener() {
            // start view tour/view art activity
            public void onClick(View view) {
                startViewItem(item, finalpos);
            }
        });

        return itemLayout;
    }

    private void startViewItem(Object item, int pos) {
        if (item instanceof Artwork) {
            //String artworkId = ((Artwork) item).getId();
            Intent viewArtworkIntent = new Intent(mContext, ViewArtActivity.class);
            viewArtworkIntent.putExtra(Artwork.ARTWORK_ID, pos);
            mContext.startActivity(viewArtworkIntent);
        } else if (item instanceof Tour) {
            //String tourId = ((Tour) item).getId();
            Intent viewTourIntent = new Intent(mContext, ViewTourActivity.class);
            viewTourIntent.putExtra(Tour.TOUR_ID, pos);
            viewTourIntent.putExtra(User.USER_ID, mUserId);
            mContext.startActivity(viewTourIntent);
        } else {
            // TODO: so hacky..is there a better way?
            Toast.makeText(mContext, "Help", Toast.LENGTH_LONG);
        }
    }


}
