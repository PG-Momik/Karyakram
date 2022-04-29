package com.momik.karyakram.Activities;

import android.app.ActionBar;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Host3Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String getCatsUrl = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=extra&op=getAllCategories";
    String createUrl = "https://karyakram0.000webhostapp.com/backend/API/api.php?en=extra&op=getAllCategories";

    DatePicker dp1;
    Button backBtn, publishBtn, selectImgBtn;
    String[] courses = { "Select Category", "C", "Data structures",
            "Interview prep", "Algorithms",
            "DSA with java", "OS" };

    ArrayList<CheckBox> tags;
    String related_tags;
    String category, deadline;
    ImageView iv1;
    Bitmap bitmap;
    LinearLayout checkbox_container;
    Intent oldIntent;
    private final int Gallery_Req_code = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_host_p3);

        checkbox_container =  findViewById(R.id.checkboxContainer);
        dp1 =  findViewById(R.id.host_deadline);

        deadline = new StringBuilder().
                append(dp1.getYear()).
                append("-").append(dp1.getMonth() + 1).
                append("-").append(dp1.getDayOfMonth()).
                toString();
        publishBtn = findViewById(R.id.publish_btn);
        selectImgBtn = findViewById(R.id.upload_btn);

        iv1 =  findViewById(R.id.imageView);

        oldIntent = getIntent();
        oldIntent.getStringExtra("user_id");
        oldIntent.getStringExtra("name");
        oldIntent.getStringExtra("start");
        oldIntent.getStringExtra("end");
        oldIntent.getStringExtra("location");
        oldIntent.getStringExtra("price");
        oldIntent.getStringExtra("org");
        oldIntent.getStringExtra("desc");
        try {
            getCatData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    postData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        selectImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery =  new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, Gallery_Req_code);
                iv1.setVisibility(View.VISIBLE);
            }
        });

    }

    private void postData() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(Host3Activity.this);
        JSONObject thingy = new JSONObject();
        thingy.put("user_id", oldIntent.getStringExtra("user_id"));
        thingy.put("name", oldIntent.getStringExtra("name"));
        thingy.put("start",  oldIntent.getStringExtra("start"));
        thingy.put("end", oldIntent.getStringExtra("end"));
        thingy.put("location", oldIntent.getStringExtra("location"));

        thingy.put("price", oldIntent.getStringExtra("price"));
        thingy.put("organizer", oldIntent.getStringExtra("org"));
        thingy.put("details", oldIntent.getStringExtra("desc"));
        thingy.put("category",oldIntent.getStringExtra("category") );

        thingy.put("image", imageToString(bitmap));
        thingy.put("deadline",deadline);
        thingy.put("tags", "x");
//          Log.d( "postData: ", imageToString(bitmap));
        Log.d("json", thingy.toString());
//        params.put("image", imageToString(bitmap));
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                createUrl, thingy,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            if (code.equals("200")) {
                                Log.d("Message", response.getJSONArray("message").toString());
                                loadCheckbox(response.getJSONArray("message"));
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

    private void getCatData() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(Host3Activity.this);
        JSONObject params = new JSONObject();
//        params.put("image", imageToString(bitmap));
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                getCatsUrl, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String code = response.getString("code");
                            if (code.equals("200")) {
                                Log.d("Message", response.getJSONArray("message").toString());
                                loadCheckbox(response.getJSONArray("message"));
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


    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==Gallery_Req_code){
//                iv1.setImageURI(data.getData());
            Uri path = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                    iv1.setImageURI(data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }





    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        category =  courses[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        // Auto-generated method stub
    }


    private void loadCheckbox(JSONArray message) throws JSONException {
        tags = new ArrayList<>();
//        int needed_rows =  (((courses.length-1)/2) + 1);
        int needed_rows =  (((message.length()-1)/2)+1);

        for(int i = 0, index = 0 ; i < needed_rows; i++){
            LinearLayout row =  new LinearLayout(Host3Activity.this);
            row.setId(i);
            row.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 1; j<3; j++){
                if(index<message.length()){
                    CheckBox c =  new CheckBox(Host3Activity.this);
                    c.setLayoutParams(new TableLayout.LayoutParams(
                            ActionBar.LayoutParams.MATCH_PARENT,
                            ActionBar.LayoutParams.WRAP_CONTENT,
                            1f));
                    c.setId(index);
                    c.setText(message.getJSONObject (index).getString("name"));
                    index = index+1;
                    row.addView(c);
                }
            }
            checkbox_container.addView(row);
        }


    }
}