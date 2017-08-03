package com.example.snc.instademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    EditText nameet,emailet,usernameet,passwordet,confirmpasswordet,contactnoet,dateet;
    RadioButton maleR,femaleR;
    Button signup,reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        emailet = (EditText)findViewById(R.id.reg_email);
        passwordet = (EditText)findViewById(R.id.reg_password);
        signup = (Button)findViewById(R.id.reg_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1=new Intent(SignupActivity.this, EditProfileActivity.class);
                startActivity(i1);
                attemptSignup();

            }
        });

    }

    public  void attemptSignup(){

        String email = emailet.getText().toString();
        String password = passwordet.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Intent i1 = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(i1);
                }
            }
        });
    }

}
