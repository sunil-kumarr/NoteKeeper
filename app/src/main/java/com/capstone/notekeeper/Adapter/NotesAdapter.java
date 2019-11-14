package com.capstone.notekeeper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Activity.NoteShowActivity;
import com.capstone.notekeeper.Models.NotesDetails;
import com.capstone.notekeeper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        holder.NotesNAme.setText(current.getTitle());
        holder.NotesAuthor.setText(String.format("By: %s", current.getAuthor()));
        holder.NotesDescription.setText(current.getDescription());
        holder.NotesType.setText(String.format("Type: %s", current.getType()));
        holder.mNotesColor.setBackgroundResource(mColor.get(position%5));
        holder.mDownloadFile.setOnClickListener(v->{
            DM.DownloadNotes(current);
        });
        holder.cardView.setOnClickListener(v->{
            Intent intent = new Intent(mContext, NoteShowActivity.class);
            intent.putExtra("pdf",current.getFileLink());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mContext, ""+courseList.size(), Toast.LENGTH_SHORT).show();
        return notesList.size();
    }

    class NotesHolder extends RecyclerView.ViewHolder{
        TextView NotesNAme,NotesAuthor,NotesType,NotesDescription;
        FloatingActionButton mDownloadFile;
        View mNotesColor;
        CardView cardView;
        NotesHolder(@NonNull View itemView) {
            super(itemView);
            cardView =itemView.findViewById(R.id.notesMainCard);
            NotesNAme = itemView.findViewById(R.id.NotesName);
            NotesAuthor = itemView.findViewById(R.id.NotesAuthor);
            NotesType = itemView.findViewById(R.id.NotesType);
            NotesDescription = itemView.findViewById(R.id.NotesDescription);
            mNotesColor = itemView.findViewById(R.id.NotesColor);
            mDownloadFile = itemView.findViewById(R.id.NotesDownloadBtn);
        }
    }
    public interface DownloadData{
         long DownloadNotes(NotesDetails notes);
    }
}
