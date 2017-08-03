package com.example.snc.instademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    Button submit;
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;
    EditText usernaemET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        submit = (Button)findViewById(R.id.reg_submit);
        usernaemET = (EditText)findViewById(R.id.reg_username);
        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addUserData();
          }
        });
    }

    public void addUserData(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String name = usernaemET.getText().toString();
        final DatabaseReference myRef = database.getReference(mAuth.getCurrentUser().getUid());
        myRef.child("Name").setValue(name);
        myRef.child("Phone").setValue("7207808375");
        myRef.child("DOB").setValue("06-08-1995");
        myRef.child("Gender").setValue("Male");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText(EditProfileActivity.this, "User data added ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(EditProfileActivity.this, "User data not added ", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
