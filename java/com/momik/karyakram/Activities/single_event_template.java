package com.momik.karyakram.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.momik.karyakram.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class single_event_template extends AppCompatActivity {
    String eid;
    String url = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=event&op=getEventById";
    TextView event_name, event_location, event_cost, event_start, event_end, event_desc, event_poster, event_interested, event_organizer;
    Button interestedBtn, goingBtn, bookedBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event_template);
        Intent old_intent = getIntent();
        eid = old_intent.getStringExtra("event_id");
        event_name = findViewById(R.id.event_name);
        event_location = findViewById(R.id.event_location);
        event_cost = findViewById(R.id.event_cost);
        event_start = findViewById(R.id.event_start);
        event_end = findViewById(R.id.event_end);
        event_desc = findViewById(R.id.event_desc);
        event_poster = findViewById(R.id.event_poster);
        event_interested = findViewById(R.id.event_interests);
        event_organizer = findViewById(R.id.event_organizers);

        interestedBtn = findViewById(R.id.interested);
        goingBtn = findViewById(R.id.going);
        bookedBtn = findViewById(R.id.book);


        getEventById(eid);

    }

    private void getEventById(String parameter) {
        RequestQueue queue = Volley.newRequestQueue(single_event_template.this);
        JSONObject params = new JSONObject();
        try {
            params.put("id", parameter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            if (code.equals("200")) {
                                Log.d("Message", response.getJSONObject("message").toString());
                                parseData(response.getJSONObject("message"));
                                }
                        } catch (JSONException e) {
                            Log.d("Message: ", "inside catch");
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

    private void parseData(JSONObject message) throws JSONException {
        event_name.setText(message.getString("name"));
        event_location.setText(message.getString("location"));
        if(message.getString("price").equals("0")){
            event_cost.setText("FREE") ;
        }else{
            event_cost.setText(message.getString("price"));
        }
        event_start.setText(message.getString("start_date")) ;
        event_end.setText(message.getString("end_date")) ;
        event_desc.setText(message.getString("detail"));
        event_interested.setText(message.getString("interested"));
        event_organizer.setText(message.getString("organizer"));
        event_poster.setText(message.getString("username"));

        final int n = new Random().nextInt((3-1)+1)+1;
        switch (n){
            case 1:
                interestedBtn.setVisibility(View.VISIBLE);
                break;
            case 2:
                goingBtn.setVisibility(View.VISIBLE);
                break;
            case 3:
                bookedBtn.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

    }
}