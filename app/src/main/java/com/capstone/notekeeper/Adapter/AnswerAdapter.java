package com.capstone.notekeeper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Models.Answer;
import com.capstone.notekeeper.R;

import java.util.ArrayList;
import java.util.List;


public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerAdapterViewHolder> {

    private List<Answer> mAnswers;
    public void addAnswer(Answer answer) {
        mAnswers.add(answer);
        notifyDataSetChanged();
    }

    public AnswerAdapter() {
        mAnswers = new ArrayList<>();
    }

    @NonNull
    @Override
    public AnswerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_list_item, parent, false);
        return new AnswerAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(AnswerAdapterViewHolder holder, int position) {
        holder.update(position, mAnswers.get(position));
    }

    @Override
    public int getItemCount() {
        return mAnswers.size();
    }

    class AnswerAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView answerString,answerDetails,likeAnswerCount;
        ImageView userImage,likeAnswer;
        int position;

        AnswerAdapterViewHolder(View itemView) {
            super(itemView);
            answerString = itemView.findViewById(R.id.answer_text);
            userImage = itemView.findViewById(R.id.question_owner_image);
            answerDetails = itemView.findViewById(R.id.question_owner_details_time);
            likeAnswer = itemView.findViewById(R.id.like_answer);
        }

        void update(int position, Answer answer) {
            this.position = position;
            answerString.setText(answer.getmAnswerString());

        }
    }
}
