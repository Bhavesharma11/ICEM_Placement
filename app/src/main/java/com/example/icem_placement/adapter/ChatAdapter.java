package com.example.icem_placement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icem_placement.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    ArrayList displayName;
    ArrayList profileImage;
    Context context;

    public ChatAdapter(Context context, ArrayList displayName, ArrayList profileImage) {
        this.context = context;
        this.displayName = displayName;
        this.profileImage = profileImage;
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_row_design, parent, false);
// set the view's size, margins, paddings and layout parameters
        ChatAdapter.MyViewHolder vh = new ChatAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        holder.nameTF.setText((CharSequence) displayName.get(position));
        holder.userImage.setImageResource((Integer) profileImage.get(position));
    }


    @Override
    public int getItemCount() {
        return displayName.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTF;
        CircleImageView userImage;
        public MyViewHolder(View itemView) {
            super(itemView);

// get the reference of item view's
            nameTF = (TextView) itemView.findViewById(R.id.textViewSingleListName);
            userImage = (CircleImageView) itemView.findViewById(R.id.circleImageViewUserImage);

        }
    }
}
