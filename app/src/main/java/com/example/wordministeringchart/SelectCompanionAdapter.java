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

public class SelectCompanionAdapter
        extends RecyclerView.Adapter<SelectCompanionAdapter.SelectCompanionViewHolder> {
    private static final String TAG = "CompanionSelectAD";
    private final ArrayList<String> peopleKeyArray;
    private final DatabaseReference peopleRef =
            FirebaseDatabase.getInstance().getReference("People");
    private final Context SelectCompanionContext;
    private String newFamilyKey;

    public SelectCompanionAdapter(Context context, ArrayList<String> peopleKeyArray) {
        this.peopleKeyArray = peopleKeyArray;
        this.SelectCompanionContext = context;
    }

    static class SelectCompanionViewHolder extends RecyclerView.ViewHolder {
        public TextView firstName;
        public TextView lastName;
        public TextView age;
        public ConstraintLayout peopleLayout;

        public SelectCompanionViewHolder(View view) {
            super(view);
            firstName = view.findViewById(R.id.firstName);
            lastName = view.findViewById(R.id.lastName);
            age = view.findViewById(R.id.age);
            peopleLayout = view.findViewById(R.id.peopleLayout);
        }
    }

    @NonNull
    @Override
    public SelectCompanionAdapter.SelectCompanionViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_member_adapter, parent, false);
        return new SelectCompanionAdapter.SelectCompanionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCompanionViewHolder holder, int position) {
        String personKey = peopleKeyArray.get(position);
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

            }
        });
        // onClickListener for remove this person from family
        holder.peopleLayout.setOnClickListener(v -> {
            Intent intent = new Intent(SelectCompanionContext, AddCompanion.class);
            intent.putExtra("personKey", personKey);
            intent.putExtra("newFamilyKey", newFamilyKey);
            Log.d(TAG, "Success to putExtra " + personKey + " " + newFamilyKey);
            startActivity(SelectCompanionContext, intent, null);
        });
    }
    @Override
    public int getItemCount() {
        return peopleKeyArray.size();
    }
}
