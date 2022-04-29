package com.momik.karyakram.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.momik.karyakram.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    String userDetailsUls = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=user&op=getUserById";

    ImageView myEventsBtn, editProfileBtn, rateBtn;
    TextView full_name, status;
    TextInputEditText name, email, phone, role, created, black;
    ProgressBar pb ;
    LinearLayout prof_cont;
    Button updateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profile);
        myEventsBtn = findViewById(R.id.myEventsBtn);
        full_name = findViewById(R.id.full_name);
        status = findViewById(R.id.status);
        editProfileBtn = findViewById(R.id.editProfile_btn);
        pb = findViewById(R.id.profile_load);
        prof_cont = findViewById(R.id.profile_container);
        updateBtn = findViewById(R.id.upload_btn);

        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        phone = findViewById(R.id.profile_phone);
        role = findViewById(R.id.profile_role);
        black = findViewById(R.id.profile_black);
        created = findViewById(R.id.profile_created);

        Intent oldIntent =  getIntent();
        oldIntent.getStringExtra("user_id");
        getUserDetails(oldIntent.getStringExtra("user_id"));



        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                name.setClickable(true);
                name.setCursorVisible(true);

                email.setEnabled(true);
                email.setClickable(true);
                email.setCursorVisible(true);


                phone.setEnabled(true);
                phone.setClickable(true);
                phone.setCursorVisible(true);

                updateBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getUserDetails(String user_id) {
        RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
        JSONObject params = new JSONObject();
        try {
            params.put("id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                userDetailsUls, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            if (code.equals("200")) {
                                parseData(response.getJSONObject("message"));
                                Log.d( "onResponse: ", "eta mah");
                            }
                        } catch (JSONException e) {
                            Log.d("Message: ", "inside catch");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d( "onErrorResponse: ", "jesus");
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
        full_name.setText(message.getString("name"));
        if(message.getString("status").equals("0")){
            status.setText("Unverified");
        }else{
            status.setText(", Verified");
        }
        name.setText(message.getString("name"));
        email.setText(message.getString("email"));
        phone.setText(message.getString("contact"));
        if(message.getString("role").equals("0")){
            role.setText("Normal User");
        }else{
            role.setText("Admin");
        }
        black.setText(message.getString("blacklist"));
        created.setText(message.getString("created"));
        pb.setVisibility(View.INVISIBLE);
        prof_cont.setVisibility(View.VISIBLE);
        editProfileBtn.setClickable(true);
    }
}