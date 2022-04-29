package com.momik.karyakram.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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

public class LoginActivity extends AppCompatActivity {
    String loginUrl = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=user&op=login";
    EditText p1, e2;
    Button b1;
    TextView t1;
    CheckBox rememberme;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        p1= findViewById(R.id.phone);
        p1.setText("9860222338");
        e2= findViewById(R.id.password);
        e2.setText("123456789");
        t1 = findViewById(R.id.register_redirect);
        b1= findViewById(R.id.login_btn);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone= p1.getText().toString();
                String password= e2.getText().toString();
                loginUser(phone, password);
            }

        });
    }

    private void loginUser(String phone, String password) {
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        JSONObject params = new JSONObject();
        try {
            params.put("phone", phone);
            params.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                loginUrl, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            if (code.equals("200")) {
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
        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
        i.putExtra("id",message.getString("id"));
        i.putExtra("name", message.getString("name"));
        i.putExtra("role", message.getString("role"));
        startActivity(i);
        finish();
    }
}

