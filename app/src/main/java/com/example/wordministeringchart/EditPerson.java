package com.example.wordministeringchart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPerson extends AppCompatActivity {
    private String personKey;
    private DatabaseReference personRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);

        EditText firstName = findViewById(R.id.firstNameEdit);
        EditText lastName = findViewById(R.id.lastNameEdit);
        EditText age = findViewById(R.id.ageEdit);
        EditText phoneNumber = findViewById(R.id.phoneNumberEdit);
        EditText mail = findViewById(R.id.mailEdit);
        EditText facebook = findViewById(R.id.facebookEdit);
        EditText instagram = findViewById(R.id.addInstagramEdit);
        EditText twitter = findViewById(R.id.twitterEdit);

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            personKey = (String) bd.get("personKey");
        }
        personRef = FirebaseDatabase.getInstance().getReference("People/" + personKey);

        personRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Person person = snapshot.getValue(Person.class);
                if (person != null) {
                    firstName.setText(person.getFirstName());
                    lastName.setText(person.getLastName());
                    age.setText(person.getAge());
                    phoneNumber.setText(person.getPhoneNumber());
                    mail.setText(person.getMail());
                    facebook.setText(person.getFacebook());
                    instagram.setText(person.getInstagram());
                    twitter.setText(person.getTwitter());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updatePerson(View view) {
        Intent updatePersonIntent = new Intent(this, DisplayPerson.class);
        updatePersonIntent.putExtra("personKey", personKey);
        Person updatedPerson = new Person();

        EditText firstNameField = findViewById(R.id.firstNameEdit);
        String updatedFirstName = firstNameField.getText().toString();
        updatedPerson.setFirstName(updatedFirstName);

        EditText lastNameField = findViewById(R.id.lastNameEdit);
        String updatedLastName = lastNameField.getText().toString();
        updatedPerson.setLastName(updatedLastName);

        EditText ageField = findViewById((R.id.ageEdit));
        String updatedAge = ageField.getText().toString();
        updatedPerson.setAge(updatedAge);

        EditText phoneField = findViewById(R.id.phoneNumberEdit);
        String updatedPhoneNumber = phoneField.getText().toString();
        updatedPerson.setPhoneNumber(updatedPhoneNumber);

        EditText mailField = findViewById(R.id.mailEdit);
        String updatedMail = mailField.getText().toString();
        updatedPerson.setMail(updatedMail);

        EditText facebookField = findViewById(R.id.facebookEdit);
        String updatedFacebook = facebookField.getText().toString();
        updatedPerson.setFacebook(updatedFacebook);

        EditText instagramField = findViewById(R.id.addInstagramEdit);
        String updatedInstagram = instagramField.getText().toString();
        updatedPerson.setInstagram(updatedInstagram);

        EditText twitterField = findViewById(R.id.twitterEdit);
        String updatedTwitter = twitterField.getText().toString();
        updatedPerson.setTwitter(updatedTwitter);

        personRef.setValue(updatedPerson);

        Toast toast = Toast.makeText(this, "Data is updated", Toast.LENGTH_LONG);
        toast.show();

        startActivity(updatePersonIntent);
    }
    public void cancelUpdate(View view) {
        finish();
    }
}