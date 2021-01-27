package com.example.icem_placement.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.icem_placement.R;
import com.example.icem_placement.adapter.AlertAdapter;
import com.example.icem_placement.adapter.AnswerAdapter;
import com.example.icem_placement.pojo.Answer;
import com.example.icem_placement.pojo.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAnswersActivity extends AppCompatActivity implements ValueEventListener{

    private TextView questionTV;
    private DatabaseReference ref;
    private List<Answer> answerList;
    private AnswerAdapter madapter;
    private RecyclerView recyclerView;
    private ProgressDialog loadingbar;
    private String question,questionID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answers);

        questionTV = findViewById(R.id.questionTV);
        recyclerView = findViewById(R.id.list);

        Intent intent = getIntent();
        question = intent.getStringExtra("question");
        questionID = intent.getStringExtra("questionID");

        questionTV.setText(question);

        answerList = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Answers").child(questionID);
        ref.addListenerForSingleValueEvent((ValueEventListener) this);
        madapter = new AnswerAdapter(ViewAnswersActivity.this,answerList);
        recyclerView.setAdapter(madapter);

        loadingbar = new ProgressDialog(this);
        loadingbar.setTitle("Fetching answers");
        loadingbar.setMessage("Please wait while we are fetching latest answers for you");
        loadingbar.setCanceledOnTouchOutside(true);
        loadingbar.show();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        answerList.clear();
        for(DataSnapshot childsnapshot : snapshot.getChildren()) {
            Answer answer = childsnapshot.getValue(Answer.class);
            if (answer != null) {
                answerList.add(new Answer(answer.getQuestion(),answer.getQuestionID(),answer.getAnswer(),answer.getAnswerID(),answer.getUsername()));
            }
        }

        if (recyclerView != null && madapter != null) {
            loadingbar.dismiss();
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(madapter);
            madapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}