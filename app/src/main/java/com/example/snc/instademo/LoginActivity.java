package com.example.snc.instademo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button signup,signin;
    private FirebaseAuth mAuth;
    EditText emailET,passwordET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup = (Button)findViewById(R.id.login_signup);
        signin = (Button)findViewById(R.id.login_signin);

        mAuth = FirebaseAuth.getInstance();
        emailET = (EditText)findViewById(R.id.login_un);
        passwordET = (EditText)findViewById(R.id.login_pw);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i1);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptSignin();
            }
        });
    }

    public void attemptSignin(){

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Intent i1 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i1);
                }
            }
        });
    }
}
