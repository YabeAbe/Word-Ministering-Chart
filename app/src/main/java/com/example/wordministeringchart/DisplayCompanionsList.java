package com.example.wordministeringchart;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class DisplayCompanionsList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String[] myDataset = new String[20];
    private static final String TAG = "DisplayCompanions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_companions_list);
        recyclerView = findViewById(R.id.companions_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);


        int i = 0;
        while (i < 20) {
            myDataset[i] = String.format(Locale.ENGLISH, "Data_0%d", i);
            i++;
        }
        if (i == 20) {
            Log.d(TAG, "Create Data list");
        }

        // specify an adapter (see also next example)

        RecyclerView.Adapter rAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(rAdapter);
    }
}
