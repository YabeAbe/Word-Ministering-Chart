package com.example.wordministeringchart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddNewPerson extends AppCompatActivity {
    Person newPerson = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_person);
    }

    public void addNewPersonData() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}