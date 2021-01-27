package com.example.icem_placement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icem_placement.R;
import com.example.icem_placement.pojo.Notification;

import java.util.List;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.MyViewHolder>{

    List<Notification> notificationList;
    Context context;

    public AlertAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_row_design, parent, false);
// set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notification notification = (Notification)  notificationList.get(position);
        holder.nameTF.setText(notification.getTitle());
        holder.descriptionTF.setText(notification.getDescription());
    }


    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView nameTF;
        TextView descriptionTF;
        public MyViewHolder(View itemView) {
            super(itemView);

// get the reference of item view's
            nameTF = (TextView) itemView.findViewById(R.id.titleTV);
            descriptionTF = (TextView) itemView.findViewById(R.id.descriptionTV);

        }
    }
}