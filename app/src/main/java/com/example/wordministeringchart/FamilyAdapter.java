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

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.FamilyViewHolder> {
    private static final String TAG = "FamilyAdapter";
    private final ArrayList<String> familyKeyArray;
    public final Context displayFamilyContext;
    private final DatabaseReference familyRef =
            FirebaseDatabase.getInstance().getReference("Family");

    public FamilyAdapter(Context context, ArrayList<String> familyKeyArray) {
        this.familyKeyArray = familyKeyArray;
        this.displayFamilyContext = context;
    }

    static class FamilyViewHolder extends RecyclerView.ViewHolder {
        TextView familyName;
        ConstraintLayout familyLayout;

        public FamilyViewHolder(View view) {
            super(view);
            familyName = view.findViewById(R.id.familyName);
            familyLayout = view.findViewById(R.id.familyLayout);
        }
    }

    @NonNull
    @Override
    public FamilyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_adapter, parent, false);
        return new FamilyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyViewHolder holder, int position) {
        String familyKey = familyKeyArray.get(position);

        familyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Family family = snapshot.child(familyKey).getValue(Family.class);
                if (family != null) {
                    holder.familyName.setText(family.getFamilyName());
                    Log.d(TAG, "BindViewHolder for " + family.getFamilyName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Failed to read data from Database");
            }
        });

        holder.familyLayout.setOnClickListener(v -> {
            Intent intent = new Intent(displayFamilyContext, DisplayFamilyDetail.class);
            intent.putExtra("familyKey", familyKey);
            Log.d(TAG, "Success to putExtra " + familyKey);
            startActivity(displayFamilyContext, intent, null);
        });
    }

    @Override
    public int getItemCount() {
        return familyKeyArray.size();
    }
}
