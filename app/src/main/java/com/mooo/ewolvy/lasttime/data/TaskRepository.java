package com.mooo.ewolvy.lasttime.data;

import android.arch.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class TaskRepository {
    private final TaskDao taskDao;

    @Inject
    public TaskRepository (TaskDao taskDao){
        this.taskDao = taskDao;
    }

    public LiveData<List<TaskItem>> getAllTasks (){
        return taskDao.getAllTasks();
    }

    public LiveData<List<TaskItem>> getRemindedTasks (Date date) {
        return taskDao.getRemindedTasks(date);
    }

    public LiveData<TaskItem> getTaskById (int id){
        return taskDao.getTaskById(id);
    }

    public void deleteTask (TaskItem... items){
        taskDao.deleteTask(items);
    }

    public void addTask (TaskItem... items){
        taskDao.addTask(items);
    }

    public void updateTask (TaskItem... items){
        taskDao.updateTasks(items);
    }
}
