package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayFamiliesList extends AppCompatActivity {
    private static final String TAG = "FamiliesList";
    private final DatabaseReference familyRef =
            FirebaseDatabase.getInstance().getReference("Family");
    private final ArrayList<String> familyKeyArray = new ArrayList<>();
    private final Context DisplayFamilyContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_families_list);
        RecyclerView recyclerView = findViewById(R.id.displayFamilies);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        Query familyQuery = familyRef.orderByChild("familyName");
        familyQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String familyKey = dataSnapshot.getKey();
                    Log.d(TAG, "Get family key: " + familyKey);
                    familyKeyArray.add(familyKey);
                }
                FamilyAdapter adapter = new FamilyAdapter(DisplayFamilyContext, familyKeyArray);
                Log.d(TAG, "Create FamilyAdapter");
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addNewFamily(View view) {
        Intent intent = new Intent(this, AddNewFamily.class);
        startActivity(intent);
    }

    public void returnMenu(View view) {
        finish();
    }
}