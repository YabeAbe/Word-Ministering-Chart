package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;

public class AddFamilyMember extends AppCompatActivity {
    private static final String TAG = "AddFamilyMember";
    private String newFamilyName;
    private ArrayList<String> familyMemberKeyArray = new ArrayList<>();
    private final ArrayList<String> peopleKeyArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_member);

        // get previously created new family reference
        Bundle extras = getIntent().getExtras();
        String newFamilyKey = extras.getString("newFamilyKey");
        Log.d(TAG, "newFamilyKey: " + newFamilyKey);

        DatabaseReference familyRef = FirebaseDatabase.getInstance().getReference("Family/" + newFamilyKey);

        // Assign family name to layout
        TextView familyName = findViewById(R.id.familyName);
        familyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Family newFamily = snapshot.getValue(Family.class);
                newFamilyName = newFamily.getFamilyName();
                familyName.setText(newFamilyName);
                Log.d(TAG, "Family name assigned to the layout");
                // Create familyMemberOptionRecycler
                familyMemberOptionRecycler(newFamilyName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Data reading failed");
            }
        });

        // Create familyMemberRecycler
        familyMemberRecycler(familyRef);
    }

    public void familyMemberRecycler(DatabaseReference familyRef) {
        Log.d(TAG, "Call familyMemberRecycler");
        RecyclerView familyMemberRecycler = findViewById(R.id.familyMemberRecycler);
        familyMemberRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        familyMemberRecycler.setLayoutManager(layoutManager);

        familyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Family newFamily = snapshot.getValue(Family.class);
                if (newFamily.getFamilyName() != null) {
                    familyMemberKeyArray = newFamily.getFamilyMemberKeyArray();
                    Log.d(TAG, "get familyMemberKeyArray");
                } else {
                    Log.d(TAG, "familyMemberRecycler is skipped");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Data reading failed");
            }
        });
        FamilyMemberAdapter familyMemberAdapter = new FamilyMemberAdapter(familyMemberKeyArray);
        familyMemberRecycler.setAdapter(familyMemberAdapter);
    }

    public void familyMemberOptionRecycler(String newFamilyName) {
        Log.d(TAG, "Call familyMemberOptionRecycler");
        Log.d(TAG, "familyMemberOptionRecycler newFamilyName: " + newFamilyName);
        RecyclerView familyMemberOptionRecycler = findViewById(R.id.familyMemberOptionRecycler);
        familyMemberOptionRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        familyMemberOptionRecycler.setLayoutManager(layoutManager);

        DatabaseReference peopleRef = FirebaseDatabase.getInstance().getReference("People");

        // search people who has same last name with new family
        Query peopleQuery = peopleRef.orderByChild("lastName").equalTo(newFamilyName);
        peopleQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String personKey = dataSnapshot.getKey();
                    peopleKeyArray.add(personKey);
                }
                FamilyMemberOptionAdapter adapter = new FamilyMemberOptionAdapter(peopleKeyArray);
                familyMemberOptionRecycler.setAdapter(adapter);

                Set<Integer> selectedItemPositionsSet = adapter.getSelectedItemPositions();
                Log.d(TAG, "Get selectedItemPositions");
                ArrayList<Integer> selectedItemPositionsArray = new ArrayList<>(selectedItemPositionsSet);
                ArrayList<String> selectedFamilyMemberList = new ArrayList<>();
                String selectedNewFamilyMemberKey;
                for (int i = 0; i < selectedItemPositionsArray.size(); i++) {
                    int selectedItemPositions = selectedItemPositionsArray.get(i);
                    selectedNewFamilyMemberKey = peopleKeyArray.get(selectedItemPositions);
                    selectedFamilyMemberList.add(selectedNewFamilyMemberKey);
                }
                for (int i = 0; i < selectedFamilyMemberList.size(); i++) {
                    Log.d(TAG, selectedFamilyMemberList.get(i));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Data reading failed");
            }
        });
    }

    public void addNewFamilyMember(View viw) {

    }
}