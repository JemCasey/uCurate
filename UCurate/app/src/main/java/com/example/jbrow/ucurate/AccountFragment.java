package com.example.jbrow.ucurate;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.ListFragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class AccountFragment extends ListFragment {

    AccountItemAdapter mAccountItemAdapter;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountItemAdapter = new AccountItemAdapter(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);

        setListAdapter(mAccountItemAdapter);

        //User user = null; // getUser();

//        TextView username = (TextView) view.findViewById(R.id.username);
//        username.setText(user.getName());
//
//        TextView userBio = (TextView) view.findViewById(R.id.user_bio);
//        userBio.setText(user.getBiography());


        // drop down for artwork vs tours
        Spinner typeSpinner = (Spinner) view.findViewById(R.id.item_type);
        ArrayAdapter<CharSequence> typeAdapter =
                ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.item_types, android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        // list only tours
                        return;
                    default:
                        // list only paintings
                        return;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        // drop down for sort by options
        Spinner sortSpinner = (Spinner) view.findViewById(R.id.sort_by);
        ArrayAdapter<CharSequence> sortAdapter =
                ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.sort_by_values, android.R.layout.simple_spinner_item);
        sortSpinner.setAdapter(sortAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // sort by name
                        return;
                    case 2:
                        // sort by tour
                        return;
                    default:
                        // sort by date
                        return;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        // search box
        EditText searchBox = (EditText) view.findViewById(R.id.search_box);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAccountItemAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
}
