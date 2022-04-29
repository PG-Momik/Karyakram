package com.momik.karyakram.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.momik.karyakram.Activities.single_event_template;
import com.momik.karyakram.Activities.xyz;
import com.momik.karyakram.Models.EventInfo;
import com.momik.karyakram.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class nav1Fragment extends Fragment {
    LinearLayout row1_content, row2_content, row3_content;
    TextView t1, t2, t3;
    TextView viewAll;
    ArrayList<ArrayList<EventInfo>> events = new ArrayList<ArrayList<EventInfo>>();
    ArrayList<EventInfo> temp;
    ProgressBar pb;
    //    String url = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=event&op=getForAll";
    String getPopularUrl = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=event&op=getForMainP2";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav1, container, false);

        row1_content = view.findViewById(R.id.row1);
        viewAll = view.findViewById(R.id.viewAll);
        t1 = view.findViewById(R.id.row1_header);
        row2_content = view.findViewById(R.id.row2);
        t2 = view.findViewById(R.id.row2_header);
        row3_content = view.findViewById(R.id.row3);
        t3 = view.findViewById(R.id.row3_header);
        pb = view.findViewById(R.id.progressBar);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), xyz.class);
                startActivity(i);
            }
        });



        getEventForAll(inflater);

        return view;
    }

    private void getEventForAll(LayoutInflater inflater) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JSONObject params = new JSONObject();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getPopularUrl, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            if (code.equals("200")) {
                                pb.setVisibility(View.GONE);
                                JSONArray message = response.getJSONArray("message");
                                parseData(message, inflater);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(jsonObjReq);
    }


    public void parseData(JSONArray message, LayoutInflater inflater) throws JSONException {
        int size = message.length();

        for (int i = 0; i < size; i++) {
            JSONObject obj = message.getJSONObject(i);
            View card = inflater.inflate(R.layout.template_card, null);
            TextView name = card.findViewById(R.id.event_name);
            TextView location = card.findViewById(R.id.event_location);
            name.setText(obj.getString("name"));
            location.setText(obj.getString("location"));
            row1_content.addView(card);
            t1.setVisibility(View.VISIBLE);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent i = new Intent(getContext(), single_event_template.class);
                        i.putExtra("event_id", obj.getString("id"));
                        startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


//            JSONArray row = message.getJSONArray(i);
//            temp = new ArrayList<>();
//            int number_of_cards = row.length();
//            for (int j = 0; j < number_of_cards; j++) {
//                JSONObject obj = row.getJSONObject(j);
//                EventInfo info = new EventInfo();
//                info.setId(obj.getString("id"));
//                info.setName(obj.getString("name"));
//                info.setLocation(obj.getString("location"));
//                temp.add(info);
//            }
//            events.add(temp);
//        }

//        for(int i=0; i<events.size();i++){
//            for(int j = 0; j<events.get(i).size();j++){
//                View card =  inflater.inflate(R.layout.template_card, null);
//                TextView name = card.findViewById(R.id.event_name);
//                TextView location = card.findViewById(R.id.event_location);
//                name.setText(events.get(i).get(j).getName());
//                location.setText(events.get(i).get(j).getLocation());
//                int finalJ = j;
//                int finalI = i;
//                card.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent i = new Intent(getContext(), single_event_template.class);
//                        i.putExtra("event_id", events.get(finalI).get(finalJ).getId());
//                        startActivity(i);
//                    }
//                });
//                if (i==0) {
//                    row1_content.addView(card);
//                    t1.setVisibility(View.VISIBLE);
//                }
//            }
//        }
    }


}
