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

import org.w3c.dom.Text;

public class DisplayPerson extends AppCompatActivity {
    private static final String TAG = "DisplayPerson";
    private String personKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_person);
        TextView headerName = findViewById(R.id.headerName);
        TextView firstName = findViewById(R.id.firstName);
        TextView lastName = findViewById(R.id.lastName);
        TextView age = findViewById(R.id.age);
        TextView phoneNumber = findViewById(R.id.phoneNumber);
        TextView mail = findViewById(R.id.mail);
        TextView facebook = findViewById(R.id.facebook);
        TextView instagram = findViewById(R.id.instagram);
        TextView twitter = findViewById(R.id.twitter);
        TextView companion = findViewById(R.id.companion);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            personKey = (String) bd.get("personKey");
            Log.d(TAG, "getStringExtra: " + personKey);
        }
        DatabaseReference personRef =
                FirebaseDatabase.getInstance().getReference("People/"+ personKey);

        personRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.getValue(Person.class);
                headerName.setText(person.getFirstName() + " " +person.getLastName() + "'s Information");
                firstName.setText(person.getFirstName());
                lastName.setText(person.getLastName());
                age.setText(person.getAge());
                phoneNumber.setText(person.getPhoneNumber());
                mail.setText(person.getMail());
                facebook.setText(person.getFacebook());
                instagram.setText(person.getInstagram());
                twitter.setText(person.getTwitter());
                companion.setText(person.getCompanionName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void EditPerson(View view) {
        Intent intent = new Intent(this, EditPerson.class);
        intent.putExtra("personKey", personKey);
        startActivity(intent);
    }
    /*
    public void deletePerson(View view) {
        Intent intent new Intent(this, )
    }

     */

    public void backToPeopleList(View view) {
        Intent intent = new Intent(this, DisplayPeopleList.class);
        startActivity(intent);
    }
}