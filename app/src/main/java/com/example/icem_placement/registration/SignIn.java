package com.example.icem_placement.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icem_placement.R;
import com.example.icem_placement.admin.AdminMainActivity;
import com.example.icem_placement.student.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference myRef;
    private EditText emailET,passwordET;
    private TextView forgetpassword;
    private Button login;
    private String email,password;
    private String TAG = "Sign in activity";
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
        forgetpassword = (TextView) findViewById(R.id.forgetpassword);
        loadingbar = new ProgressDialog(this);
        setupFirebaseAuth();
        initializeComponents();
    }

    private void signInAccount() {
         email = emailET.getText().toString().trim();
         password = passwordET.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(SignIn.this,"Please enter Email ID",Toast.LENGTH_SHORT).show();
            emailET.setError("Please enter email id");
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(SignIn.this,"Please enter password",Toast.LENGTH_SHORT).show();
            passwordET.setError("Please enter password");
        }
        else {
            loadingbar.setTitle("Sign In");
            loadingbar.setMessage("Please wait...");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    FirebaseUser user = mAuth.getCurrentUser();
                    if (task.isSuccessful()) {

                        if(email.equals("admin.account@gmail.com"))
                        {
                            loadingbar.dismiss();
                            Intent intent = new Intent(SignIn.this, AdminMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            try {
                                if (user.isEmailVerified()) {
                                    //Toast.makeText(SignIn.this, "Email verification successful", Toast.LENGTH_SHORT).show();
                                    loadingbar.dismiss();
                                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignIn.this, "Email is not verified \n check your email inbox.", Toast.LENGTH_SHORT).show();
                                    loadingbar.dismiss();
                                    mAuth.signOut();
                                }
                            } catch (NullPointerException e) {
                                String error = e.toString();
                                Toast.makeText(SignIn.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        String error = task.getException().getLocalizedMessage();
                        Toast.makeText(SignIn.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }
    }


    private void initializeComponents(){
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInAccount();
            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.equals("")){
                    Toast.makeText(SignIn.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignIn.this, "Please check your Email", Toast.LENGTH_SHORT).show();
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(SignIn.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }



    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {

                    if(user.getEmail().equals("admin.account@gmail.com"))
                    {
                        Intent intent = new Intent(SignIn.this, AdminMainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    else
                    {
                        // User is signed in
                        try {
                            if (user.isEmailVerified()) {
                                //Toast.makeText(SignIn.this, "Email verification successful", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                Intent intent = new Intent(SignIn.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Email is not verified \n check your email inbox.", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                mAuth.signOut();
                            }
                        } catch (NullPointerException e) {
                            String error = e.toString();
                            Toast.makeText(SignIn.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                        }
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    }
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }



    public void register(View v)
    {
        startActivity(new Intent(SignIn.this, SignUp.class));
        finish();
    }

}