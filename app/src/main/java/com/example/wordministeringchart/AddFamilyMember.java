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
    private Context addFamilyMemberContext = this;

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
                familyMemberOptionRecycler(newFamilyName, newFamilyKey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Data reading failed");
            }
        });

        // Create familyMemberRecycler
        familyMemberRecycler(newFamilyKey);
    }

    public void familyMemberRecycler(String newFamilyKey) {
        Log.d(TAG, "Call familyMemberRecycler");
        RecyclerView familyMemberRecycler = findViewById(R.id.familyMemberRecycler);
        familyMemberRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        familyMemberRecycler.setLayoutManager(layoutManager);

        DatabaseReference familyMemberRef =
                FirebaseDatabase.getInstance().getReference("FamilyMember/" + newFamilyKey);
        Log.d(TAG, "newFamilyKey: " + newFamilyKey);
        familyMemberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String personKey = (String) dataSnapshot.getValue();
                    familyMemberKeyArray.add(personKey);
                    Log.d(TAG, "Added personKey: " + personKey);
                }
                FamilyMemberAdapter familyMemberAdapter =
                        new FamilyMemberAdapter(addFamilyMemberContext, familyMemberKeyArray,
                                newFamilyKey);
                familyMemberRecycler.setAdapter(familyMemberAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Data reading failed");
            }
        });
    }

    public void familyMemberOptionRecycler(String newFamilyName, String newFamilyKey) {
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
                FamilyMemberOptionAdapter adapter =
                        new FamilyMemberOptionAdapter(addFamilyMemberContext, peopleKeyArray,
                                newFamilyKey);
                familyMemberOptionRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Data reading failed");
            }
        });
    }
}