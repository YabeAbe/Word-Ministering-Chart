package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
    private final ArrayList<String> peopleKeyArray = new ArrayList<>();
    private final Context DisplayPeopleContext = this;

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

        Query peopleQuery = peopleRef.orderByChild("firstName");
        peopleQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String personKey = dataSnapshot.getKey();
                    Log.d(TAG, "Key: " + personKey);
                    peopleKeyArray.add(personKey);
                }
                PeopleAdapter adapter = new PeopleAdapter(DisplayPeopleContext, peopleKeyArray);
                Log.d(TAG, "Create PersonAdapter");
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Data reading failed");
            }
        });
    }

    public void addNewPerson(View view) {
        Intent intent = new Intent(this, AddNewPerson.class);
        startActivity(intent);
    }

    public void returnMenu(View view) {
        finish();
    }
}