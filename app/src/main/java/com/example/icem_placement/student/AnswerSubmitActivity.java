package com.example.icem_placement.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icem_placement.R;
import com.example.icem_placement.pojo.Answer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AnswerSubmitActivity extends AppCompatActivity {

    private TextView questionTV;
    private EditText answerET;
    private Button submit;
    private String question,questionID;
    private String answer, answerID, username;
    FirebaseUser user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_submit);

        questionTV = findViewById(R.id.questionTV);
        answerET = findViewById(R.id.answerET);
        submit = findViewById(R.id.save_answer);

        Intent intent = getIntent();
        question = intent.getStringExtra("question");
        questionID = intent.getStringExtra("questionID");

        questionTV.setText(question);
        user = FirebaseAuth.getInstance().getCurrentUser();
        username = user.getEmail();
        int i = username.indexOf("@");
        username = username.substring(0,i);
        Toast.makeText(this, username,Toast.LENGTH_SHORT).show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = answerET.getText().toString();
                answerID = String.valueOf(System.currentTimeMillis());

                ref = FirebaseDatabase.getInstance().getReference("Answers").child(questionID).child(answerID);
                Answer answerObject = new Answer(question,questionID,answer,answerID,username);
                ref.setValue(answerObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            answerET.setText("");
                            Toast.makeText(AnswerSubmitActivity.this,"Answer submitted successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AnswerSubmitActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        });
    }

}