package com.example.wordministeringchart;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class FamilyMemberAdapter extends RecyclerView.Adapter<FamilyMemberAdapter.FamilyMemberViewHolder> {
    private static final String TAG = "FamilyMember_ad";
    private final ArrayList<String> familyMemberKeyArray;
    private final DatabaseReference peopleRef =
            FirebaseDatabase.getInstance().getReference("People");
    private final Context addFamilyMemberContext;
    private String newFamilyKey;

    public FamilyMemberAdapter(Context context, ArrayList<String> familyMemberKeyArray, String newFamilyKey) {
        this.familyMemberKeyArray = familyMemberKeyArray;
        this.addFamilyMemberContext = context;
        this.newFamilyKey = newFamilyKey;
    }

    static class FamilyMemberViewHolder extends RecyclerView.ViewHolder {
        public TextView firstName;
        public TextView lastName;
        public TextView age;
        public ConstraintLayout peopleLayout;

        public FamilyMemberViewHolder(View view) {
            super(view);
            firstName = view.findViewById(R.id.firstName);
            lastName = view.findViewById(R.id.lastName);
            age = view.findViewById(R.id.age);
            peopleLayout = view.findViewById(R.id.peopleLayout);
        }
    }

    @NonNull
    @Override
    public FamilyMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_member_adapter, parent, false);
        return new FamilyMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberViewHolder holder, int position) {
        String personKey = familyMemberKeyArray.get(position);
        peopleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.child(personKey).getValue(Person.class);
                if (person != null) {
                    holder.firstName.setText(person.getFirstName());
                    holder.lastName.setText(person.getLastName());
                    holder.age.setText(person.getAge());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Data reading Failed");
            }
        });

        /*
        // onClickListener for remove this person from family
        holder.peopleLayout.setOnClickListener(v -> {
            Intent intent = new Intent(addFamilyMemberContext, RemoveFamilyMember.class);
            intent.putExtra("personKey", personKey);
            intent.putExtra("newFamilyKey", newFamilyKey);
            Log.d(TAG, "Success to putExtra " + personKey + " " + newFamilyKey);
            startActivity(addFamilyMemberContext, intent, null);
        });

         */

    }

    @Override
    public int getItemCount() {
        return familyMemberKeyArray.size();
    }
}
