package com.capstone.notekeeper.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Adapter.CourseAdapter;
import com.capstone.notekeeper.Models.CourseType;
import com.capstone.notekeeper.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Context mContext;
    private RecyclerView mNotesRecycler;
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
        getActivity().setTitle("Home");
        ArrayList<CourseType> Courses = new ArrayList<>();
        Courses.add(new CourseType("Computer Science Engineering", R.drawable.ic_browser));
        Courses.add(new CourseType("Information Technology Engineering", R.drawable.ic_information));
        Courses.add(new CourseType("Civil Engineering", R.drawable.ic_engineer));
        Courses.add(new CourseType("Mechanical Engineering", R.drawable.ic_mechanic));
        Courses.add(new CourseType("Electrical Engineering", R.drawable.ic_electrician));
        Courses.add(new CourseType("Chemical Engineering", R.drawable.ic_chemical));
        Courses.add(new CourseType("BioEngineering & BioScience", R.drawable.ic_bio));
        Courses.add(new CourseType("Agriculture & Physical Sciences", R.drawable.ic_leaf));
        Courses.add(new CourseType("School of Business", R.drawable.ic_pie));
        Courses.add(new CourseType("Fashion Technology", R.drawable.ic_dress));
        Courses.add(new CourseType("School of Law     ", R.drawable.ic_balance));
        Courses.add(new CourseType("Quantitative Aptitude", R.drawable.ic_idea));
        Courses.add(new CourseType("Verbal Ability", R.drawable.ic_idea));
        Courses.add(new CourseType("Logical Reasoning", R.drawable.ic_idea));
        Courses.add(new CourseType("Verbal Reasoning", R.drawable.ic_idea));
        CourseAdapter myAdapter = new CourseAdapter(Courses, mContext);
        mNotesRecycler = view.findViewById(R.id.notes_recycler_view);
        mNotesRecycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        mNotesRecycler.setAdapter(myAdapter);
        mNotesRecycler.setItemAnimator(new DefaultItemAnimator());
    }


}