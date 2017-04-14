package com.example.crosbylanham.baseballstatscollector;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {
    Button submit;

    TextView registar;

    EditText email,password;

    private String TAG = "LogInPage";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        registar = (TextView) findViewById(R.id.Login_NewRegistar);
        email = (EditText) findViewById(R.id.Login_Email);
        password = (EditText) findViewById(R.id.LogIn_Password);
        submit = (Button) findViewById(R.id.Login_Submit);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Log In", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Log In", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        registarButtonAction();
        signInUser();

    }
    public void registarButtonAction(){
        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserWithEmailAndPassword(
                        email.getText().toString(),
                        password.getText().toString()
                );
            }
        });
    }
    public void signInUser(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                existingUser(
                        email.getText().toString(),
                        password.getText().toString()
                );
            }
        });
    }
    public void createUserWithEmailAndPassword (String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Toast.makeText(LogIn.this,mAuth.getCurrentUser().getUid(),
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LogIn.this,"Sorry we could not registar you",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void existingUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LogIn.this,mAuth.getCurrentUser().getUid(),
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LogIn.this,HomePage.class));
                        }else{
                            Toast.makeText(LogIn.this, "Sorry we were unable to log you in",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
