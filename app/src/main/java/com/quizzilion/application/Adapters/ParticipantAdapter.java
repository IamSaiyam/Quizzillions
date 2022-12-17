package com.quizzilion.application.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quizzilion.application.Participant;
import com.quizzilion.application.Quiz;
import com.quizzilion.application.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.viewHolder> {

    ArrayList<Participant> participants;
    Context context;
    Integer questionNums;

    public ParticipantAdapter(ArrayList<Participant> pQuiz, Context context, Integer numOfQuestions) {
        this.participants = pQuiz;
        this.context = context;
        this.questionNums = numOfQuestions;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_item_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        TextView nameParticipant = holder.itemView.findViewById(R.id.name_participant);
        TextView emailParticipant = holder.itemView.findViewById(R.id.email_participant);
        TextView scoreParticipant = holder.itemView.findViewById(R.id.score_participant);
        TextView correct = holder.itemView.findViewById(R.id.correct_out_of);


        nameParticipant.setText(participants.get(position).getpName());
        emailParticipant.setText(participants.get(position).getpEmail());
        scoreParticipant.setText(participants.get(position).getScore() + "%");
        correct.setText(participants.get(position).getCorrect() + "/" + questionNums);

    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{

        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
