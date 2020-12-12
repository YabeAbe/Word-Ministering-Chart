package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayMemberOption extends AppCompatActivity {
    private static final String TAG = "DisplayFamilyMemberOpt";
    private String personKey;
    private String newFamilyKey;
    private String fullNameExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_member_option);
        TextView headerText = findViewById(R.id.headerText);
        TextView firstName = findViewById(R.id.firstName);
        TextView lastName = findViewById(R.id.lastName);
        TextView age = findViewById(R.id.age);
        TextView phoneNumber = findViewById(R.id.phoneNumber);
        TextView mail = findViewById(R.id.mail);
        TextView facebook = findViewById(R.id.facebook);
        TextView instagram = findViewById(R.id.instagram);
        TextView twitter = findViewById(R.id.twitter);
        TextView companion = findViewById(R.id.companion);

        Bundle bd = getIntent().getExtras();
        personKey = (String) bd.get("personKey");
        Log.d(TAG, "getStringExtra: " + personKey);
        DatabaseReference personRef =
                FirebaseDatabase.getInstance().getReference("People/" + personKey);

        personRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.getValue(Person.class);
                if (person != null) {
                    String fullName = person.getFirstName() + " " + person.getLastName();
                    String displayText = fullName + "'s Information";
                    headerText.setText(displayText);
                    firstName.setText(person.getFirstName());
                    lastName.setText(person.getLastName());
                    age.setText(person.getAge());
                    phoneNumber.setText(person.getPhoneNumber());
                    mail.setText(person.getMail());
                    facebook.setText(person.getFacebook());
                    instagram.setText(person.getInstagram());
                    twitter.setText(person.getTwitter());
                    companion.setText(person.getCompanionName());

                    fullNameExtra = fullName;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Data reading failed");
            }
        });
    }

    // Add this person into previous family
    public void addMember(View view) {
        Bundle bd = getIntent().getExtras();
        personKey = (String) bd.get("personKey");
        newFamilyKey = (String) bd.get("newFamilyKey");
        DatabaseReference FamilyMemberRef =
                FirebaseDatabase.getInstance().getReference("FamilyMember");
        FamilyMemberRef.child(newFamilyKey).push().setValue(personKey);
        Log.d(TAG, "PersonKey added into FamilyMember");

        Intent intent = new Intent(this, AddFamilyMember.class);
        intent.putExtra("newFamilyKey", newFamilyKey);
        startActivity(intent);
    }

    // Back to family member adding menu
    public void cancel(View view) {
        finish();
    }
}