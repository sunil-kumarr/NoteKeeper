package com.capstone.notekeeper.Models;

public class CourseType {
    private String courseName;
    private Integer courseImage;

    public CourseType(String courseName, Integer courseImage) {
        this.courseName = courseName;
        this.courseImage = courseImage;
    }

    public Integer getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(Integer courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
