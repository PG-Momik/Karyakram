package com.momik.karyakram.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Host2Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String getCatsUrl = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=extra&op=getAllCategories";
    EditText et1, et2, et3;
    Button btn2;
    String[] cats;
    String[] id_array, cat_array;
    Spinner spino;
    String category;
    int selected_index;
    String user_id;
    Intent oldIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_host_p2);
        et1 = findViewById(R.id.host_price);
        et2 = findViewById(R.id.host_organization);
        et3 = findViewById(R.id.host_desc);
        spino = findViewById(R.id.coursesspinner);

        btn2 = findViewById(R.id.next_btn);


        getCatsSpinner();

        oldIntent = getIntent();

        user_id = oldIntent.getStringExtra("user_id");


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price = et1.getText().toString();
                String org = et2.getText().toString();
                String desc = et3.getText().toString();

                Intent i = new Intent(Host2Activity.this, Host3Activity.class);
                i.putExtra("user_id", user_id);
                i.putExtra("name", oldIntent.getStringExtra("name"));
                i.putExtra("start", oldIntent.getStringExtra("start"));
                i.putExtra("end", oldIntent.getStringExtra("end"));
                i.putExtra("location", oldIntent.getStringExtra("location"));
                i.putExtra("price", price);
                i.putExtra("org", org);
                i.putExtra("desc", desc);
                i.putExtra("category", category);
                startActivity(i);

            }
        });


    }

    private void getCatsSpinner() {
        RequestQueue queue = Volley.newRequestQueue(Host2Activity.this);
        JSONObject params = new JSONObject();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getCatsUrl, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            if (code.equals("200")) {
                                Log.d("Message", response.getJSONArray("message").toString());
                                loadSpinner(response.getJSONArray("message"));
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


    private void loadSpinner(JSONArray message) throws JSONException {
        int size = message.length();
        id_array = new String[size];
        cat_array = new String[(size+1)];
        cat_array[0]= "Select a Category";
        for(int i = 0; i<message.length(); i++){
            JSONObject obj = message.getJSONObject(i);
            id_array[i] = obj.getString("id");
            cat_array[i+1] = obj.getString("name");
        }
        spino.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,cat_array);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spino.setAdapter(aa);


    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        category =  cat_array[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        spino.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cats);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spino.setAdapter(ad);

    }
}