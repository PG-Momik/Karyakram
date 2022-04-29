package com.momik.karyakram.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.momik.karyakram.Activities.single_event_template;
import com.momik.karyakram.Models.EventInfo;
import com.momik.karyakram.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class inSnEvListAdapter extends ArrayAdapter<EventInfo> {
    Context context;



    public inSnEvListAdapter(@NonNull Context context, ArrayList<EventInfo> list) {
        super(context,0,list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent){

        View view= LayoutInflater.from(getContext()).inflate(R.layout.sn_ev_single_list,null);
        LinearLayout l1 = view.findViewById(R.id.listBox);

        TextView name = view.findViewById(R.id.event_name);
        TextView location= view.findViewById(R.id.event_location);
        TextView price = view.findViewById(R.id.event_price);
        TextView start = view.findViewById(R.id.event_start);
        TextView end = view.findViewById(R.id.event_end);
        TextView interests = view.findViewById(R.id.event_interests);

        EventInfo info = getItem(position);

        name.setText("Name: "+info.getName());
        location.setText("Place: "+info.getLocation());
        price.setText("Price: "+info.getPrice());
        start.setText("From: "+info.getStart());
        end.setText("To: "+info.getEnd());
        interests.setText("Interested: "+info.getInterested());





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
