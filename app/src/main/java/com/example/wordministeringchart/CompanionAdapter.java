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

public class CompanionAdapter extends RecyclerView.Adapter<CompanionAdapter.CompanionViewHolder> {
    private static final String TAG = "FamilyAdapter";
    private final ArrayList<String> companionKeyArray;
    public final Context displayCompanionContext;
    private final DatabaseReference companionRef =
            FirebaseDatabase.getInstance().getReference("Companion");

    public CompanionAdapter(Context context, ArrayList<String> companionKeyArray) {
        this.companionKeyArray = companionKeyArray;
        this.displayCompanionContext = context;
    }

    static class CompanionViewHolder extends RecyclerView.ViewHolder {
        TextView companion1;
        TextView companion2;
        ConstraintLayout companionLayout;

        public CompanionViewHolder(View view) {
            super(view);
            companion1 = view.findViewById(R.id.companion1);
            companion2 = view.findViewById(R.id.companion2);
            companionLayout = view.findViewById(R.id.companionLayout);
        }
    }

    @NonNull
    @Override
    public CompanionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.companions_adapter, parent, false);
        return new CompanionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanionViewHolder holder, int position) {
        String companionKey = companionKeyArray.get(position);

        companionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Companion companion = snapshot.child(companionKey).getValue(Companion.class);
                if (companion != null) {
                    holder.companion1.setText(companion.getCompanionKey1());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Failed to read data from Database");
            }
        });

        holder.companionLayout.setOnClickListener(v -> {
            Intent intent = new Intent(displayCompanionContext, DisplayCompanionDetail.class);
            intent.putExtra("companionKey", companionKey);
            startActivity(displayCompanionContext, intent, null);
        });
    }

    @Override
    public int getItemCount() {
        return companionKeyArray.size();
    }
}

