package com.momik.karyakram.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.momik.karyakram.Fragments.nav1Fragment;
import com.momik.karyakram.Fragments.nav2Fragment;
import com.momik.karyakram.Fragments.nav3Fragment;
import com.momik.karyakram.Fragments.nav4Fragment;
import com.momik.karyakram.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
String hapUrl = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=event&op=getForMainP1";
ImageButton menuBtn, searchBtn;
TextView nav1, nav2, nav3, nav4;
TextView username;
EditText search_bar;
String id, name, role;
LinearLayout l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        menuBtn =  findViewById(R.id.menu_btn);
        search_bar = findViewById(R.id.search_bar);
        searchBtn =  findViewById(R.id.search_btn);
        username = findViewById(R.id.username);
        nav1 = findViewById(R.id.nav1);
        nav2 = findViewById(R.id.nav2);
        nav3 = findViewById(R.id.nav3);
        nav4 = findViewById(R.id.nav4);
        l1 =findViewById(R.id.thisWeek);

        if(getIntent()!=null){
            Intent oldIntent = getIntent();
            id = oldIntent.getStringExtra("id");
            name = oldIntent.getStringExtra("name");
            role = oldIntent.getStringExtra("role");
            username.setText(name);
        }

        getHappening(l1);
        loadFragment(new nav1Fragment(), 1);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, MenuActivity.class);
                i.putExtra("user_id", id);
                startActivity(i);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String param =  search_bar.getText().toString();
                Intent i = new Intent(HomeActivity.this, ResultActivity.class);
                i.putExtra("keyword", param);
                startActivity(i);
            }
        });


        nav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetNavColor();
                makeActive(nav1);
                loadFragment(new nav1Fragment(), 1);
            }
        });
        nav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetNavColor();
                makeActive(nav2);
                loadFragment(new nav2Fragment(), 0);
            }
        });
        nav3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetNavColor();
                makeActive(nav3);
                loadFragment(new nav3Fragment(), 0);
            }
        });
        nav4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetNavColor();
                makeActive(nav4);
                loadFragment(new nav4Fragment(), 0);
            }
        });

    }

    private void getHappening(LinearLayout l1) {
        RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
        JSONObject params = new JSONObject();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                hapUrl, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            if (code.equals("200")) {
                                Log.d( "onResponse: ", "Eta");
                                parseData(response.getJSONObject("message"));
                            }
                        } catch (JSONException e) {
                            Log.d( "onResponse: ", "uta");
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

    private void parseData(JSONObject obj) throws JSONException {
            TextView name = l1.findViewById(R.id.hap_name);
            TextView location = l1.findViewById(R.id.hap_location);
            TextView start = l1.findViewById(R.id.hap_start);
            TextView end = l1.findViewById(R.id.hap_end);
            TextView interested = l1.findViewById(R.id.hap_interests);
            TextView price = l1.findViewById(R.id.hap_price);
            name.setText("Event: "+obj.getString("name"));
            location.setText("Place: "+obj.getString("location"));
            start.setText("From: "+obj.getString("start"));
            end.setText("To: "+obj.getString("end"));
            interested.setText("Interested: "+obj.getString("interested"));
            price.setText("Price: "+obj.getString("price"));

            l1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent i = new Intent(HomeActivity.this, single_event_template.class);
                        i.putExtra("event_id", obj.getString("id"));
                        startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        l1.setVisibility(View.VISIBLE);

    }


    private void resetNavColor() {
        nav1.setBackgroundColor(Color.parseColor("#FFFFFF"));
        nav1.setTextColor(Color.parseColor("#272727"));
        nav2.setBackgroundColor(Color.parseColor("#FFFFFF"));
        nav2.setTextColor(Color.parseColor("#272727"));
        nav3.setBackgroundColor(Color.parseColor("#FFFFFF"));
        nav3.setTextColor(Color.parseColor("#272727"));
        nav4.setBackgroundColor(Color.parseColor("#FFFFFF"));
        nav4.setTextColor(Color.parseColor("#272727"));
    }

    private void makeActive(TextView nav) {
        nav.setBackgroundColor(Color.parseColor("#272727"));
        nav.setTextColor(Color.parseColor("#ffffff"));
    }

    public void loadFragment(Fragment fragment, int flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(flag == 1){
            ft.replace(R.id.container, fragment);
            clearBackStack(fm);
        }else{
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    public void clearBackStack(FragmentManager fragmentManager) {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}