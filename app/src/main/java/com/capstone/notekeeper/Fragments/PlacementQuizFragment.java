package com.capstone.notekeeper.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Adapter.QuizAdapter;
import com.capstone.notekeeper.R;

public class PlacementQuizFragment extends Fragment {
    private Context mContext;
    private RecyclerView mQuizRecyclerView;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_placement_quiz, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Practice Quiz");
        mQuizRecyclerView = view.findViewById(R.id.quiz_recycler_view);
        QuizAdapter adapter = new QuizAdapter(mContext);
        mQuizRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        mQuizRecyclerView.setAdapter(adapter);
    }
}
