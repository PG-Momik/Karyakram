package com.momik.karyakram.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.momik.karyakram.R;

public class MenuActivity extends AppCompatActivity {
    LinearLayout ll1, ll2, ll3, ll4;
    String black="#272727";
    String white ="#ffffff";
    String id;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if(getIntent()!=null){
            id = getIntent().getStringExtra("user_id");
            Log.d( "menu ma id aayo ki?", id);

        }

        ll1 = findViewById(R.id.menu_1);
        ll2 = findViewById(R.id.menu_2);
        ll3 = findViewById(R.id.menu_3);
        ll4 = findViewById(R.id.menu_4);

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, ProfileActivity.class);
                i.putExtra("user_id", id);
                startActivity(i);
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, Host1Activity.class);
                i.putExtra("user_id", id);
                startActivity(i);
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, InterestedEventActivity.class);
                i.putExtra("user_id", "1");
                startActivity(i);
            }
        });
//        ll4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MenuActivity.this, Logout.class);
//                startActivity(i);
//            }
//        });

    }
}