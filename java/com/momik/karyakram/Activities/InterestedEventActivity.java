package com.momik.karyakram.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.momik.karyakram.Adapters.resEvListAdapter;
import com.momik.karyakram.Models.EventInfo;
import com.momik.karyakram.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class InterestedEventActivity extends AppCompatActivity {
    String myInterestsUrl = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=user&op=getMyInterests";
    String user_id;
    ArrayList<EventInfo> list;
    ListView listView;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;
    String month;
    String day;
    String[] separated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_interested_event);
        Intent oldIntent = getIntent();
        listView = findViewById(R.id.interestedListView);
        user_id =  oldIntent.getStringExtra("user_id");

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date=simpleDateFormat.format(calendar.getTime());
        getMyInterests(user_id);
    }

    private void getMyInterests(String user_id) {
        RequestQueue queue = Volley.newRequestQueue(InterestedEventActivity.this);
        JSONObject params = new JSONObject();
        try {
            params.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                myInterestsUrl, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            if (code.equals("200")) {
                                Log.d("Message", response.getJSONArray("message").toString());
                                parseData(response.getJSONArray("message"));
                            }
                        } catch (JSONException e) {
                            Log.d("Message: ", "inside catch");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "error: ", "inside volley error");
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
    private void parseData(JSONArray message) throws JSONException {
        list = new ArrayList<>();
        int size = message.length();
        Log.d("length: ", String.valueOf(size));
        Log.d("parseData: ", "Bruhhh");
        for (int i = 0; i < size; i++) {
            Log.d( "parseData: ", "loop");
            try {
                JSONObject obj = message.getJSONObject(i);
                EventInfo info = new EventInfo();
                info.setId(obj.getString("id"));
                info.setName(obj.getString("name"));
                info.setStart(obj.getString("start_date"));
                info.setEnd(obj.getString("end_date"));
                info.setDeadline(obj.getString("deadline"));
                info.setLocation(obj.getString("location"));
                info.setDesc(obj.getString("detail"));
                info.setInterested(obj.getString("interested"));
                info.setPrice(obj.getString("price"));
                info.setAttendence(obj.getString("attendence"));
                info.setToday(Date);
                list.add(info);
            } catch (JSONException e) {
                Log.d( "parseData: ", "Eta");
                e.printStackTrace();
            }
        }
        listView.setAdapter(new resEvListAdapter(InterestedEventActivity.this, list));
    }

}
