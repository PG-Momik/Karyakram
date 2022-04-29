package com.momik.karyakram.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.momik.karyakram.R;

public class Host1Activity extends AppCompatActivity {
    EditText et1, et2;
    DatePicker dp1, dp2;
    Button  btn2;
    Intent oldIntent;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_host_p1);

//        user_id =;
        et1 = findViewById(R.id.host_name);
        et2 = findViewById(R.id.host_location);

        dp1 =  findViewById(R.id.host_start);
        dp2 =  findViewById(R.id.host_end);
        oldIntent = getIntent();
        user_id = oldIntent.getStringExtra("user_id");
        btn2 = findViewById(R.id.next_btn);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, location, start, end;
                name = et1.getText().toString();
                location = et2.getText().toString();
                start = new StringBuilder().
                        append(dp1.getYear()).
                        append("-").append(dp1.getMonth() + 1).
                        append("-").append(dp1.getDayOfMonth()).
                        toString();
                end = new StringBuilder().
                        append(dp2.getYear()).
                        append("-").append(dp2.getMonth() + 1).
                        append("-").append(dp2.getDayOfMonth()).
                        toString();

                Intent i = new Intent(Host1Activity.this, Host2Activity.class);
                i.putExtra("user_id", user_id);
                i.putExtra("name", name);
                i.putExtra("location", location);
                i.putExtra("start", start);
                i.putExtra("end", end);
                startActivity(i);
            }
        });

    }
}
