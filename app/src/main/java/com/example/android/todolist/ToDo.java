package com.example.android.todolist;

import java.io.Serializable;

import static android.R.attr.id;

/**
 * Created by Krit17 on 09-09-2017.
 */

public class ToDo implements Serializable {
    private String Title;
    private String Task;

    private int id;
    public ToDo(String Title,String Task){
        this.Title=Title;
        this.Task=Task;
    }
    public ToDo(String Title,String Task,int id){
        this.Title=Title;
        this.Task=Task;
        this.id=id;
    }
    public int getId() {
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
