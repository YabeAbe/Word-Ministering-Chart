package com.example.wordministeringchart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DisplayCompanionsList extends AppCompatActivity {
    private String[] myDataset = new String[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_companions_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.companions_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager rLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter rAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(rAdapter);
    }
}
