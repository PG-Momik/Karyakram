package com.momik.karyakram.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.momik.karyakram.R;

import java.util.ArrayList;


public class nav4Fragment extends Fragment {
    LinearLayout row1_content, row2_content, row3_content;
    ArrayList<String> names;
    ArrayList<String> locations;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        names = new ArrayList<>();
        locations = new ArrayList<>();

        names.add("One");
        names.add("Two");
        names.add("Three");
        names.add("Four");
        names.add("Five");

        locations.add("X, Y");
        locations.add("Y, Z");
        locations.add("Z, A");
        locations.add("A, B");
        locations.add("B, C");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav4, container, false);
        row1_content = view.findViewById(R.id.row1);
        row2_content = view.findViewById(R.id.row2);
        row3_content = view.findViewById(R.id.row3);
        for(int i = 0; i<5; i++){
            View card =  inflater.inflate(R.layout.template_card, null);
            View card2 =  inflater.inflate(R.layout.template_card, null);
            View card3 =  inflater.inflate(R.layout.template_card, null);

            TextView name = card.findViewById(R.id.event_name);
            TextView location = card.findViewById(R.id.event_location);

            TextView name2 = card.findViewById(R.id.event_name);
            TextView location2 = card.findViewById(R.id.event_location);

            TextView name3 = card.findViewById(R.id.event_name);
            TextView location3 = card.findViewById(R.id.event_location);

            name.setText(names.get(i));
            location.setText(locations.get(i));
            row1_content.addView(card);

            name2.setText(names.get(i));
            location2.setText(locations.get(i));
            row2_content.addView(card2);

            name3.setText(names.get(i));
            location3.setText(locations.get(i));
            row3_content.addView(card3);

        }

        return view;
    }


}