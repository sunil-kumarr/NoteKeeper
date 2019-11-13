package com.capstone.notekeeper.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class HomeFragment extends Fragment {
    private Context mContext;
    private RecyclerView notesRecyclerView;
    private ImageView mTitleImage;
    private TextView mTitleName;
    private static final String TAG = "HomeFragment";
    private ArrayList<NotesDetails> mNotes;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Study Materials");
        notesRecyclerView = view.findViewById(R.id.courseRecyclerView);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);
        mNotes = new ArrayList<>();
        ArrayList<Integer> mColros = new ArrayList<>();
        mColros.add(R.color.blue_500);
        mColros.add(R.color.red_500);
        mColros.add(R.color.green_500);
        mColros.add(R.color.pink_500);
        mColros.add(R.color.yello);
        NotesAdapter notesAdapter = new NotesAdapter(mNotes,mContext,mColros);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        notesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        notesRecyclerView.setAdapter(notesAdapter);
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("notes");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mNotes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    NotesDetails notes = postSnapshot.getValue(NotesDetails.class);
                    mNotes.add(notes);
                }
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
    }
}