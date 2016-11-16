package com.example.jbrow.ucurate;

import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FeedItemAdapter extends BaseAdapter {

    private final List<FeedItem> mItems = new ArrayList<FeedItem>();
    private final Context mContext;

    public FeedItemAdapter(Context context) {
        mContext = context;
    }

    public void add(FeedItem feedItem) {
        mItems.add(feedItem);
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
        FeedItem feedItem = (FeedItem) getItem(pos);

        RelativeLayout itemLayout = null;

        if (convertView == null) {
            itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.feed_item, parent, false);
        } else {
            itemLayout = (RelativeLayout) convertView;
        }

        ImageView userImage = (ImageView) itemLayout.findViewById(R.id.user_image);
        userImage.setImageBitmap(feedItem.getUserImage());

        TextView updateContent = (TextView) itemLayout.findViewById(R.id.update_content);
        updateContent.setText(feedItem.getUpdate());

        ImageView updateImage = (ImageView) itemLayout.findViewById(R.id.update_image);
        updateImage.setImageBitmap(feedItem.getUpdateImage());

        return itemLayout;
    }



}
