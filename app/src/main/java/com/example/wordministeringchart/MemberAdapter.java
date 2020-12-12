package com.example.wordministeringchart;

import android.content.Context;
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

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    private static final String TAG = "FamilyMember_ad";
    private final ArrayList<String> memberKeyArray;
    private final DatabaseReference peopleRef =
            FirebaseDatabase.getInstance().getReference("People");
    private final Context displayFamilyDetailContext;
    private String newFamilyKey;

    public MemberAdapter(Context context, ArrayList<String> memberKeyArray) {
        this.memberKeyArray = memberKeyArray;
        this.displayFamilyDetailContext = context;
    }

    static class MemberViewHolder extends RecyclerView.ViewHolder {
        public TextView firstName;
        public TextView lastName;
        public TextView age;
        public ConstraintLayout peopleLayout;

        public MemberViewHolder(View view) {
            super(view);
            firstName = view.findViewById(R.id.firstName);
            lastName = view.findViewById(R.id.lastName);
            age = view.findViewById(R.id.age);
            peopleLayout = view.findViewById(R.id.peopleLayout);
        }
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_adapter, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        String personKey = memberKeyArray.get(position);
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
    }
    @Override
    public int getItemCount() {
        return memberKeyArray.size();
    }
}
