package com.momik.karyakram.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.momik.karyakram.Adapters.nav2ListAdapter;
import com.momik.karyakram.Models.EventInfo;
import com.momik.karyakram.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class nav2Fragment extends Fragment {
    ArrayList<EventInfo> list;
    String url = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=event&op=getForFree";
    ListView listView;
    ProgressBar pb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav2, container, false);
//        list = new ArrayList<EventInfo>();
        listView = view.findViewById(R.id.frag2ListView);
        pb =view.findViewById(R.id.progressBar);

        getEventForFree(inflater);
        return view;
    }

    private void getEventForFree(LayoutInflater inflater) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JSONObject params = new JSONObject();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            if (code.equals("200")) {
                                pb.setVisibility(View.GONE);
                                Log.d("Response: ", "Inside try");
                                parseData(response.getJSONArray("message"), inflater);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Response: ", "Inside catch");
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

    private void parseData(JSONArray message, LayoutInflater inflater) {
        list = new ArrayList<EventInfo>();
        int size = message.length();
        for (int i = 0; i < size; i++) {
            try {
                JSONObject obj = message.getJSONObject(i);
                EventInfo info = new EventInfo();
                info.setId(obj.getString("id"));
                info.setName(obj.getString("name"));
                info.setLocation(obj.getString("location"));
                info.setInterested(obj.getString("interested"));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("parseData: ", listView.toString());
        listView.setAdapter(new nav2ListAdapter(getContext(), list));
    }

}