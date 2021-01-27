package com.example.icem_placement.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.icem_placement.chat.ChatActivity;
import com.example.icem_placement.R;
import com.example.icem_placement.registration.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    EditText titleET, descriptionET;
    Button submit;
    DatabaseReference ref;
    String time;

    public CreateNotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateNotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateNotificationFragment newInstance(String param1, String param2) {
        CreateNotificationFragment fragment = new CreateNotificationFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_notification, container, false);

        titleET = (EditText) view.findViewById(R.id.notice_title);
        descriptionET = (EditText) view.findViewById(R.id.notice_description);
        submit = (Button) view.findViewById(R.id.notice_save);
        // Inflate the layout for this fragment

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title_str = titleET.getText().toString();
                String description_str = descriptionET.getText().toString();
                ref = FirebaseDatabase.getInstance().getReference();
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("title", title_str);
                hashMap.put("description", description_str);
                time = String.valueOf(System.currentTimeMillis());
                ref.child("Notifications").child(time).setValue(hashMap);
                titleET.setText("");
                descriptionET.setText("");

                Toast.makeText(getContext(),"Notice created successfully",Toast.LENGTH_SHORT).show();
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

        return view;
    }
}