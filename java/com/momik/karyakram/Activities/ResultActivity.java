package com.momik.karyakram.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.momik.karyakram.Adapters.inSnEvListAdapter;
import com.momik.karyakram.Adapters.resEvListAdapter;
import com.momik.karyakram.Models.EventInfo;
import com.momik.karyakram.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    String url = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=event&op=getEventFromSearch";
    String search_parameter;
    ArrayList<EventInfo> list;
    ListView listView;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_res);
        search_parameter = getIntent().getStringExtra("keyword");
        listView = findViewById(R.id.resultListView);
        pb = findViewById(R.id.progressBar);
        getEventFromKeyword(search_parameter);
    }
    private void getEventFromKeyword(String parameter) {
        RequestQueue queue = Volley.newRequestQueue(ResultActivity.this);
        JSONObject params = new JSONObject();
        try {
            params.put("keyword", parameter);
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
                                pb.setVisibility(View.INVISIBLE);
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

    private void parseData(JSONArray message) {
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
                info.setUser(obj.getString("username"));
                info.setPrice(obj.getString("price"));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        listView.setAdapter(new inSnEvListAdapter(ResultActivity.this, list));
    }
}