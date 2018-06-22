package com.mooo.ewolvy.lasttime.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.mooo.ewolvy.lasttime.data.TaskDao;
import com.mooo.ewolvy.lasttime.data.TaskItem;
import com.mooo.ewolvy.lasttime.data.TasksDatabase;

public class TaskEditViewModel extends AndroidViewModel {
    private TaskDao mTaskDao;
    private TaskItem mTask;

    public TaskEditViewModel(@NonNull Application application) {
        super(application);
        mTaskDao = TasksDatabase.getInstance(application).taskDao();
    }
}
