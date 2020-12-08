package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeletePerson extends AppCompatActivity {
    private static final String TAG = "DeletePerson";
    private String personKey;
    private String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_person);

        TextView personName = findViewById(R.id.deleteConfirmationText);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            personKey = (String) bd.get("personKey");
            Log.d(TAG, "getStringExtra: personKey = " + personKey);
            fullName = (String) bd.get("fullName");
            Log.d(TAG, "fetStringExtra: fullName = " + fullName);
        }

        String displayString = fullName + " will be deleted.";
        personName.setText(displayString);
    }

    public void deletePersonData(View view) {
        DatabaseReference personRef =
                FirebaseDatabase.getInstance().getReference("People/"+ personKey);
        personRef.removeValue();
        Toast toast = Toast.makeText(this, fullName + " was deleted", Toast.LENGTH_LONG);
        toast.show();

        Intent intent = new Intent(this, DisplayPeopleList.class);
        startActivity(intent);
    }

    public void cancelDeletePersonData(View view) {
        finish();
    }
}