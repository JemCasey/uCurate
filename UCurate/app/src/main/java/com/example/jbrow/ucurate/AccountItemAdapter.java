package com.example.jbrow.ucurate;

import android.accounts.Account;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charmipatel on 11/29/16.
 */

public class AccountItemAdapter extends BaseAdapter implements Filterable {
    private final List<AccountItem> mItems = new ArrayList<AccountItem>(); // all items
    private List<AccountItem> mDisplayItems = new ArrayList<AccountItem>(); // displayed items
    private final Context mContext;

    public AccountItemAdapter(Context context) {
        mContext = context;
    }

    public void add(AccountItem accountItem) {
        mItems.add(accountItem);
        mDisplayItems.add(accountItem);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        mDisplayItems.clear();
        notifyDataSetChanged();
    }

    public int getCount() {
        return mDisplayItems.size();
    }

    public Object getItem(int pos) {
        return mDisplayItems.get(pos);
    }

    public long getItemId(int pos) {
        return pos;
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        AccountItem accountItem = (AccountItem) getItem(pos);

        RelativeLayout itemLayout = null;

        if (convertView == null) {
            itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.account_item, parent, false);
        } else {
            itemLayout = (RelativeLayout) convertView;
        }

        ImageView image = (ImageView) itemLayout.findViewById(R.id.account_item_image);
        image.setImageBitmap(accountItem.getImage());

        TextView name = (TextView) itemLayout.findViewById(R.id.account_item_name);
        name.setText(accountItem.getName());

        TextView description = (TextView) itemLayout.findViewById(R.id.account_item_description);
        description.setText(accountItem.getDescription());

        itemLayout.setOnClickListener(new View.OnClickListener() {
            // start view image/tour activity
            public void onClick(View view) {

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

                List<AccountItem> filteredList = new ArrayList<AccountItem>();

                int itemCount = mItems.size();

                for (int i = 0; i < itemCount; i++) {
                    AccountItem current = mItems.get(i);

                    String name = current.getName().toLowerCase();
                    String description = current.getDescription().toLowerCase();

                    if (name.contains(filterString) || description.contains(filterString)) {
                        filteredList.add(current);
                    }
                }

                filterResults.values = filteredList;
                filterResults.count = filteredList.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDisplayItems = (ArrayList<AccountItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
