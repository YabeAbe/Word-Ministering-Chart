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
import java.util.Set;

import static androidx.core.content.ContextCompat.startActivity;

public class FamilyMemberOptionAdapter
        extends RecyclerView.Adapter<FamilyMemberOptionAdapter.FamilyMemberOptionViewHolder> {
    private static final String TAG = "FamilyMemberOptAd";
    private final ArrayList<String> peopleKeyArray;
    private final DatabaseReference peopleRef =
            FirebaseDatabase.getInstance().getReference("People");
    private final Context addFamilyMemberContext;
    private String newFamilyKey;

    public FamilyMemberOptionAdapter(Context context, ArrayList<String> peopleKeyArray, String newFamilyKey) {
        this.peopleKeyArray = peopleKeyArray;
        this.addFamilyMemberContext = context;
        this.newFamilyKey = newFamilyKey;
    }

    static class FamilyMemberOptionViewHolder extends RecyclerView.ViewHolder {
        public TextView firstName;
        public TextView lastName;
        public ConstraintLayout peopleLayout;

        public FamilyMemberOptionViewHolder(View view) {
            super(view);
            firstName = view.findViewById(R.id.firstName);
            lastName = view.findViewById(R.id.lastName);
            peopleLayout = view.findViewById(R.id.peopleLayout);
        }
    }

    @NonNull
    @Override
    public FamilyMemberOptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_member_option_adapter, parent, false);
        return new FamilyMemberOptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberOptionViewHolder holder, int position) {
        String personKey = peopleKeyArray.get(position);

        // Read Database and set value into holder
        peopleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.child(personKey).getValue(Person.class);
                if (person != null) {
                    holder.firstName.setText(person.getFirstName());
                    holder.lastName.setText(person.getLastName());
                    Log.d(TAG, "BindViewHolder for " + person.getFirstName() + " " +
                            person.getLastName());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Data reading Failed");
            }
        });

        // This CardView is clickable
        holder.peopleLayout.setOnClickListener(v -> {
            Intent intent = new Intent(addFamilyMemberContext, DisplayMemberOption.class);
            intent.putExtra("personKey", personKey);
            intent.putExtra("newFamilyKey", newFamilyKey);
            Log.d(TAG, "Success to putExtra " + personKey);
            startActivity(addFamilyMemberContext, intent, null);
        });
    }

    @Override
    public int getItemCount() {
        return peopleKeyArray.size();
    }
}
