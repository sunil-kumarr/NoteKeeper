package com.capstone.notekeeper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Activity.NotesListActivity;
import com.capstone.notekeeper.Models.CourseType;
import com.capstone.notekeeper.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private ArrayList<CourseType> courseList;
    private Context mContext;
    public CourseAdapter(ArrayList<CourseType> course, Context context){
        mContext = context;
        courseList = course;
    }
    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_course_tab,parent,false);
        return new CourseHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        CourseType currentCourse = courseList.get(position);
       holder.CourseName.setText(currentCourse.getCourseName());
       holder.CourseImage.setImageResource(currentCourse.getCourseImage());
       holder.courseTab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(mContext, NotesListActivity.class);
               Bundle b = new Bundle();
               b.putString("coursename",currentCourse.getCourseName());
               b.putInt("courseimage",currentCourse.getCourseImage());
               i.putExtras(b);
               mContext.startActivity(i);
           }
       });

    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mContext, ""+courseList.size(), Toast.LENGTH_SHORT).show();
        return courseList.size();
    }

    class CourseHolder extends RecyclerView.ViewHolder{
        ImageView CourseImage;
        TextView CourseName;
        MaterialCardView courseTab;
        CourseHolder(@NonNull View itemView) {
            super(itemView);
            CourseImage = itemView.findViewById(R.id.courseImage);
            CourseName = itemView.findViewById(R.id.courseName);
            courseTab = itemView.findViewById(R.id.courseTab);
        }
    }
}
