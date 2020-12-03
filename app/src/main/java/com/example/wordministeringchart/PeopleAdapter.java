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

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {
    private final ArrayList<String> personsKeyArray;
    private static final String TAG = "DisplayPeopleList";
    private final Context DisplayPeopleContext;
    private final DatabaseReference peopleRef =
            FirebaseDatabase.getInstance().getReference("People");

    public PeopleAdapter(Context context, ArrayList<String> personsArray) {
        this.personsKeyArray = personsArray;
        this.DisplayPeopleContext = context;
    }

    static class PeopleViewHolder extends RecyclerView.ViewHolder {
        TextView firstName;
        TextView lastName;
        ConstraintLayout peopleLayout;

        public PeopleViewHolder(View view) {
            super(view);
            firstName = view.findViewById(R.id.firstName);
            lastName = view.findViewById(R.id.lastName);
            peopleLayout = view.findViewById(R.id.peopleLayout);
        }
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_people, parent, false);
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
        String personKey = personsKeyArray.get(position);

        peopleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.child(personKey).getValue(Person.class);
                holder.firstName.setText(person.getFirstName());
                holder.lastName.setText(person.getLastName());
                Log.d(TAG, "BindViewHolder for " + person.getFirstName() + " " +
                        person.getLastName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Failed to data reading");
            }
        });

        holder.peopleLayout.setOnClickListener(v -> {
            Intent intent = new Intent(DisplayPeopleContext, DisplayPerson.class);
            intent.putExtra("personKey", personKey);
            Log.d(TAG, "Success to putExtra " + personKey);
            startActivity(DisplayPeopleContext, intent, null);
        });
    }

    @Override
    public int getItemCount() {
        return personsKeyArray.size();
    }
}
