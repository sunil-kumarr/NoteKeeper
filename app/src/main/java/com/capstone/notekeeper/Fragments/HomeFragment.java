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
    ArrayList<CourseType> Courses;

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
        createCourse();

        CourseAdapter myAdapter = new CourseAdapter(Courses, mContext,"home");
        mNotesRecycler = view.findViewById(R.id.notes_recycler_view);
        mNotesRecycler.setLayoutManager(new GridLayoutManager(mContext, 3));
        mNotesRecycler.setAdapter(myAdapter);
        mNotesRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    private void createCourse() {
        Courses = new ArrayList<>();
        String[] coursename = mContext.getResources().getStringArray(R.array.course_titles);
        Courses.add(new CourseType(coursename[0], R.drawable.ic_browser));
        Courses.add(new CourseType(coursename[1], R.drawable.ic_information));
        Courses.add(new CourseType(coursename[2], R.drawable.ic_mechanic));
        Courses.add(new CourseType(coursename[3], R.drawable.ic_electrician));
        Courses.add(new CourseType(coursename[4], R.drawable.ic_engineer));
        Courses.add(new CourseType(coursename[5], R.drawable.ic_bio));
        Courses.add(new CourseType(coursename[6], R.drawable.ic_chemical));
        Courses.add(new CourseType(coursename[7], R.drawable.ic_leaf));
        Courses.add(new CourseType(coursename[8], R.drawable.ic_pie));
        Courses.add(new CourseType(coursename[9], R.drawable.ic_dress));
        Courses.add(new CourseType(coursename[10], R.drawable.ic_balance));
        Courses.add(new CourseType(coursename[11], R.drawable.ic_idea));
        Courses.add(new CourseType(coursename[12], R.drawable.ic_idea));
        Courses.add(new CourseType(coursename[13], R.drawable.ic_idea));
        Courses.add(new CourseType(coursename[14], R.drawable.ic_idea));
    }

}