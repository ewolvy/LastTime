package com.mooo.ewolvy.lasttime.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mooo.ewolvy.lasttime.data.TaskDao;
import com.mooo.ewolvy.lasttime.data.TaskItem;
import com.mooo.ewolvy.lasttime.data.TaskRepository;

import java.util.List;

public class TaskEditViewModel extends ViewModel {

    private TaskRepository repository;

    public TaskEditViewModel(TaskRepository repository) {
        this.repository = repository;
    }

    public LiveData<TaskItem> getTaskById(int id){
        return repository.getTaskById(id);
    }
}
