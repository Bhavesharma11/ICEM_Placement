package com.example.icem_placement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icem_placement.R;
import com.example.icem_placement.pojo.Answer;
import com.example.icem_placement.pojo.Question;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.MyViewHolder> {

    List<Answer> answerList;
    Context context;

    public AnswerAdapter(Context context, List<Answer> answerList)
    {
        this.context = context;
        this.answerList = answerList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_row_design, parent, false);
// set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.MyViewHolder holder, int position) {
        Answer answer = answerList.get(position);
        holder.usernameTF.setText(answer.getUsername());
        holder.answerTF.setText(answer.getAnswer());

    }

    @Override
    public int getItemCount() { return answerList.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView answerTF, usernameTF;
        public MyViewHolder(final View itemView) {
            super(itemView);

// get the reference of item view's
            answerTF = itemView.findViewById(R.id.answerTV);
            usernameTF = itemView.findViewById(R.id.usernameTV);
        }
    }
}
