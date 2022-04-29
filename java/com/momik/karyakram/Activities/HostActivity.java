package com.momik.karyakram.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.momik.karyakram.R;

public class HostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_host_p1);
    }

    public static class InterestedEventActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu_interested_event);
        }
    }
}