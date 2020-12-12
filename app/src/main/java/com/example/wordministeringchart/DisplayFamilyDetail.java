package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class DisplayFamilyDetail extends AppCompatActivity {
    private static final String TAG = "FamilyDetail";
    private String familyKey;
    private final Context displayFamilyDetailContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_family_detail);
        TextView familyName = findViewById(R.id.familyName);
        TextView familyAddress = findViewById(R.id.familyAddress);

        Bundle extras = getIntent().getExtras();
        familyKey = (String) extras.get("familyKey");

        DatabaseReference familyRef =
                FirebaseDatabase.getInstance().getReference("Family/" + familyKey);
        familyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Family family = snapshot.getValue(Family.class);
                if (family != null) {
                    familyName.setText(family.getFamilyName());
                    familyAddress.setText(family.getAddress());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.familyMemberRecycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DatabaseReference familyMemberRef =
                FirebaseDatabase.getInstance().getReference("FamilyMember/" + familyKey);
        ArrayList<String> memberKeyArray = new ArrayList<>();
        familyMemberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String memberKey = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "MemberKey: ");
                    memberKeyArray.add(memberKey);
                }
                MemberAdapter adapter = new MemberAdapter(displayFamilyDetailContext, memberKeyArray);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void back(View view) {
        finish();
    }
}