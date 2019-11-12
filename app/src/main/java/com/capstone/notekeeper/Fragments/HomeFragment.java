package com.capstone.notekeeper.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Adapter.NotesAdapter;
import com.capstone.notekeeper.Models.NotesDetails;
import com.capstone.notekeeper.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Context mContext;
    private RecyclerView notesRecyclerView;
    private ImageView mTitleImage;
    private TextView mTitleName;
    private static final String TAG = "HomeFragment";

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
        ArrayList<NotesDetails> notesList = new ArrayList<>();
//        for(int i=0;i<10;i++)
//            notesList.add(new NotesDetails("sunil kumar","Handwritten",
//                    "This are awesome notes","operating system notes",""));
        NotesAdapter notesAdapter = new NotesAdapter(notesList,mContext);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        notesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        notesRecyclerView.setAdapter(notesAdapter);
    }
}