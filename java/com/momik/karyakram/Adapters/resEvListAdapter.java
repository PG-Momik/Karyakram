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

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.momik.karyakram.Activities.single_event_template;
import com.momik.karyakram.Models.EventInfo;
import com.momik.karyakram.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class resEvListAdapter extends ArrayAdapter<EventInfo> {
    Context context;
    SimpleDateFormat sdf;
    Date d1, d2;

    public resEvListAdapter(@NonNull Context context, ArrayList<EventInfo> list) {
        super(context,0,list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent){

        View view= LayoutInflater.from(getContext()).inflate(R.layout.res_single_list,null);
        LinearLayout l1 = view.findViewById(R.id.listBox);

        TextView name = view.findViewById(R.id.event_name);
        TextView location= view.findViewById(R.id.event_location);
        TextView price = view.findViewById(R.id.event_price);
        TextView start = view.findViewById(R.id.event_start);
        TextView end = view.findViewById(R.id.event_end);
        TextView interests = view.findViewById(R.id.event_interests);

        EventInfo info = getItem(position);
        Log.d( "inside adapter: ", String.valueOf(position));
        name.setText("Name: "+info.getName());
        location.setText("Place: "+info.getLocation());
        price.setText("Price: "+info.getPrice());
        start.setText("From: "+info.getStart());
        end.setText("To: "+info.getEnd());
        interests.setText("Interested: "+info.getInterested());
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            d1 = sdf.parse(info.getToday());
            d2 = sdf.parse(info.getEnd());
            Log.d( "D1: ", info.getToday());
            Log.d( "D2: ", info.getEnd());
            if(d2.after(d1) && info.getAttendence().equals("0")){
                l1.setBackgroundResource(R.drawable.shape_green);
            }else if(d2.after(d1) && info.getAttendence().equals("1")){
                l1.setBackgroundResource(R.drawable.shape_red);
            }else{
                l1.setBackgroundResource(R.drawable.shape_blue);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
