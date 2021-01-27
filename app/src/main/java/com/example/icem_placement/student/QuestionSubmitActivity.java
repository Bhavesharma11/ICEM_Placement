package com.example.icem_placement.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.icem_placement.R;
import com.example.icem_placement.pojo.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class QuestionSubmitActivity extends AppCompatActivity {

    private EditText questionET;
    private Button submit;
    DatabaseReference ref;
    String questionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_submit);

        questionET = findViewById(R.id.questionET);
        submit = findViewById(R.id.save_question);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = questionET.getText().toString();
                questionID = String.valueOf(System.currentTimeMillis());
                ref = FirebaseDatabase.getInstance().getReference("Questions").child(questionID);
                Question questionObject = new Question(questionID,question);
                ref.setValue(questionObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            questionET.setText("");
                            Toast.makeText(QuestionSubmitActivity.this,"Question submitted successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(QuestionSubmitActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
    }
}