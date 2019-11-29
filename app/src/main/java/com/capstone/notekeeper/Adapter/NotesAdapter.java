package com.capstone.notekeeper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Activity.StudyMaterialDetails;
import com.capstone.notekeeper.Models.NotesDetails;
import com.capstone.notekeeper.R;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {
    private ArrayList<NotesDetails> notesList;
    private ArrayList<Integer> mColor;
    private DownloadData DM;
    private Context mContext;
    public NotesAdapter(ArrayList<NotesDetails> course, Context context,ArrayList<Integer> colorlist){
        mContext = context;
        notesList = course;
        mColor = colorlist;
        DM = (DownloadData) context;
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
        holder.NotesTitle.setText(current.getTitle());
        holder.NotesAuthor.setText(String.format("By: %s", current.getAuthor()));
        holder.NotesDescription.setText(current.getDescription());
//        holder.NotesType.setText(String.format("Type: %s", current.getType()));
        holder.mDownloadFile.setOnClickListener(v->{
            DM.DownloadNotes(current);
        });
        holder.cardView.setOnClickListener(v->{
            Intent intent = new Intent(mContext, StudyMaterialDetails.class);
            intent.putExtra("pdf",current);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mContext, ""+courseList.size(), Toast.LENGTH_SHORT).show();
        return notesList.size();
    }

    class NotesHolder extends RecyclerView.ViewHolder{
        TextView NotesTitle,NotesAuthor,NotesType,NotesDescription;
        ImageView mDownloadFile;
        View mNotesColor;
        CardView cardView;
        NotesHolder(@NonNull View itemView) {
            super(itemView);
            cardView =itemView.findViewById(R.id.notesMainCard);
            NotesTitle = itemView.findViewById(R.id.NotesName);
            NotesAuthor = itemView.findViewById(R.id.NotesAuthor);
//            NotesType = itemView.findViewById(R.id.NotesType);
            NotesDescription = itemView.findViewById(R.id.NotesDescription);
            mDownloadFile = itemView.findViewById(R.id.NotesDownloadBtn);
        }
    }
    public interface DownloadData{
         void DownloadNotes(NotesDetails notes);
    }
}
