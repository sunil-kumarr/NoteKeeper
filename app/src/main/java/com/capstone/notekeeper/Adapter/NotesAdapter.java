package com.capstone.notekeeper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Models.CourseType;
import com.capstone.notekeeper.Models.NotesDetails;
import com.capstone.notekeeper.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {
    private ArrayList<NotesDetails> notesList;
    private Context mContext;
    public NotesAdapter(ArrayList<NotesDetails> course, Context context){
        mContext = context;
        notesList = course;
    }
    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_notes_tab,parent,false);
        return new NotesHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder holder, int position) {
        NotesDetails current = notesList.get(position);

    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mContext, ""+courseList.size(), Toast.LENGTH_SHORT).show();
        return notesList.size();
    }

    class NotesHolder extends RecyclerView.ViewHolder{
        TextView NotesIcon,NotesNAme,NotesAuthor,NotesType,NotesRating;
        RatingBar ratingBar;
        NotesHolder(@NonNull View itemView) {
            super(itemView);
            NotesNAme = itemView.findViewById(R.id.NotesName);
            NotesAuthor = itemView.findViewById(R.id.NotesAuthor);
            NotesType = itemView.findViewById(R.id.NotesType);
        }
    }
}
