package com.mooo.ewolvy.lasttime.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.graphics.Color;

import com.mooo.ewolvy.lasttime.data.TaskDao;
import com.mooo.ewolvy.lasttime.data.TaskItem;
import com.mooo.ewolvy.lasttime.data.TasksDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskListViewModel extends AndroidViewModel {
    private LiveData<List<TaskItem>> mTasks;
    private TaskDao mTaskDao;

    public TaskListViewModel(Application application){
        super (application);

        mTaskDao = TasksDatabase.getInstance(application).taskDao();
        mTasks = mTaskDao.getAllTasks();
    }

    public LiveData<List<TaskItem>> getAllTasks() {
        return mTasks;
    }

    public List<TaskItem> getRemindedTasks(){
        List<TaskItem> remindedTasks = new ArrayList<>();
        if (mTasks.getValue() != null) {
            for (int i = 0; i < mTasks.getValue().size(); i++) {
                if (mTasks.getValue().get(i).getRemindOn().before(Calendar.getInstance().getTime())){
                    remindedTasks.add(mTasks.getValue().get(i));
                }
            }
        }

        return remindedTasks;
    }

    public void addDummyTask(TaskItem task){
        mTaskDao.addTask(task);
    }
}
