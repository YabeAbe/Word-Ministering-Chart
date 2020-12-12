package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateNewCompanion extends AppCompatActivity {
    private static final String TAG = "CompanionCreate";
    private final DatabaseReference peopleRef =
            FirebaseDatabase.getInstance().getReference("People");
    private final ArrayList<String> peopleKeyArray = new ArrayList<>();
    private final Context createCompanionContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_companion);

        RecyclerView recyclerView = findViewById(R.id.personRecycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        Query peopleQuery = peopleRef.orderByChild("firstName");
        peopleQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String personKey = dataSnapshot.getKey();
                    Log.d(TAG, "Key: " + personKey);
                    peopleKeyArray.add(personKey);
                }
                SelectCompanionAdapter adapter = new SelectCompanionAdapter(createCompanionContext, peopleKeyArray);
                Log.d(TAG, "Create PersonAdapter");
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Data reading failed");
            }
        });
    }

}