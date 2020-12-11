package com.example.wordministeringchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DisplayFamilyDetail extends AppCompatActivity {
    private static final String TAG = "FamilyDetail";
    private String familyKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_family_detail);
    }
}