package com.example.wordministeringchart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private final ArrayList<Person> mDataset;
    private static String TAG = "DisplayPeopleList_PersonAdapter";

    static class ViewHolder extends RecyclerView.ViewHolder {
        private static String TAG = "DisplayPeopleList_PersonAdapter_ViewHolder";
        TextView first_name;
        TextView last_name;
        TextView age;

        ViewHolder(View view) {
            super(view);
            first_name = (TextView)view.findViewById(R.id.firstname);
            last_name = (TextView)view.findViewById(R.id.lastname);
            age = (TextView)view.findViewById(R.id.age);
        }
    }

    PersonAdapter(ArrayList<Person> personsArray) {
        mDataset = personsArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_person, parent, false);
        Log.d(TAG, "Create ViewHolder");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.first_name.setText((CharSequence) mDataset.get(position).getFirstname());
        holder.last_name.setText((CharSequence) mDataset.get(position).getLastname());
        holder.age.setText((CharSequence) mDataset.get(position).getAge());
        Log.d(TAG, "Bind Viewholder");
    }

    @Override
    public int getItemCount() {
        int itemCount = mDataset.size();
        Log.d(TAG, "Get itemCount = " + mDataset.size());
        return itemCount;
    }


}
