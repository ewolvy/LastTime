package com.mooo.ewolvy.lasttime.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.mooo.ewolvy.lasttime.data.TaskDao;
import com.mooo.ewolvy.lasttime.data.TaskItem;

public class TaskEditViewModel extends ViewModel {
    private TaskDao mTaskDao;
    private TaskItem mTask;
}
