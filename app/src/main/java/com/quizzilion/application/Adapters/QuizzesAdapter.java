package com.quizzilion.application.Adapters;

import android.content.Context;
import android.content.Intent;
import android.sax.TextElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quizzilion.application.Quiz;
import com.quizzilion.application.QuizDetails;
import com.quizzilion.application.R;

import java.util.ArrayList;

public class QuizzesAdapter extends RecyclerView.Adapter<QuizzesAdapter.viewHolder> {

    ArrayList<Quiz> quizes;
    Context context;

    public QuizzesAdapter(ArrayList<Quiz> quiz, Context context) {
        this.quizes = quiz;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_item_view, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        TextView quizId = holder.itemView.findViewById(R.id.quiz_id_item);
        TextView quizName = holder.itemView.findViewById(R.id.name_item);
        TextView viewResults = holder.itemView.findViewById(R.id.viewDetails);

        quizName.setText(quizes.get(position).getName());
        quizId.setText(quizes.get(position).getId());
        int index = position;

        viewResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, QuizDetails.class);
                i.putExtra("Index",  index + "");
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return quizes.size();
    }


    public static class viewHolder extends RecyclerView.ViewHolder {
        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
