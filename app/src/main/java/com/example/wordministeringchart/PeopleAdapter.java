package com.example.wordministeringchart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {
    private final ArrayList<Person> personsArray;
    private static String TAG = "DisplayPeopleList_PeopleAdapter";

    static class PeopleViewHolder extends RecyclerView.ViewHolder {
        TextView firstName;
        TextView lastName;

        public PeopleViewHolder(View view) {
            super(view);
            firstName = (TextView)view.findViewById(R.id.firstName);
            lastName = (TextView)view.findViewById(R.id.lastName);
        }
    }

    public PeopleAdapter(ArrayList<Person> personsArray) {
        this.personsArray = personsArray;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_people, parent, false);
        Log.d(TAG, "Create ViewHolder by activity_people");
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
        holder.firstName.setText((CharSequence) personsArray.get(position).getFirstName());
        holder.lastName.setText((CharSequence) personsArray.get(position).getLastName());
        Log.d(TAG, "Bind ViewHolder");
    }

    @Override
    public int getItemCount() {
        return personsArray.size();
    }
}
