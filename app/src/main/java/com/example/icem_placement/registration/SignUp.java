package com.example.icem_placement.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.icem_placement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    private EditText usernameET,emailET,mobileET,passwordET;
    private Button register;
    private ProgressDialog loadingbar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    String UserID;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeComponents();

        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mContext = SignUp.this;

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        final String username = usernameET.getText().toString().trim();
        final String email = emailET.getText().toString().trim();
        final String mobile = mobileET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password))
        {
            Toast.makeText(SignUp.this,"Some fields are missing",Toast.LENGTH_SHORT).show();
        }
        else if(!email.contains("@") || !email.contains(".com"))
        {
            Toast.makeText(SignUp.this,"Please enter valid Indira ID",Toast.LENGTH_SHORT).show();
        }
        else if(mobile.length()!=10)
        {
            Toast.makeText(SignUp.this,"Invalid mobile number",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingbar.setTitle("Creating Account");
            loadingbar.setMessage("Please wait while we are creating account for you");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        if(email.equals("admin.account@gmail.com"))
                        {
                            UserID = mAuth.getCurrentUser().getUid();
                            Toast.makeText(SignUp.this,"Account created successfully",Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            myRef = FirebaseDatabase.getInstance().getReference("Users").child(UserID);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", UserID);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("search", username.toLowerCase());
                            hashMap.put("email", email);
                            hashMap.put("mobile", mobile);

                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(SignUp.this, SignIn.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                        else
                        {
                            sendVerificationEmail();
                            UserID = mAuth.getCurrentUser().getUid();
                            Toast.makeText(SignUp.this,"Account created successfully",Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                            myRef = FirebaseDatabase.getInstance().getReference("Users").child(UserID);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", UserID);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("search", username.toLowerCase());
                            hashMap.put("email", email);
                            hashMap.put("mobile", mobile);

                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(SignUp.this, SignIn.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                    }
                    else
                    {
                        String error = task.getException().toString();
                        Toast.makeText(SignUp.this,"Error : "+error,Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }
    }

    private void initializeComponents() {
        usernameET = (EditText) findViewById(R.id.username);
        emailET = (EditText) findViewById(R.id.email);
        mobileET = (EditText) findViewById(R.id.mobile);
        passwordET = (EditText) findViewById(R.id.password);

        register = (Button) findViewById(R.id.register);
        loadingbar = new ProgressDialog(this);
    }

    public void login(View v)
    {
        startActivity(new Intent(SignUp.this, SignIn.class));
        finish();
    }


    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                            }else{
                                Toast.makeText(SignUp.this,"couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}