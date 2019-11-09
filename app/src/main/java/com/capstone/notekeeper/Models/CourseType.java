package com.capstone.notekeeper.Models;

public class CourseType {
    private String courseName;
    private Integer courseImage;

    public CourseType(String courseName, Integer courseImage) {
        this.courseName = courseName;
        this.courseImage = courseImage;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getCourseImage() {
        return courseImage;
    }
}
