package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * Activity for displaying people list
 *
 * This Activity is used to display card list of each people info which contain in Firebase
 * realtime database.
 *
 * @author Yuki Abe
 *
 */
public class DisplayPeopleList extends AppCompatActivity {
    private static final String TAG = "DisplayPeopleList";
    private final DatabaseReference peopleRef =
            FirebaseDatabase.getInstance().getReference("People");
    private ArrayList<Person> peopleArray = new ArrayList<>();

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

        // [START WRITING] This part is for writing test data
        /*
        Person person1 = new Person();
        person1.setAge("25");
        person1.setFirstName("Yuki");
        person1.setLastName("Abe");
        person1.setPhoneNumber("08045008120");
        person1.setMail("warutu4aria@gmail.com");
        peopleRef.push().setValue(person1);

        Person person2 = new Person();
        person2.setAge("19?");
        person2.setFirstName("Daijiro");
        person2.setLastName("Sagane");
        person2.setPhoneNumber("1234567890");
        person2.setMail("something@gmail.com");
        peopleRef.push().setValue(person2);

        Log.d(TAG, "Set person data in database");
         */
        // [END WRITING]

        Query peopleQuery = peopleRef.orderByChild("firstName");
        peopleQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Person person = (Person) dataSnapshot.getValue(Person.class);
                    Log.d(TAG, "Name is " + person.getFirstName());
                    peopleArray.add(person);
                }
                PeopleAdapter adapter = new PeopleAdapter(peopleArray);
                Log.d(TAG, "Create PersonAdapter");
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addNewPerson(View view) {
        Intent intent = new Intent(this, AddNewPerson.class);
        startActivity(intent);
    }

    public void returnMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}