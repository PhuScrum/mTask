package com.example.admin.listview;

import java.util.Comparator;
import java.util.Date;

public class Task implements Comparable<Task>{
    private String taskName;
    private String taskDescription;
    private String taskDate;
    private Date taskTime;

    public Task(String taskName, String taskDescription, String taskDate, Date taskTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskDate_String(){
        return taskDate.toString();
    }


    @Override
    public int compareTo(Task o) {
        return getTaskTime().compareTo(o.getTaskTime());
    }
}
