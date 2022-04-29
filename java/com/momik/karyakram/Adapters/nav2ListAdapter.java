package com.momik.karyakram.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.momik.karyakram.Activities.single_event_template;
import com.momik.karyakram.Models.EventInfo;
import com.momik.karyakram.R;

import java.util.ArrayList;
import java.util.List;

public class nav2ListAdapter extends ArrayAdapter<EventInfo> {
    Context context;

    public nav2ListAdapter(@NonNull Context context, ArrayList<EventInfo> list) {
        super(context,0,list);
        this.context = context;
    }

//    Context context;

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent){

        View view= LayoutInflater.from(getContext()).inflate(R.layout.nav2_single_list,null);

        TextView name = view.findViewById(R.id.event_name);
        TextView location= view.findViewById(R.id.event_location);
        TextView interested = view.findViewById(R.id.event_interests);
        ImageView img = view.findViewById(R.id.event_image);


        EventInfo info = getItem(position);
        Log.d( "inside adapter: ", String.valueOf(position));
        name.setText(info.getName());
        location.setText(info.getLocation());
        interested.setText(info.getInterested());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, single_event_template.class);
                i.putExtra("event_id", info.getId());
                context.startActivity(i);
            }
        });

        return view;
    }


}