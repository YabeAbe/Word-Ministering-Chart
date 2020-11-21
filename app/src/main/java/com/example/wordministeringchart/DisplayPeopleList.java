package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayPeopleList extends AppCompatActivity {
    private static String TAG = "DisplayPeopleList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Start Activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_people_list);
        RecyclerView recyclerView = findViewById(R.id.displayPeople);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        Log.d(TAG, "create RecyclerView");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "Create DatabaseReference");


        // [START WRITING] This part is for writing data
        Person person1 = new Person();
        person1.setAge("25");
        person1.setFirstName("Yuki");
        person1.setLastName("Abe");
        person1.setPhoneNumber("08045008120");
        person1.setMail("warutu4aria@gmail.com");
        mDatabase.child(person1.getFirstName()).setValue(person1);

        Person person2 = new Person();
        person2.setAge("19?");
        person2.setFirstName("Daijiro");
        person2.setLastName("Sagane");
        person2.setPhoneNumber("1234567890");
        person2.setMail("something@gmail.com");
        mDatabase.child(person2.getFirstName()).setValue(person2);

        Log.d(TAG, "Set person data in database");
        // [END WRITING]

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "Get Datasnapshot");

                Person person_A = snapshot.child("Yuki").getValue(Person.class);
                Log.d(TAG, "get person_A");
                Person person_B = snapshot.child("Daijiro").getValue(Person.class);
                Log.d(TAG, "get person_B");

                ArrayList<Person> peopleArray = new ArrayList<>();
                peopleArray.add(person_A);
                peopleArray.add(person_B);
                Log.d(TAG, "Create peopleArray");

                RecyclerView.Adapter adapter = new PeopleAdapter(peopleArray);
                Log.d(TAG, "Create PersonAdapter");
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value");
            }
        });
    }
}