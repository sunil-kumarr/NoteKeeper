package com.capstone.notekeeper.StudyLibararyModule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private ArrayList<CourseTypeModel> courseList;
    private Context mContext;
    private String mCaller;
    private String selectedItem;
    private ArrayList<LinearLayout> mLayoutArrayList;

    public CourseAdapter(ArrayList<CourseTypeModel> course, Context context, String caller) {
        mContext = context;
        courseList = course;
        mCaller = caller;
       mLayoutArrayList = new ArrayList<>();
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = null;
        if (mCaller.equals("home")) {
            layout = LayoutInflater.from(mContext).inflate(R.layout.study_layout_course_tab, parent, false);
        } else {
            layout = LayoutInflater.from(mContext).inflate(R.layout.study_category_tab, parent, false);
        }
        return new CourseHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        CourseTypeModel currentCourse = courseList.get(position);
        holder.CourseName.setText(currentCourse.getCourseName());
        holder.CourseImage.setImageResource(currentCourse.getCourseImage());
        if (mCaller.equals("home")) {
            holder.courseTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(mContext, NotesListActivity.class);
                    Bundle b = new Bundle();
                    b.putString("coursename", currentCourse.getCourseName());
                    b.putInt("courseimage", currentCourse.getCourseImage());
                    i.putExtras(b);
                    mContext.startActivity(i);
                }
            });
        } else if (mCaller.equals("upload")) {
            if(!mLayoutArrayList.contains(holder.courseTab2)) {
                mLayoutArrayList.add(holder.courseTab2);
            }
            holder.courseTab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View pView) {
                    for(LinearLayout layout:mLayoutArrayList){
                        layout.setBackgroundResource(R.drawable.category_box);
                    }
                    holder.courseTab2.setBackgroundResource(R.color.green_300);
                    selectedItem = currentCourse.getCourseName();
                }
            });

        }

    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mContext, ""+courseList.size(), Toast.LENGTH_SHORT).show();
        return courseList.size();
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        ImageView CourseImage;
        TextView CourseName;
        MaterialCardView courseTab;
        LinearLayout courseTab2;

        CourseHolder(@NonNull View itemView) {
            super(itemView);
            CourseImage = itemView.findViewById(R.id.courseImage);
            CourseName = itemView.findViewById(R.id.courseName);
            if (mCaller.equals("home")) {
                courseTab = itemView.findViewById(R.id.courseTab);
            } else {
                courseTab2 = itemView.findViewById(R.id.container_tab);
            }
        }
    }

}
