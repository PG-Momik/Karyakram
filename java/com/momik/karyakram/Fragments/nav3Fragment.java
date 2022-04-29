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
import com.momik.karyakram.Adapters.nav3ListAdapter;
import com.momik.karyakram.Models.EventInfo;
import com.momik.karyakram.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class nav3Fragment extends Fragment {
    ArrayList<EventInfo> list;
    String url = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=event&op=getForPaid";
    ListView listView;
    ProgressBar pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav3, container, false);
        listView = view.findViewById(R.id.frag3ListView);
        pb =view.findViewById(R.id.progressBar);

        getEventForPaid(inflater);
        return view;
    }

    private void getEventForPaid(LayoutInflater inflater) {
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
                info.setPrice(obj.getString("price"));
                list.add(info);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        listView.setAdapter(new nav3ListAdapter(getContext(), list));
    }

}