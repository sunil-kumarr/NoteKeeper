package com.capstone.notekeeper.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.notekeeper.Adapter.NotesAdapter;
import com.capstone.notekeeper.Models.NotesDetails;
import com.capstone.notekeeper.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity  implements  NotesAdapter.DownloadData{
    private RecyclerView notesRecyclerView;
    private ImageView mTitleImage,courseImage;
    private TextView mTitleName,courseName;
    private static final String TAG = "HomeFragment";
    private ArrayList<NotesDetails> mNotes;
    private ShimmerFrameLayout shimmerFrameLayout;
    private String branch;
    private Integer branchImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        Intent intent = getIntent();
        courseImage= findViewById(R.id.courseImage);
        courseName = findViewById(R.id.courseName);
        if(intent!=null){
            branch = intent.getStringExtra("coursename");
            branchImage =intent.getIntExtra("courseimage",0);
            if(branch!=null){
                courseName.setText(branch);
            }
            if(branchImage!=null){
                courseImage.setImageResource(branchImage);
            }
        }
        notesRecyclerView = findViewById(R.id.courseRecyclerView);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.setIntensity(1);
        mNotes = new ArrayList<>();
        ArrayList<Integer> mColros = new ArrayList<>();
        mColros.add(R.color.blue_500);
        mColros.add(R.color.red_500);
        mColros.add(R.color.green_500);
        mColros.add(R.color.pink_500);
        mColros.add(R.color.yello);
        NotesAdapter notesAdapter = new NotesAdapter(mNotes,this,mColros);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        notesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        notesRecyclerView.setAdapter(notesAdapter);
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("notes").child(branch);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNotes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NotesDetails notes = postSnapshot.getValue(NotesDetails.class);
                   // Toast.makeText(NotesListActivity.this, ""+notes.getTitle(), Toast.LENGTH_SHORT).show();
                    mNotes.add(notes);
                }
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NotesListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public long DownloadNotes(NotesDetails fileUrl) {
        long output = 0;
        Toast.makeText(this, "Download Started...", Toast.LENGTH_SHORT).show();
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl.getFileLink()));
        request.setTitle(fileUrl.getTitle());
        request.setDescription(fileUrl.getDescription());
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "NoteKeeper");
        if (downloadManager != null) {
            output = downloadManager.enqueue(request);
        }
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadcomplete,intentFilter);
        return output;
    }
    BroadcastReceiver downloadcomplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Toast.makeText(context, "Download Complete.", Toast.LENGTH_SHORT).show();
            }
        }
    } ;
    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }
}
