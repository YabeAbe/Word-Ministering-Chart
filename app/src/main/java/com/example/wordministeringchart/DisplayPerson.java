package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayPerson extends AppCompatActivity {
    private static final String TAG = "DisplayPerson";
    /*

    private final TextView firstName = findViewById(R.id.firstName);
    private final TextView lastName = findViewById(R.id.lastName);
    private final TextView Age = findViewById(R.id.age);
    private final TextView phoneNumber = findViewById(R.id.phoneNumber);
    private final TextView mail = findViewById(R.id.mail);

    private final Intent intent = getIntent();
    private final String personKey = intent.getStringExtra("personKey");
    private final DatabaseReference personRef =
            FirebaseDatabase.getInstance().getReference("People/"+ personKey);

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_person);

/*
        personRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Person person = dataSnapshot.getValue(Person.class);
                    firstName.setText(person.getFirstName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

 */
    }
}