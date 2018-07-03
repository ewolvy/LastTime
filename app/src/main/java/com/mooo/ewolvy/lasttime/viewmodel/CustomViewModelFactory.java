package com.mooo.ewolvy.lasttime.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.mooo.ewolvy.lasttime.data.TaskRepository;

public class CustomViewModelFactory implements ViewModelProvider.Factory{

    private final TaskRepository repository;

    public CustomViewModelFactory(TaskRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskListViewModel.class)) {
            return (T) new TaskListViewModel(repository);
        } else if (modelClass.isAssignableFrom(TaskEditViewModel.class)) {
            return (T) new TaskEditViewModel(repository);
        } else {
            throw new IllegalArgumentException("Viewmodel not found");
        }
    }
}
