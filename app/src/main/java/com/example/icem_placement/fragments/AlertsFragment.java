package com.example.icem_placement.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icem_placement.R;
import com.example.icem_placement.adapter.AlertAdapter;
import com.example.icem_placement.pojo.Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class AlertsFragment extends Fragment implements ValueEventListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private DatabaseReference ref;
    private List<Notification> notificationList;
    private AlertAdapter madapter;
    private RecyclerView recyclerView;
    private ProgressDialog loadingbar;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlertsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AlertsFragment newInstance(int columnCount) {
        AlertsFragment fragment = new AlertsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        notificationList = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Notifications");
        ref.addListenerForSingleValueEvent((ValueEventListener) this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alerts_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));
            madapter = new AlertAdapter(getContext(), notificationList);
            recyclerView.setAdapter(madapter);

            loadingbar = new ProgressDialog(view.getContext());
            loadingbar.setTitle("Fetching notifications");
            loadingbar.setMessage("Please wait while we are fetching new notifications for you");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();
        }
        return view;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        notificationList.clear();
        for(DataSnapshot childsnapshot : snapshot.getChildren()) {
            Notification notification = childsnapshot.getValue(Notification.class);
            if (notification != null) {
                notificationList.add(new Notification(notification.getTime(), notification.getTitle(), notification.getDescription()));
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