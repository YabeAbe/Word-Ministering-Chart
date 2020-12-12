package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayCompanionsList extends AppCompatActivity {
    private static final String TAG = "Companions";
    private final DatabaseReference companionsRef =
            FirebaseDatabase.getInstance().getReference("Companion");
    private final ArrayList<String> companionsKeyArray = new ArrayList<>();
    private final Context DisplayCompanionsContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_companions_list);

        RecyclerView recyclerView = findViewById(R.id.companionRecycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        companionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String  companionKey = dataSnapshot.getKey();
                    companionsKeyArray.add(companionKey);
                }
                CompanionAdapter adapter = new CompanionAdapter(DisplayCompanionsContext, companionsKeyArray);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void createNewCompanion(View view) {
        Intent intent = new Intent(this, CreateNewCompanion.class);
        startActivity(intent);
    }
}