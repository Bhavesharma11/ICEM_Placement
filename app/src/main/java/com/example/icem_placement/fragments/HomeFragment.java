package com.example.icem_placement.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icem_placement.chat.ChatActivity;
import com.example.icem_placement.student.QuestionSubmitActivity;
import com.example.icem_placement.R;
import com.example.icem_placement.registration.SignIn;
import com.example.icem_placement.adapter.QuestionAdapter;
import com.example.icem_placement.pojo.Question;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ValueEventListener{

    private DatabaseReference ref;
    private List<Question> questionList;
    private QuestionAdapter madapter;
    private RecyclerView recyclerView;
    private ProgressDialog loadingbar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        questionList = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Questions");
        ref.addListenerForSingleValueEvent((ValueEventListener) this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.list);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), QuestionSubmitActivity.class);
                startActivity(i);
            }
        });

        ImageView message = (ImageView) view.findViewById(R.id.message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ChatActivity.class);
                startActivity(i);
            }
        });

        ImageView logout = (ImageView) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getContext(), SignIn.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                startActivity(i);
            }
        });

        madapter = new QuestionAdapter(getContext(), questionList);
        recyclerView.setAdapter(madapter);

        loadingbar = new ProgressDialog(view.getContext());
        loadingbar.setTitle("Fetching questions");
        loadingbar.setMessage("Please wait while we update your feed");
        loadingbar.setCanceledOnTouchOutside(true);
        loadingbar.show();

        // Inflate the layout for this fragment
        return  view;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        questionList.clear();
        for(DataSnapshot childsnapshot : snapshot.getChildren()) {
            Question question = childsnapshot.getValue(Question.class);
            if (question != null) {
                questionList.add(new Question(question.getQuestionID(), question.getQuestion()));
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