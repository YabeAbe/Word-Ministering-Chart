package com.example.wordministeringchart;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private String[] dataset;
    private String TAG = "Adapter";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        private static String TAG = "Adapter_ViewHolder";
        TextView mTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.text_view);
            Log.d(TAG, "ViewHolder created");
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    MyAdapter(String[] myDataset) {
        dataset = myDataset;
        for (int i = 0; i < 20; i++) {
            Log.d("MyAdapter", String.valueOf(dataset[i]));
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.d(TAG, "onBindViewHolder");
        Log.d(TAG, dataset[position]);
        holder.mTextView.setText(dataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.length;
    }
}