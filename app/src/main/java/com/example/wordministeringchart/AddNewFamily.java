    package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddNewFamily extends AppCompatActivity {
    private static final String TAG = "AddNewFamily";
    private final DatabaseReference familyRef =
            FirebaseDatabase. getInstance().getReference("Family");
    private String newFamilyKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_family);
    }

    public void addNewFamilyData(View view) {
        // Set new family
        Family newFamily = new Family();

        // Set general information
        EditText familyNameField = findViewById(R.id.familyNameEdit);
        String familyName = familyNameField.getText().toString();
        newFamily.setFamilyName(familyName);

        EditText familyAddressField = findViewById(R.id.familyAddressEdit);
        String familyAddress = familyAddressField.getText().toString();
        newFamily.setAddress(familyAddress);

        // Write general data into database
        DatabaseReference familyPushRef = familyRef.push();
        familyPushRef.setValue(newFamily);
        Log.d(TAG, "New Family info written in database");

        newFamilyKey = familyPushRef.getKey();
        Log.d(TAG, "newFamilyKey: " + newFamilyKey);

        // Move to set family member in next activity
        Intent intent = new Intent(this, AddFamilyMember.class);
        intent.putExtra("newFamilyKey", newFamilyKey);
        startActivity(intent);
    }

    public void cancelAddNewFamily(View view) {
        finish();
    }
}

