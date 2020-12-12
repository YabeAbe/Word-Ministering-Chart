package com.example.wordministeringchart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewPerson extends AppCompatActivity {
    private static final String TAG = "DisplayPeopleList_AddNewPerson";
    private  DatabaseReference peopleRef =
            FirebaseDatabase.getInstance().getReference("People");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_person);
    }

    public void addNewPersonData(View view) {
        Intent intent = new Intent(this, DisplayPeopleList.class);
        // Set new person
        Person newPerson = new Person();

        // get First name field and set in new person
        EditText firstNameField = (EditText) findViewById(R.id.firstNameEdit);
        String firstName = firstNameField.getText().toString();
        newPerson.setFirstName(firstName);

        // get Last name field and set in new person
        EditText lastNameField = (EditText) findViewById(R.id.lastNameEdit);
        String lastName = lastNameField.getText().toString();
        newPerson.setLastName(lastName);

        // get Age field and set in new person
        EditText ageField = (EditText) findViewById((R.id.ageEdit));
        String age = ageField.getText().toString();
        newPerson.setAge(age);

        // get Phone number and set in new person
        EditText phoneField = (EditText) findViewById(R.id.phoneNumberEdit);
        String phoneNumber = phoneField.getText().toString();
        newPerson.setPhoneNumber(phoneNumber);

        // get Mail field and set in new person
        EditText mailField = (EditText) findViewById(R.id.mailEdit);
        String mail = mailField.getText().toString();
        newPerson.setMail(mail);

        // get Facebook field and set in new person
        EditText facebookField = (EditText) findViewById(R.id.facebookEdit);
        String facebook = facebookField.getText().toString();
        newPerson.setFacebook(facebook);

        // get Instagram field and set in new person
        EditText instagramField = (EditText) findViewById(R.id.addInstagramEdit);
        String instagram = instagramField.getText().toString();
        newPerson.setInstagram(instagram);

        // get twitter field and set in new person
        EditText twitterField = (EditText) findViewById(R.id.twitterEdit);
        String twitter = twitterField.getText().toString();
        newPerson.setTwitter(twitter);

        peopleRef.push().setValue(newPerson);

        Toast toast = Toast.makeText(this, firstName + " " + lastName +
                " is added", Toast.LENGTH_LONG);
        toast.show();

        startActivity(intent);
    }

    public void cancelAddPerson(View view) {
        Intent intent = new Intent(this, DisplayPeopleList.class);
        startActivity(intent);
    }
}