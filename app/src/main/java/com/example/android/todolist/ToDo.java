package com.example.android.todolist;

import java.io.Serializable;

import static android.R.attr.id;

/**
 * Created by Krit17 on 09-09-2017.
 */

public class ToDo implements Serializable {
    private String Title;
    private String Task;
    private String Category;
    private long time;
    private long id;
    public ToDo(String Title,String Task,long id,String category,long time){
        this.Title=Title;
        this.Task=Task;
        this.time=time;
        this.id=id;
        this.Category=category;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }




}
