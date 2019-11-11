package com.capstone.notekeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.notekeeper.Adapter.NotesAdapter;
import com.capstone.notekeeper.Models.NotesDetails;

import java.util.ArrayList;

public class NotesDetailsActivity extends AppCompatActivity {
    private RecyclerView notesRecyclerView;
    private ImageView mTitleImage;
    private TextView mTitleName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        mTitleImage = findViewById(R.id.courseTitleImage);
        mTitleName = findViewById(R.id.courseTitle);
        if(data!=null){
            mTitleImage.setImageResource(data.getInt("courseimage"));
            mTitleName.setText(data.getString("coursename"));
        }
        notesRecyclerView = findViewById(R.id.courseRecyclerView);
        ArrayList<NotesDetails> notesList = new ArrayList<>();
        for(int i=0;i<10;i++)
        notesList.add(new NotesDetails("sunil kumar","Handwritten",
                "This are awesome notes","operating system notes"));
        NotesAdapter notesAdapter = new NotesAdapter(notesList,this);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        notesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        notesRecyclerView.setAdapter(notesAdapter);
    }
}
