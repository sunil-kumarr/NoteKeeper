package com.capstone.notekeeper.QuizModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.R;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizHolder> {
   private Context mContext;
    public QuizAdapter(Context pContext) {
        mContext = pContext;
    }

    @NonNull
    @Override
    public QuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.quiz_tab,parent,false);
        return new QuizHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class QuizHolder extends RecyclerView.ViewHolder{
        QuizHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
